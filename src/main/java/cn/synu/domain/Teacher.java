package cn.synu.domain;

import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Component
public class Teacher {
    private Long id;

    private Long t_id;

    private Long tnum;

    private String username;

    private String password;

    private String name;

    private Long age;

    private String address;

    private String sex;

    private String email;

    private String p_title;

    private String dept;
}