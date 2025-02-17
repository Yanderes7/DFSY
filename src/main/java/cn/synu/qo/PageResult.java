package cn.synu.qo;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * @ClassName PageResult
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/8 11:58
 * @Version 1.0
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class PageResult {
    //当前页
    private int currentPage = 1;
    //每页显示条数
    private int pageSize = 1;

}
