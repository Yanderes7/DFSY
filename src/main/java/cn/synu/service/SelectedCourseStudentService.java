package cn.synu.service;

import cn.synu.domain.SelectedCourseStudent;
import cn.synu.domain.Student;
import cn.synu.qo.JsonResult;
import cn.synu.qo.Save;
import cn.synu.qo.XHQueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @ClassName SelectedCourseStudentService
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/18 16:03
 * @Version 1.0
 **/
public interface SelectedCourseStudentService {
    void deleteByPrimaryKey(Long id);

    void insert(SelectedCourseStudent record);

    SelectedCourseStudent selectByPrimaryKey(Long id);

    List<SelectedCourseStudent> selectAll();

    void updateByPrimaryKey(SelectedCourseStudent record);

    //已选课程插入
    JsonResult save(Save save , Student student);

    List<SelectedCourseStudent> selectByStu_ID(Long id);

    //根据学生ID查询 当前学生的选课情况 返回到一个集合中,并且进行分页、过滤操作
    PageInfo<SelectedCourseStudent> selectByStu_ID_pageinfo(XHQueryObject qo,Long stu_id);

    //根据当前教师ID进行查询，查询学生已选课程表，并根据查到的学生id进行查询学生信息表操作
    PageInfo<Student> selectByTea_ID(XHQueryObject qo , Long t_id);

    //根据课程id删除学生已选课程表
    void deleteByCourseId(Long id);

    //根据课程id删除课程-教师-教室中间表
    void deleteByCid(Long id);
}
