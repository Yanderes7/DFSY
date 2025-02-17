package cn.synu.service;

import cn.synu.domain.Student;
import cn.synu.qo.XHQueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @ClassName StudentService
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/18 16:03
 * @Version 1.0
 **/
public interface StudentService {
    void deleteByPrimaryKey(Long id);

    void insert(Student record);

    Student selectByPrimaryKey(Long id);

    List<Student> selectAll();

    void updateByPrimaryKey(Student record);

    void deleteByIdForS_C_S(Long id);

    Boolean selByUsername(String username);

    //查询所有学生信息，并做分页、过滤处理
    PageInfo<Student> selectAllStu(XHQueryObject qo);

    //根据学生id集合进行批量搜索
    PageInfo<Student> selStuByStuIDS(XHQueryObject qo, List<Long> stuIDs);

    //分页过滤查询
    PageInfo<Student> selectForList(XHQueryObject qo);

    //管理员删除学生信息
    void deleteByAdmin(Long id);

}
