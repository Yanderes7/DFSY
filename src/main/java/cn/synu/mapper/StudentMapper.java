package cn.synu.mapper;

import cn.synu.domain.Student;
import cn.synu.qo.XHQueryObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentMapper {

    //新增
    void insert(Student record);

    //删除
    void deleteByPrimaryKey(Long id);

    //修改
    void updateByPrimaryKey(Student record);

    //查一个
    Student selectByPrimaryKey(Long id);

    //查多个
    List<Student> selectAll();

    void deleteByIdForS_C_S(Long id);

    Boolean selByUsername(String username);

    List<Student> selectAllStu(XHQueryObject qo);

    List<Student> selStuByStuIDS(@Param("qo") XHQueryObject qo, @Param("stuIDs") List<Long> stuIDs);

    List<Student> selectForList(XHQueryObject qo);

    Student selByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}