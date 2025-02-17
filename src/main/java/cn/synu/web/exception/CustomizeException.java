package cn.synu.web.exception;

/**
 * @ClassName zidingyiException
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/8 16:08
 * @Version 1.0
 **/

public class CustomizeException extends RuntimeException {
    public CustomizeException(String msg) {
        super(msg);
    }
}
