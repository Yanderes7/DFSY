package cn.synu.service.impl;

import cn.synu.domain.Classroom;
import cn.synu.mapper.ClassroomMapper;
import cn.synu.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName ClassroomServiceImpl
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/18 16:04
 * @Version 1.0
 **/
@Transactional
@Service
public class ClassRoomServiceImpl implements ClassRoomService {

    @Autowired
    private ClassroomMapper classroomMapper;
    @Autowired
    private Classroom classroom;

    @Override
    public void deleteByPrimaryKey(Long id) {
        classroomMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(Classroom record) {
        classroomMapper.insert(record);
    }

    @Override
    public Classroom selectByPrimaryKey(Long id) {
        return classroomMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Classroom> selectAll() {
        return classroomMapper.selectAll();
    }

    @Override
    public void updateByPrimaryKey(Classroom record) {
        classroomMapper.updateByPrimaryKey(record);
    }

    //根据获取到的教室id设置对应的教室为空教室
    @Override
    public void updateByCr_ids(List<Long> cr_ids) {
        //遍历修改
        for (Long cr_id : cr_ids) {
            Classroom classroom = classroomMapper.selectByPrimaryKey(cr_id);
            classroom.setFlag(false);
            classroomMapper.updateByPrimaryKey(classroom);
        }
    }

    //查询空余的教室
    @Override
    public List<Classroom> selectByNullClassRoom(boolean b) {
        return classroomMapper.selectByNullClassRoom(b);
    }
}
