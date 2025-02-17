package cn.synu.web.controller;

import cn.synu.domain.Course;
import cn.synu.domain.Student;
import cn.synu.domain.Teacher;
import cn.synu.qo.JsonResult;
import cn.synu.qo.XHQueryObject;
import cn.synu.service.*;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AdminController
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/27 14:14
 * @Version 1.0
 **/
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private SelectedCourseStudentService selectedCourseStudentService;
    @Autowired
    private ClassRoomService classRoomService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

    /**
     * 所用课程信息展示、增删改查
     **/
    //查
    @RequestMapping("/courseList")
    @RequiresPermissions("admin:courseList")
    public String courseList(XHQueryObject qo, Model model){
        PageInfo<Course> pageInfo = courseService.query(qo);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("qo",qo);
        return "admin/courseList";
    }

    //删除
    @RequestMapping("/courseDelete")
    @RequiresPermissions("admin:courseDelete")
    public String courseDelete(Long id){
        //根据课程id删除课程信息
        courseService.deleteByPrimaryKey(id);
        //根据课程id删除学生已选课程表
        selectedCourseStudentService.deleteByCourseId(id);
        //根据课程id，查询到的对应教室id
        List<Long> cr_ids = courseService.selectedByCourseIDForClassRoom_ID(id);
        //根据获取到的教室id设置对应的教室为空教室
        classRoomService.updateByCr_ids(cr_ids);
        //根据课程id删除课程-教师-教室中间表
        selectedCourseStudentService.deleteByCid(id);
        return "redirect:/admin/courseList";
    }

    //courseInput
    @RequestMapping("/courseInput")
    @RequiresPermissions("admin:courseInput")
    public String courseInput(Model model,Long id){
        System.out.println(id);
        //查询所有的任课教师
        List<Teacher> allTeachers = teacherService.selectAll();
        model.addAttribute("allTeachers",allTeachers);
        //修改
        if(id != null){
            //根据id查询所有的课程信息进行回显
            Course course = courseService.selectByPrimaryKey(id);
            model.addAttribute("course",course);
            //根据课程id查询当前的课程信息
            //通过中间表查询对应的教师id
            List<Long> t_ids = courseService.selT_idByC_id(id);
            //根据t_ids批量查找对应的教师信息
            List<Teacher> selfTeachers = new ArrayList<>();
            for (Long t_id : t_ids) {
                Teacher teacher = teacherService.selectByPrimaryKey(t_id);
                selfTeachers.add(teacher);
            }
            System.out.println(selfTeachers);
            model.addAttribute("selfTeachers",selfTeachers);
        }
        return "admin/courseInput";
    }
    //courseSaveOrUpdate
    @RequestMapping("/courseSaveOrUpdate")
    @RequiresPermissions("admin:courseSaveOrUpdate")
    @ResponseBody
    public JsonResult courseSaveOrUpdate(Course course,Long [] teachersIds,Model model){
        if(course.getId() != null){
            //修改
            JsonResult jsonResult = courseService.insertForC_T_CR(course, teachersIds);
            return jsonResult;
        }
        else{
            //新增
            JsonResult jsonResult = courseService.save(course , teachersIds);
            return jsonResult;
        }
    }

    //检查课程名是否存在
    @RequestMapping("/checkCname")
    @ResponseBody
    public Map<String, Boolean> checkCname(Course course) {
        Boolean flag = courseService.checkCname(course.getCname());
        Map<String, Boolean> map = new HashMap<>();
        map.put("valid" ,!flag);
        return map;
    }

    /**
     * 所有教师信息展示、增删改查
     **/
    //查
    @RequestMapping("/teacherList")
    @RequiresPermissions("admin:teacherList")
    public String teacherList(Model model ,XHQueryObject qo){
        PageInfo<Teacher> pageInfo = teacherService.selectForList(qo);
        model.addAttribute("qo",qo);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/teacherList";
    }

    //删
    @RequestMapping("/teacherDelete")
    @RequiresPermissions("admin:teacherDelete")
    public String teacherDelete(Long id){
        //教师信息删除
        teacherService.deleteAll_Teacher(id);
        return "redirect:/admin/teacherList";
    }

    //teacherInput
    @RequestMapping("/teacherInput")
    @RequiresPermissions("admin:teacherInput")
    public String teacherInput(Long id,Model model){
        if(id != null){
            //根据当前教师id查询当前的教师信息
            Teacher teacher = teacherService.selectByPrimaryKey(id);
            model.addAttribute("teacher",teacher);
        }
        return "admin/teacherInput";
    }

    //teacherSaveOrUpdate
    @RequestMapping("/teacherSaveOrUpdate")
    @RequiresPermissions("admin:teacherSaveOrUpdate")
    @ResponseBody
    public JsonResult teacherSaveOrUpdate(Teacher teacher){
        if(teacher.getId() != null){
            //修改
            teacherService.updateByAdmin(teacher);
            return new JsonResult(true,"成功");
        }else{
            //新增
            teacherService.insert(teacher);
            return new JsonResult(true,"成功");
        }
    }


    /**
     * 所有学生信息展示、增删改查
     **/
    //查
    @RequestMapping("/studentList")
    @RequiresPermissions("admin:studentList")
    public String studentList(Model model,XHQueryObject qo){
        PageInfo<Student> pageInfo = studentService.selectForList(qo);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("qo",qo);
        return "admin/studentList";
    }

    //删除
    @RequestMapping("/studentDelete")
    @RequiresPermissions("admin:studentDelete")
    public String studentDelete(Long id){
        //管理员删除学生信息
        studentService.deleteByAdmin(id);
        return "redirect:/admin/studentList";
    }

    //studentInput
    @RequestMapping("/studentInput")
    @RequiresPermissions("admin:studentInput")
    public String studentInput(Long id,Model model){
        if(id != null){
            //根据id查询当前学生的信息
            Student student = studentService.selectByPrimaryKey(id);
            model.addAttribute("student",student);
        }
        return "admin/studentInput";
    }

    //studentSaveOrUpdate
    @RequestMapping("/studentSaveOrUpdate")
    @RequiresPermissions("admin:studentSaveOrUpdate")
    @ResponseBody
    public JsonResult studentSaveOrUpdate(Student student){
        if(student.getId() == null){
            //新增
            studentService.insert(student);
        }else {
            //修改
            studentService.updateByPrimaryKey(student);
        }
        return new JsonResult(true ,"操作成功！");
    }

    //跳转主页
    @RequestMapping("/homePage")
    public String homePage(){
        return "common/individual";
    }
}
