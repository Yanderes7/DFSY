package cn.synu.service.impl;

import cn.synu.domain.Student;
import cn.synu.domain.Teacher;
import cn.synu.domain.User;
import cn.synu.mapper.StudentMapper;
import cn.synu.mapper.TeacherMapper;
import cn.synu.mapper.UserMapper;
import cn.synu.service.UserService;
import cn.synu.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/2/3 21:32
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public void deleteByPrimaryKey(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(User record) {
        userMapper.insert(record);
    }

    @Override
    public User selectByPrimaryKey(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public void updateByPrimaryKey(User record) {
        userMapper.updateByPrimaryKey(record);
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User selectIdentityByUsernameAndPassword(String username, String password) {
        return userMapper.selectIdentityByUsernameAndPassword(username,password);
    }

    @Override
    public void loginByUser(User user) {
        //根据用户名和密码查询对应的身份 1 为管理员 2为学生 3为教师
        User userIdentity = userMapper.selectIdentityByUsernameAndPassword(user.getUsername(), user.getPassword());
        System.err.println(userIdentity);
        if(userIdentity.getIdentity() == 2){
            //学生
            Student student = studentMapper.selByUsernameAndPassword(userIdentity.getUsername(), userIdentity.getPassword());
            HttpSession session = UserUtils.getSession();
            session.setAttribute("USER_IN_SESSION",student);
            System.err.println("这里是session" + session.toString());
            return;
        } else if(userIdentity.getIdentity() == 3){
            //教师
            Teacher teacher = teacherMapper.selByUsernameAndPassword(userIdentity.getUsername(), userIdentity.getPassword());
            System.err.println(teacher);
            HttpSession session = UserUtils.getSession();
            session.setAttribute("USER_IN_SESSION",teacher);
            return;
        }
    }

    @Override
    public void updateByUserName(String username, String password) {
        userMapper.updateByUserName(username,password);
    }

    @Override
    public List<Long> selPermission_id(Long role_id) {
        List<Long> permission_ids = userMapper.selPermission_id(role_id);
        return permission_ids;
    }

    @Override
    public List<String> selPermission(List<Long> permissionIds) {
        List<String> currentUser_permission = userMapper.selPermission(permissionIds);
        return currentUser_permission;
    }
}
