package cn.synu.qo;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * @ClassName EmployeeQueryObject
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/8 19:27
 * @Version 1.0
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class XHQueryObject extends QueryObject {
    private String keyword;
    private Long deptId;

    private int currentPage = 1;
    private int pageSize = 8;
}
