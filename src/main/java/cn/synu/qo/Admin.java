package cn.synu.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName Admin
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/2/4 14:22
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Admin {

    private String username;

    private String password;

    private Long identity;

}
