package cn.synu.domain;

import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Component
public class SelectedCourseStudent {
    private Long id;

    private String c_name;

    private String c_type;

    private String c_hour;

    private String t_name;

    private String t_title;

    private String classroom;

    private Long s_id;

    private Long t_id;

    private Long c_id;
}