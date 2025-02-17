package cn.synu.service;

import cn.synu.domain.User;

import java.util.List;

/**
 * @ClassName UserService
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/18 16:03
 * @Version 1.0
 **/
public interface UserService {
    void deleteByPrimaryKey(Long id);

    void insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    void updateByPrimaryKey(User record);

    User selectByUsername(String username);

    User selectIdentityByUsernameAndPassword(String username, String password);

    void loginByUser(User user);

    void updateByUserName(String username, String password);

    List<Long> selPermission_id(Long valueOf);

    List<String> selPermission(List<Long> permissionIds);
}
