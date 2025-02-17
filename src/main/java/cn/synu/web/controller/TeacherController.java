package cn.synu.web.controller;

import cn.synu.domain.Student;
import cn.synu.domain.Teacher;
import cn.synu.qo.JsonResult;
import cn.synu.qo.XHQueryObject;
import cn.synu.service.SelectedCourseStudentService;
import cn.synu.service.StudentService;
import cn.synu.service.TeacherService;
import cn.synu.service.UserService;
import cn.synu.utils.UserUtils;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TeacherController
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/26 17:35
 * @Version 1.0
 **/
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private SelectedCourseStudentService selectedCourseStudentService;
    @Autowired
    private UserService userService;

    //教师可以查看所有学生信息
    @RequestMapping("/students")
    @RequiresPermissions("teacher:students")
    public String list(Model model, XHQueryObject qo){
        PageInfo<Student> pageInfo = studentService.selectAllStu(qo);
        model.addAttribute("qo",qo);
        model.addAttribute("pageInfo",pageInfo);
        return "teacher/students";
    }

    //当前登录教师可以查看自己的课程选课情况
    @RequestMapping("/stuSelected")
    @RequiresPermissions("teacher:stuSelected")
    public String stuSelected(Model model,XHQueryObject qo){
        //获取当前登录的教师信息
        HttpSession session = UserUtils.getSession();
        Teacher teacherUser = (Teacher) session.getAttribute("USER_IN_SESSION");
        //根据当前教师ID进行查询，查询学生已选课程表，并根据查到的学生id进行查询学生信息表操作
        PageInfo<Student> pageInfo = selectedCourseStudentService.selectByTea_ID(qo,teacherUser.getId());
        model.addAttribute("qo",qo);
        model.addAttribute("pageInfo",pageInfo);
        return "teacher/stuSelected";
    }

    //教师个人信息
    @RequestMapping("/individual")
    @RequiresPermissions("teacher:individual")
    public String individual(Model model){
        HttpSession session = UserUtils.getSession();
        Teacher teacherUser = (Teacher) session.getAttribute("USER_IN_SESSION");
        Teacher teacher = teacherService.selectByPrimaryKey(teacherUser.getId());
        model.addAttribute("teacher",teacher);
        return "teacher/individual";
    }

    //修改个人信息显示页面
    @RequestMapping("/update")
    @RequiresPermissions("teacher:update")
    public String update(Long id , Model model){
        Teacher teacher = teacherService.selectByPrimaryKey(id);
        model.addAttribute("teacher",teacher);
        return "teacher/input";
    }

    //修改个人信息后并保存
    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions("teacher:saveOrUpdate")
    @ResponseBody
    public JsonResult input(Teacher teacher){
        //合理化判断
        if(teacher.getId() == null){
            return new JsonResult(false , "非法操作！");
        }
        //根据教师id查询完整的教师信息
        Teacher tea = teacherService.selectByPrimaryKey(teacher.getId());
        //修改教师表中的信息
        teacherService.updateByPrimaryKey(teacher);
        //根据用户名和密码修改用户表中的教师密码
        String username = tea.getUsername();
        String password = teacher.getPassword();
        userService.updateByUserName(username,password);
        return new JsonResult(true,"保存成功！");
    }

    //检查用户名是否重复
    @RequestMapping("/checkUsername")
    @ResponseBody
    public Map<String, Boolean> checkUsername(Teacher teacher){
        Boolean flag = teacherService.selByUsername(teacher.getUsername());
        Map<String,Boolean> map = new HashMap<>();
        map.put("valid",!flag);
        return map;
    }
}
