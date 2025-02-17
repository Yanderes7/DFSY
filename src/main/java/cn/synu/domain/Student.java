package cn.synu.domain;

import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Component
public class Student {

    private Long id;

    private String username;

    private String password;

    private String name;

    private Long age;

    private String dept;

    private String magor;

    private Long t_number;

    private String email;

    private String address;

    private String sex;

    private Long snum;
}