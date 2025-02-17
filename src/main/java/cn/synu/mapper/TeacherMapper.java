package cn.synu.mapper;

import cn.synu.domain.Classroom;
import cn.synu.domain.Teacher;
import cn.synu.qo.XHQueryObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface TeacherMapper {
    void deleteByPrimaryKey(Long id);

    void insert(Teacher record);

    Teacher selectByPrimaryKey(Long id);

    List<Teacher> selectAll();

    void updateByPrimaryKey(Teacher record);

    Boolean selByUsername(String username);

    List<Teacher> selectByPrimaryKeyForeach(@Param("t_ids") List<Long> t_ids);

    List<Teacher> selectForList(XHQueryObject qo);

    //根据教师id查询到所使用的教室且返回一个id集合
    List<Long> selectC_T_CrForT_id(@Param("id") Long id);

    //根据教室id集合进行批量查询，返回一个教室对象集合
    List<Classroom> selectC_T_CrListForCR_ID(@Param("classRoomIds") List<Long> classRoomIds);

    //根据教师id删除中间表信息
    void deleteC_t_CrByTid(Long t_id);

    //管理员修改教师信息
    void updateByAdmin(Teacher teacher);


    Teacher selByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}