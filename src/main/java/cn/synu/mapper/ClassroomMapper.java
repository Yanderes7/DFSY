package cn.synu.mapper;

import cn.synu.domain.Classroom;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ClassroomMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Classroom record);

    Classroom selectByPrimaryKey(Long id);

    List<Classroom> selectAll();

    int updateByPrimaryKey(Classroom record);

    List<Classroom>selectByNullClassRoom(boolean b);

    List<Long> selectCr_idByC_id(Long id);

    //根据教室id批量查找
    List<Classroom> selectByPrimaryKeyForeach(@Param("cr_ids") List<Long> cr_ids);

    //将遍历后的教室集合进行批量修改
}