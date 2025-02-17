package cn.synu.service;

import cn.synu.domain.Teacher;
import cn.synu.qo.XHQueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @ClassName TeacherService
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/18 16:03
 * @Version 1.0
 **/
public interface TeacherService {
    void deleteByPrimaryKey(Long id);

    void insert(Teacher record);

    Teacher selectByPrimaryKey(Long id);

    List<Teacher> selectAll();

    void updateByPrimaryKey(Teacher record);

    Boolean selByUsername(String username);

    List<Teacher> selectByPrimaryKeyForeach(List<Long> t_ids);

    PageInfo<Teacher> selectForList(XHQueryObject qo);

    //删除所有的教师信息
    void deleteAll_Teacher(Long id);

    //管理员修改教师信息
    void updateByAdmin(Teacher teacher);

}
