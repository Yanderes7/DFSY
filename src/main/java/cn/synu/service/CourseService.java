package cn.synu.service;

import cn.synu.domain.Course;
import cn.synu.qo.JsonResult;
import cn.synu.qo.XHQueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @ClassName CourseService
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/18 16:03
 * @Version 1.0
 **/
public interface CourseService {
    void deleteByPrimaryKey(Long id);

    void insert(Course record);

    Course selectByPrimaryKey(Long id);

    List<Course> selectAll();

    void updateByPrimaryKey(Course record);

    PageInfo<Course> query(XHQueryObject qo);

    PageInfo<Course> selected(XHQueryObject qo);

    Course selectedByID(Long id);

    //根据课程id，查询到的对应的教室id
    List<Long> selectedByCourseIDForClassRoom_ID(Long id);

    //根据课程id查询当前课程的任课老师
    Course selCourseByTeacher(Long id);

    //向中间表插入
    JsonResult insertForC_T_CR(Course course, Long[] teachersIds);

    //检查课程是否已经存在
    Boolean checkCname(String cname);

    //通过中间表查询对应的教师id
    List<Long> selT_idByC_id(Long id);

    //管理员课程新增
    JsonResult save(Course course, Long[] teachersIds);
}
