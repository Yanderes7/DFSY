package cn.synu.mapper;

import cn.synu.domain.SelectedCourseStudent;
import cn.synu.qo.XHQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SelectedCourseStudentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SelectedCourseStudent record);

    SelectedCourseStudent selectByPrimaryKey(Long id);

    List<SelectedCourseStudent> selectAll();

    int updateByPrimaryKey(SelectedCourseStudent record);

    //根据学生ID查询 当前学生的选课情况 返回到一个集合中
    List<SelectedCourseStudent> selectByStu_ID(Long stu_id);

    //根据学生ID查询 当前学生的选课情况 返回到一个集合中,并且进行分页、过滤操作
    List<SelectedCourseStudent> selectByStu_ID_pageinfo(XHQueryObject qo, Long stu_id);

    //根据当前教师ID进行查询，查询学生已选课程表，并根据查到的学生id进行查询学生信息表操作
    List<SelectedCourseStudent> selectByTea_ID(XHQueryObject qo, Long t_id);

    //根据课程id删除学生已选课程表
    void deleteByCourseId(Long id);

    //根据课程id删除课程-教师-教室中间表
    void deleteByCid(Long id);

    //删除当前课程以及当前教师所对应的教室中间表、且将对应的教室设置为空教室
    void deleteByCidAndTid(@Param("c_id") Long c_id, @Param("t_id") Long t_id);

    //查询当前课程以及当前教师所对应的教室id
    Long selectBySidAndTid(@Param("c_id") Long c_id, @Param("t_id") Long t_id);

    //删除选了当前教师的课程的学生选课表
    void deleteByTid(Long t_id);

    List<SelectedCourseStudent> selectByT_ID(Long id);

    //根据学生id删除学生已选课程表
    void deleteBySid(Long id);
}