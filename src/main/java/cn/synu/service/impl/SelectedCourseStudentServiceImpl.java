package cn.synu.service.impl;

import cn.synu.domain.*;
import cn.synu.mapper.CourseMapper;
import cn.synu.mapper.SelectedCourseStudentMapper;
import cn.synu.mapper.TeacherMapper;
import cn.synu.qo.JsonResult;
import cn.synu.qo.Save;
import cn.synu.qo.XHQueryObject;
import cn.synu.service.SelectedCourseStudentService;
import cn.synu.service.StudentService;
import cn.synu.utils.UserUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SelectedCourseStudentServiceImpl
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/20 15:42
 * @Version 1.0
 **/
@Service
@Transactional
public class SelectedCourseStudentServiceImpl implements SelectedCourseStudentService {

    @Autowired
    private SelectedCourseStudentMapper selectedCourseStudentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private SelectedCourseStudent selectedCourseStudent;
    @Autowired
    private StudentService studentService;

    @Override
    public void deleteByPrimaryKey(Long id) {
        selectedCourseStudentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(SelectedCourseStudent record) {
        selectedCourseStudentMapper.insert(record);
    }

    @Override
    public SelectedCourseStudent selectByPrimaryKey(Long id) {
        return selectedCourseStudentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SelectedCourseStudent> selectAll() {
        return selectedCourseStudentMapper.selectAll();
    }

    @Override
    public void updateByPrimaryKey(SelectedCourseStudent record) {
        selectedCourseStudentMapper.updateByPrimaryKey(record);
    }

    //已选课程插入
    @Override
    public JsonResult save(Save save , Student student) {
//        //合理化判断
//        if(save.getT_id() == null){
//            return new JsonResult(false,"请选择任课教师！");
//        }
        HttpSession session = UserUtils.getSession();
        Student studentUser = (Student) session.getAttribute("USER_IN_SESSION");
        //根据id查询指定的课程
        Course course = courseMapper.selectByPrimaryKey(save.getC_id());
        //根据id查询指定的教师
        Teacher teacher = teacherMapper.selectByPrimaryKey(save.getT_id());
        //根据课程id、教师id查询指定的教室
        Classroom classroom = courseMapper.selC_T_CR(save.getC_id(), save.getT_id());
        //将查询到的信息插入到已选课程表中
        selectedCourseStudent.setC_name(course.getCname());
        selectedCourseStudent.setC_type(course.getType());
        selectedCourseStudent.setC_hour(course.getClass_hour());
        selectedCourseStudent.setT_name(teacher.getName());
        selectedCourseStudent.setT_title(teacher.getP_title());
        selectedCourseStudent.setClassroom(classroom.getCr_name());
        //1L为测试
        selectedCourseStudent.setS_id(studentUser.getId());
        //插入教师id
        selectedCourseStudent.setT_id(save.getT_id());
        //插入课程id
        selectedCourseStudent.setC_id(save.getC_id());
        //插入

        //查询当前学生的选课情况
        //1L为测试
        List<SelectedCourseStudent> selectedCourseStudents = selectedCourseStudentMapper.selectByStu_ID(studentUser.getId());
        //进行合理化判断
        for (SelectedCourseStudent scs : selectedCourseStudents) {
            if (scs.getClassroom().equals(classroom.getCr_name())){
                return new JsonResult(false,"您已经选过此课程！");
            }
        }
        for (SelectedCourseStudent scs : selectedCourseStudents) {
            if (scs.getC_name().equals(course.getCname())){
                return new JsonResult(false,"您已经选过此类课程，如想选择，请先退课！");
            }
        }
        selectedCourseStudentMapper.insert(selectedCourseStudent);
        return new JsonResult(true,"您已选课成功！");
    }

    //根据学生ID查询 当前学生的选课情况 返回到一个集合中,并且进行分页、过滤操作
    @Override
    public List<SelectedCourseStudent> selectByStu_ID(Long id) {
        return selectedCourseStudentMapper.selectByStu_ID(id);
    }

    //根据学生ID查询 当前学生的选课情况 返回到一个集合中,并且进行分页、过滤操作
    @Override
    public PageInfo<SelectedCourseStudent> selectByStu_ID_pageinfo(XHQueryObject qo,Long stu_id) {
        HttpSession session = UserUtils.getSession();
        Student studentUser = (Student)session.getAttribute("USER_IN_SESSION");
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        return new PageInfo<>(selectedCourseStudentMapper.selectByStu_ID_pageinfo(qo,studentUser.getId()));
    }

    //根据当前教师ID进行查询，查询学生已选课程表，并根据查到的学生id进行查询学生信息表操作
    @Override
    public PageInfo<Student> selectByTea_ID(XHQueryObject qo, Long t_id) {
        Teacher teacherUser = (Teacher) UserUtils.getSession().getAttribute("USER_IN_SESSION");
        List<SelectedCourseStudent> selectedCourseStudents = selectedCourseStudentMapper.selectByTea_ID(qo, teacherUser.getId());
        List<Long> stuIDs = new ArrayList<>();
        for (SelectedCourseStudent scs : selectedCourseStudents) {
            stuIDs.add(scs.getS_id());
        }
        //根据学生id集合进行批量搜索
        return studentService.selStuByStuIDS(qo,stuIDs);
    }

    //根据课程id删除学生已选课程表
    @Override
    public void deleteByCourseId(Long id) {
        selectedCourseStudentMapper.deleteByCourseId(id);
    }

    //根据课程id删除课程-教师-教室中间表
    @Override
    public void deleteByCid(Long id) {
        selectedCourseStudentMapper.deleteByCid(id);
    }
}
