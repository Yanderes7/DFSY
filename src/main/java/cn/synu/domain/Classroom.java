package cn.synu.domain;

import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Component
public class Classroom {
    private Long id;

    private String cr_name;

    private Boolean flag;//判断教室是否为空
}