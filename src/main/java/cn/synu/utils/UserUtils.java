package cn.synu.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * @ClassName userUtils
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/8 18:59
 * @Version 1.0
 **/
//无私奉献的工具类
public abstract class UserUtils {

    public static HttpSession getSession() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest().getSession();
    }
}
