package cn.synu.service.impl;

import cn.synu.domain.Student;
import cn.synu.domain.User;
import cn.synu.mapper.SelectedCourseStudentMapper;
import cn.synu.mapper.StudentMapper;
import cn.synu.mapper.UserMapper;
import cn.synu.qo.XHQueryObject;
import cn.synu.service.StudentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName StudentServiceImpl
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/18 16:04
 * @Version 1.0
 **/
@Transactional
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private SelectedCourseStudentMapper selectedCourseStudentMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void deleteByPrimaryKey(Long id) {
        studentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(Student student) {
        studentMapper.insert(student);
        //新建用户
        String username = student.getUsername();
        String password = student.getPassword();
        User user = new User(username,password,Long.valueOf(2));
        userMapper.insert(user);
    }

    @Override
    public Student selectByPrimaryKey(Long id) {
        return studentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Student> selectAll() {
        return studentMapper.selectAll();
    }

    @Override
    public void updateByPrimaryKey(Student student) {
        //根据学生id查询学生信息
        Student stu = studentMapper.selectByPrimaryKey(student.getId());
        //修改学生个人信息
        studentMapper.updateByPrimaryKey(student);
        //如果学生修改了密码，则还需修改用户表中的密码
        String username = stu.getUsername();
        String password = student.getPassword();
        userMapper.updateByUserName(username,password);
    }

    //根据课程id删除学生已选课程
    @Override
    public void deleteByIdForS_C_S(Long id) {
        studentMapper.deleteByIdForS_C_S(id);
    }

    //检查用户名是否重复
    @Override
    public Boolean selByUsername(String username) {
        Boolean flag = studentMapper.selByUsername(username);
        return flag;
    }

    //查询所有学生信息，并做分页、过滤处理
    @Override
    public PageInfo<Student> selectAllStu(XHQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        return new PageInfo<>(studentMapper.selectAllStu(qo));
    }

    //根据学生id集合进行批量搜索
    @Override
    public PageInfo<Student> selStuByStuIDS(XHQueryObject qo, List<Long> stuIDs) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        return new PageInfo<>(studentMapper.selStuByStuIDS(qo,stuIDs));
    }

    @Override
    public PageInfo<Student> selectForList(XHQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        return new PageInfo<>(studentMapper.selectForList(qo));
    }

    //管理员删除学生信息
    @Override
    public void deleteByAdmin(Long id) {
        //根据id查询对应的学生信息
        Student student = studentMapper.selectByPrimaryKey(id);
        //先根据id删除学生已选课程表的信息
        selectedCourseStudentMapper.deleteBySid(id);
        //再删除学生信息表
        studentMapper.deleteByPrimaryKey(id);
        //再删除用户表
        String username = student.getUsername();
        String password = student.getPassword();
        userMapper.deleteByUserNameAndPassword(username,password);
    }
}
