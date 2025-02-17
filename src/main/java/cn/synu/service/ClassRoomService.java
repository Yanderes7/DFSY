package cn.synu.service;

import cn.synu.domain.Classroom;

import java.util.List;

/**
 * @ClassName ClassroomService
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/18 16:03
 * @Version 1.0
 **/
public interface ClassRoomService {
    void deleteByPrimaryKey(Long id);

    void insert(Classroom record);

    Classroom selectByPrimaryKey(Long id);

    List<Classroom> selectAll();

    void updateByPrimaryKey(Classroom record);

    //根据获取到的教室id设置对应的教室为空教室
    void updateByCr_ids(List<Long> cr_ids);

    //查询空余的教室
    List<Classroom> selectByNullClassRoom(boolean b);
}
