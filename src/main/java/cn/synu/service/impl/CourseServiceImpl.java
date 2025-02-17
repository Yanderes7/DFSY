package cn.synu.service.impl;

import cn.synu.domain.Classroom;
import cn.synu.domain.Course;
import cn.synu.mapper.ClassroomMapper;
import cn.synu.mapper.CourseMapper;
import cn.synu.mapper.SelectedCourseStudentMapper;
import cn.synu.qo.JsonResult;
import cn.synu.qo.XHQueryObject;
import cn.synu.service.CourseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CourseServiceImpl
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/18 16:04
 * @Version 1.0
 **/
@Transactional
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private SelectedCourseStudentMapper selectedCourseStudentMapper;
    @Autowired
    private ClassroomMapper classroomMapper;

    @Override
    public void deleteByPrimaryKey(Long id) {
        courseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(Course record) {
        courseMapper.insert(record);
    }

    @Override
    public Course selectByPrimaryKey(Long id) {
        return courseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Course> selectAll() {
        return courseMapper.selectAll();
    }

    @Override
    public void updateByPrimaryKey(Course record) {
        courseMapper.updateByPrimaryKey(record);
    }

    //分页过滤
    @Override
    public PageInfo<Course> query(XHQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        return new PageInfo<>(courseMapper.selForList(qo));
    }

    @Override
    public PageInfo<Course> selected(XHQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        return new PageInfo<>(courseMapper.selected(qo));
    }

    @Override
    public Course selectedByID(Long id) {
        return courseMapper.selectedByID(id);
    }

    //根据课程id，查询到的对应的教室id
    @Override
    public List<Long> selectedByCourseIDForClassRoom_ID(Long id) {
        List<Long> cr_ids = courseMapper.selectedByCourseIDForClassRoom_ID(id);
        return cr_ids;
    }

    //根据课程id查询当前课程的任课老师
    @Override
    public Course selCourseByTeacher(Long id) {
        return courseMapper.selCourseByTeacher(id);
    }

    //向中间表插入
    @Override
    public JsonResult insertForC_T_CR(Course course, Long[] teachersIds) {
        //合理化判断，判断课程名是否重复
        if(courseMapper.checkCname(course.getCname())){
            return new JsonResult(false,"非法操作");
        }
        //判断当前的课程是否有教师上课
        if(teachersIds == null){
            return new JsonResult(false,"请为当前课程选择教师！");
        }
        //先将原来的教室变成空教室、即根据当前的课程查找对应的教室
        List<Long> cr_ids = classroomMapper.selectCr_idByC_id(course.getId());
        //批量查找，将查找到的教室变成空教室
        List<Classroom> classroomsWillNull = classroomMapper.selectByPrimaryKeyForeach(cr_ids);
        //将查找到的教室变成空教室
        for (Classroom classroom : classroomsWillNull) {
            classroom.setFlag(false);
            classroomMapper.updateByPrimaryKey(classroom);
        }
        //再删除中间表信息
        selectedCourseStudentMapper.deleteByCid(course.getId());
        //合理化判断，判断剩余教室是否够分配
        List<Classroom> classrooms = classroomMapper.selectByNullClassRoom(false);
        if(classrooms.size() < teachersIds.length){
            return new JsonResult(false,"剩余教室为：" + classrooms.size() + "已不够分配,请扩充新的教室！");
        }
        //修改课程信息
        courseMapper.updateByPrimaryKey(course);
        //合理化判断
        if(teachersIds != null && teachersIds.length >= 0){
            List<Long> classroomsLong = new ArrayList<>();
            for (Classroom classroom : classrooms) {
                classroomsLong.add(classroom.getId());
            }
            List<Long> classRooms = new ArrayList<>();
            for(int i = 0 ; i < teachersIds.length ; i++){
                classRooms.add(classroomsLong.get(i));
            }
            //遍历再插入中间表信息
            for(int i = 0 ; i < teachersIds.length ; i++){
                courseMapper.insertForC_T_CR(course.getId(),teachersIds[i],classRooms.get(i));
                //根据教室id将教室变成正在使用的教室
                Classroom classroom = classroomMapper.selectByPrimaryKey(classRooms.get(i));
                classroom.setFlag(true);
                classroomMapper.updateByPrimaryKey(classroom);
            }
        }
        return new JsonResult(true,"操作成功！");
    }

    //管理员课程新增
    @Override
    public JsonResult save(Course course, Long[] teachersIds) {
        //合理化判断，判断课程名是否重复
        if(courseMapper.checkCname(course.getCname())){
            return new JsonResult(false,"非法操作");
        }
        //判断当前的课程是否有教师上课
        if(teachersIds == null){
            return new JsonResult(false,"请为当前课程选择教师！");
        }
        //查找到所有的空教室,返回到一个集合中
        List<Classroom> classrooms = classroomMapper.selectByNullClassRoom(false);
        if(classrooms.size() == 0){
            return new JsonResult(false,"剩余教室为：" + classrooms.size() + "已不够分配,请扩充新的教室！");
        }
        //新增课程信息
        courseMapper.insert(course);
        //合理化判断
        if(teachersIds != null && teachersIds.length >= 0){
            List<Long> classroomsLong = new ArrayList<>();
            for (Classroom classroom : classrooms) {
                classroomsLong.add(classroom.getId());
            }
            List<Long> classRooms = new ArrayList<>();
            for(int i = 0 ; i < teachersIds.length ; i++){
                classRooms.add(classroomsLong.get(i));
            }
            //遍历再插入中间表信息
            for(int i = 0 ; i < teachersIds.length ; i++){
                courseMapper.insertForC_T_CR(course.getId(),teachersIds[i],classRooms.get(i));
                //根据教室id将教室变成正在使用的教室
                Classroom classroom = classroomMapper.selectByPrimaryKey(classRooms.get(i));
                classroom.setFlag(true);
                classroomMapper.updateByPrimaryKey(classroom);
            }
        }
        return new JsonResult(true,"操作成功！");
    }

    //检查课程是否已经存在
    @Override
    public Boolean checkCname(String cname) {
        return courseMapper.checkCname(cname);
    }

    //通过中间表查询对应的教师id
    @Override
    public List<Long> selT_idByC_id(Long id) {
        return courseMapper.selT_idByC_id(id);
    }

}
