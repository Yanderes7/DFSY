package cn.synu.web.controller;

import cn.synu.domain.User;
import cn.synu.qo.JsonResult;
import cn.synu.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @ClassName LonginController
 * @Description TODO
 * @Auther 小昊同学
 * @Date 2023/2/3 21:21
 * @Version 1.0
 **/
@Controller
public class LonginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public JsonResult login(User user){
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return new JsonResult(false, "账号错误");
        } catch (IncorrectCredentialsException ice) {
            return new JsonResult(false, "密码错误");
        }
        return new JsonResult(true, "欢迎您尊敬的" + user.getUsername() + "同志");
    }

    @RequestMapping("/logout")
    public String logOut(HttpSession httpSession) {
        httpSession.removeAttribute("USER_IN_SESSION");
        return "/static/login.html";
    }
}
