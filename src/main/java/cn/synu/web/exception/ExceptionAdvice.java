package cn.synu.web.exception;


import cn.synu.qo.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName ExceptionConfig
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/1/8 15:36
 * @Version 1.0
 **/
@ControllerAdvice // 控制器增强器注解
public class ExceptionAdvice {

    //分为两种
    //自定义异常
    @ExceptionHandler(CustomizeException.class)
    public static String ExceptionHandler(HandlerMethod handlerMethod, Model model, HttpServletResponse response, CustomizeException ce) {
        //分为两种类型
        //AJAX
        if (handlerMethod.hasMethodAnnotation(ResponseBody.class)) {
            response.setContentType("application/json;charset=utf-8");
            try {
                response.getWriter().write(new ObjectMapper()
                        .writeValueAsString(new JsonResult(false, ce.getMessage())));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            //请求
            model.addAttribute("errorMsg", ce.getMessage());
            return "common/error";
        }
    }

    //权限异常
    @ExceptionHandler(UnauthorizedException.class)
    public static String UnauthorizedException(Model model, HandlerMethod handlerMethod, HttpServletResponse response, Exception e) {
        //分为两种类型
        //AJAX
        if (handlerMethod.hasMethodAnnotation(RequestBody.class)) {
            response.setContentType("application/json;charset=utf-8;");
            try {
                response.getWriter().write(new ObjectMapper()
                        .writeValueAsString(new JsonResult(false, "系统繁忙请稍后再试！")));
            } catch (IOException e1) {
                e.printStackTrace();
            }
            return null;
        } else {
            //请求
            model.addAttribute("errorMsg", "系统繁忙请稍后再试！");
            return "/common/nopermission";
        }
    }

    //系统异常
    @ExceptionHandler(Exception.class)
    public static String ExceptionHandler(Model model, HandlerMethod handlerMethod, HttpServletResponse response, Exception e) {
        //分为两种类型
        //AJAX
        if (handlerMethod.hasMethodAnnotation(RequestBody.class)) {
            response.setContentType("application/json;charset=utf-8;");
            try {
                response.getWriter().write(new ObjectMapper()
                        .writeValueAsString(new JsonResult(false, "系统繁忙请稍后再试！")));
            } catch (IOException e1) {
                e.printStackTrace();
            }
            return null;
        } else {
            //请求
            model.addAttribute("errorMsg", "系统繁忙请稍后再试！");
            return "/common/error";
        }

    }


}
