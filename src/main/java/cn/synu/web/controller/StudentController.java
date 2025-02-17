package cn.synu.web.controller;

import cn.synu.domain.Course;
import cn.synu.domain.SelectedCourseStudent;
import cn.synu.domain.Student;
import cn.synu.qo.JsonResult;
import cn.synu.qo.Save;
import cn.synu.qo.XHQueryObject;
import cn.synu.service.CourseService;
import cn.synu.service.SelectedCourseStudentService;
import cn.synu.service.StudentService;
import cn.synu.service.TeacherService;
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
 * @ClassName StudentController
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/18 16:11
 * @Version 1.0
 **/
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SelectedCourseStudentService selectedCourseStudentService;

    //课程展示
    @RequestMapping("/list")
    @RequiresPermissions("student:list")
    public String list(Model model, XHQueryObject qo){
        PageInfo<Course> pageInfo = courseService.query(qo);
        model.addAttribute("pageInfo",pageInfo);
        return "student/list";
    }

    //课程选择
    @RequestMapping("/selectCourse")
    @RequiresPermissions("student:selectCourse")
    public String selectCourse(Model model,XHQueryObject qo){
        //分页、过滤查询课程信息
        PageInfo<Course> pageInfo = courseService.selected(qo);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("qo",qo);
        //查询
        return "student/selected";
    }

    //插入已选课程
    @RequestMapping("/save")
    @RequiresPermissions("student:save")
    @ResponseBody
    public JsonResult save(Save save, Student student){
        System.out.println(save);
        if(save.getT_id() == null){
            return new JsonResult(false,"请选择任课教师！");
        }
        JsonResult jsonResult = selectedCourseStudentService.save(save, student);
        return jsonResult;
    }

    //展示已选课程
    @RequestMapping("/selectedCourse")
    @RequiresPermissions("student:selectedCourse")
    public String selectCourse(Model model,XHQueryObject qo,Long stu_id){
        PageInfo<SelectedCourseStudent> pageInfo = selectedCourseStudentService.selectByStu_ID_pageinfo(qo,stu_id);
        model.addAttribute("pageInfo",pageInfo);
        return "student/selectedCourse";
    }

    //展示个人主页
    @RequestMapping("/individual")
    @RequiresPermissions("student:individual")
    public String individual(Model model){
        HttpSession session = UserUtils.getSession();
        Student studentUser = (Student) session.getAttribute("USER_IN_SESSION");
        System.err.println(studentUser);
        Student student = studentService.selectByPrimaryKey(studentUser.getId());
        model.addAttribute("student",student);
        return "student/individual";
    }

    //修改个人主页
    @RequestMapping("/update")
    @RequiresPermissions("student:update")
    public String update(Model model,Long id){
        Student student = studentService.selectByPrimaryKey(id);
        System.out.println(student);
        model.addAttribute("student",student);
        return "student/input";
    }

    //修改个人信息后并保存
    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions("student:saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(Student student){
        System.out.println(student);
        if(student.getId() == null){
            return new JsonResult(false,"当前操作不合法");
        }
        studentService.updateByPrimaryKey(student);
        return new JsonResult(true,"您以保存成功！");
    }

    //检查用户名是否重复
    @RequestMapping("/checkUsername")
    @ResponseBody
    public Map<String, Boolean> checkUsername(Student student){
        Boolean flag = studentService.selByUsername(student.getUsername());
        Map<String,Boolean> map = new HashMap<>();
        map.put("valid",!flag);
        return map;
    }

    //删除已选课程·
    @RequestMapping("/delete")
    @RequiresPermissions("student:delete")
    public String delete(Long id){
        studentService.deleteByIdForS_C_S(id);
        return "redirect:/student/selectedCourse";
    }
}
