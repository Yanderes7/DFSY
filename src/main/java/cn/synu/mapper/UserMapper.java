package cn.synu.mapper;

import cn.synu.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User selectByUsername(String username);

    User selectIdentityByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    void updateByUserName(@Param("username") String username, @Param("password") String password);

    void deleteByUserNameAndPassword(@Param("username") String username, @Param("password") String password);

    List<Long> selPermission_id(Long role_id);

    List<String> selPermission(@Param("permissionIds") List<Long> permissionIds);
}