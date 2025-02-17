package cn.synu.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Component
public class Course {
    private Long id;

    private String cname;

    private String type;

    private String class_hour;

    //关联属性
    private List<Teacher> teachers = new ArrayList<>();
}