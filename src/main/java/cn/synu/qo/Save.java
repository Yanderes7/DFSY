package cn.synu.qo;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * @ClassName ceshi
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/20 13:56
 * @Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Component
public class Save {
    //教师id
    private Long t_id;
    //课程id
    private Long c_id;
}
