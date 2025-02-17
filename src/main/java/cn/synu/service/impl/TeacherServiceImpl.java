package cn.synu.service.impl;

import cn.synu.domain.Classroom;
import cn.synu.domain.SelectedCourseStudent;
import cn.synu.domain.Teacher;
import cn.synu.domain.User;
import cn.synu.mapper.ClassroomMapper;
import cn.synu.mapper.SelectedCourseStudentMapper;
import cn.synu.mapper.TeacherMapper;
import cn.synu.mapper.UserMapper;
import cn.synu.qo.XHQueryObject;
import cn.synu.service.TeacherService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName TeacherServiceImpl
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/18 16:04
 * @Version 1.0
 **/
@Transactional
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private SelectedCourseStudentMapper selectedCourseStudentMapper;
    @Autowired
    private ClassroomMapper classroomMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void deleteByPrimaryKey(Long id) {
        teacherMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(Teacher teacher) {
        teacherMapper.insert(teacher);
        String username = teacher.getUsername();
        String password = teacher.getPassword();
        User user = new User(username,password,Long.valueOf(3));
        userMapper.insert(user);
    }

    @Override
    public Teacher selectByPrimaryKey(Long id) {
        Teacher teacher = teacherMapper.selectByPrimaryKey(id);

        return teacher;
    }

    @Override
    public List<Teacher> selectAll() {
        return teacherMapper.selectAll();
    }

    @Override
    public void updateByPrimaryKey(Teacher record) {
        teacherMapper.updateByPrimaryKey(record);
    }

    //检查用户名是否重复
    @Override
    public Boolean selByUsername(String username) {
        return teacherMapper.selByUsername(username);
    }

    @Override
    public List<Teacher> selectByPrimaryKeyForeach(List<Long> t_ids) {
        return teacherMapper.selectByPrimaryKeyForeach(t_ids);
    }

    @Override
    public PageInfo<Teacher> selectForList(XHQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        return new PageInfo<>(teacherMapper.selectForList(qo));
    }

    //删除关于当前教师的所有信息
    @Override
    public void deleteAll_Teacher(Long id) {
        //删除选了当前教师的课程的学生选课表
        selectedCourseStudentMapper.deleteByTid(id);
        //根据教师id查询到所使用的教室且返回一个id集合
        List<Long> classRoomIds = teacherMapper.selectC_T_CrForT_id(id);
        //如果是没有绑定课程的教师则直接删除信息
        if(classRoomIds.size() == 0){
            //删除用户表
            Teacher teacher = teacherMapper.selectByPrimaryKey(id);
            String username = teacher.getUsername();
            String password = teacher.getPassword();
            userMapper.deleteByUserNameAndPassword(username,password);
            teacherMapper.deleteByPrimaryKey(id);
            return;
        }
        //根据教室id集合进行批量查询，返回一个教室对象集合
        List<Classroom> classrooms = teacherMapper.selectC_T_CrListForCR_ID(classRoomIds);
        //遍历设置教室集合中的flag为false
        for (Classroom classroom : classrooms) {
            classroom.setFlag(false);
            //将遍历后的教室集合进行修改
            classroomMapper.updateByPrimaryKey(classroom);
        }
        //删除用户表
        Teacher teacher = teacherMapper.selectByPrimaryKey(id);
        String username = teacher.getUsername();
        String password = teacher.getPassword();
        userMapper.deleteByUserNameAndPassword(username,password);
        //根据教师id删除中间表
        teacherMapper.deleteC_t_CrByTid(id);
        //删除当前教师
        teacherMapper.deleteByPrimaryKey(id);

    }

    //管理员修改教师信息
    @Override
    public void updateByAdmin(Teacher teacher) {
        //先修改当前教师的信息
        teacherMapper.updateByAdmin(teacher);
        //再根据教师id修改学生已选课程表中的教师姓名、教师职称
        //首先根据教师id在学生已选课程表中进行查询
        List<SelectedCourseStudent> selectedCourseStudents = selectedCourseStudentMapper.selectByT_ID(teacher.getId());
        System.err.println(selectedCourseStudents);
        Teacher teacherLast = teacherMapper.selectByPrimaryKey(teacher.getId());
        System.err.println(teacherLast);
        for (SelectedCourseStudent selectedCourseStudent : selectedCourseStudents) {
            selectedCourseStudent.setT_name(teacherLast.getName());
            selectedCourseStudent.setT_title(teacherLast.getP_title());
            selectedCourseStudentMapper.updateByPrimaryKey(selectedCourseStudent);
        }
        String username = teacher.getUsername();
        String password = teacher.getPassword();
        userMapper.updateByUserName(username,password);
    }

}
