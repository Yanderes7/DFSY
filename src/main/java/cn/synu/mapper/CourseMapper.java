package cn.synu.mapper;

import cn.synu.domain.Classroom;
import cn.synu.domain.Course;
import cn.synu.qo.XHQueryObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface CourseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Course record);

    Course selectByPrimaryKey(Long id);

    List<Course> selectAll();

    int updateByPrimaryKey(Course record);

    List<Course> selForList(XHQueryObject qo);

    List<Course> selected(XHQueryObject qo);

    Course selectedByID(Long id);

    Classroom selC_T_CR(Long c_id, Long t_id);

    List<Long>  selectedByCourseIDForClassRoom_ID(Long id);

    //根据课程id查询当前课程的任课老师
    Course selCourseByTeacher(Long id);

    //检查课程是否已经存在
    Boolean checkCname(String cname);

    //遍历插入中间表
    void insertForC_T_CR(@Param("c_id") Long c_id, @Param("t_id") Long t_id, @Param("cr_id") Long cr_id);

    //根据课程id查询对应的教师id
    Long [] selCtdForTid(Long id);

    List<Long> selT_idByC_id(Long id);
}