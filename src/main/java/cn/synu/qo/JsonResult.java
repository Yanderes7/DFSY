package cn.synu.qo;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * @ClassName JsonResult
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/8 14:33
 * @Version 1.0
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class JsonResult {
    private Boolean success;
    private String msg;

}
