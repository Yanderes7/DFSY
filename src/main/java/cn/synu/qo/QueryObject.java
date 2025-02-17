package cn.synu.qo;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName QueryObject
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
public class QueryObject<T> {
    //用户传递
    private int currentPage = 1;
    private int pageSize = 3;

    //数据库查询
    private int totalCount;
    private List<T> data;
    //后台计算
    private int totalPage;
    private int prevPage;
    private int nextPage;

    public QueryObject(int currentPage, int pageSize, int totalCount, List<T> data) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.data = data;

        this.totalPage = this.totalCount % this.pageSize == 0 ? this.totalCount / this.pageSize : this.totalCount / this.pageSize + 1;
        this.prevPage = this.currentPage - 1 <= 1 ? this.currentPage : this.currentPage - 1;
        this.nextPage = this.currentPage + 1 >= this.totalPage ? this.totalPage : this.totalPage + 1;
    }
}
