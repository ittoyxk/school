package com.xk.web;

import com.xk.common.Const;
import com.xk.common.aop.LoggerManage;
import com.xk.domain.User;
import com.xk.domain.result.ExceptionMsg;
import com.xk.domain.result.Response;
import com.xk.domain.result.ResponseData;
import com.xk.service.UserService;
import com.xk.utils.DateUtils;
import com.xk.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hengxiaokang
 * Date:2018/6/22
 * Time:21:24
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userRepository;

    @Value("${rsa.PrivateKey}")
    String rsaPrivateKey;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @LoggerManage(description = "登陆")
    public ResponseData login(@RequestBody User user, HttpServletResponse response)
    {
        try {
            user.setPassWord(new String(RSAUtil.decryptByPrivateKey(Base64.decodeBase64(user.getPassWord()), rsaPrivateKey)));

            //这里不是bug，前端userName有可能是邮箱也有可能是昵称。
            User loginUser = userRepository.findByUserNameOrPhone(user.getUserName(), user.getPhone());
            if (loginUser == null) {
                return new ResponseData(ExceptionMsg.LoginNameNotExists);
            } else if (loginUser.getPassWord().equals(getPwd(user.getPassWord()))) {
                Cookie cookie = new Cookie(Const.LOGIN_SESSION_KEY, cookieSign(loginUser.getId().toString()));
                cookie.setMaxAge(Const.COOKIE_TIMEOUT);
                cookie.setPath("/");
                response.addCookie(cookie);
                getSession().setAttribute(Const.LOGIN_SESSION_KEY, loginUser);
                return new ResponseData(ExceptionMsg.SUCCESS);
            } else {
                return new ResponseData(ExceptionMsg.LoginNameOrPassWordError);
            }
        } catch (Exception e) {
            log.error("login failed, ", e);
        }
        return new ResponseData(ExceptionMsg.FAILED);
    }

    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @LoggerManage(description = "注册")
    public String create(@RequestBody User user)
    {
        try {
            User registUser = userRepository.findByPhone(user.getPhone());
            if (null != registUser) {
                return "手机号已使用";
            }
            User userNameUser = userRepository.findByUserName(user.getUserName());
            if (null != userNameUser) {
                return "用户名已使用";
            }
            user.setPassWord(getPwd(user.getPassWord()));
            user.setCreateTime(DateUtils.getCurrentTime());
            user.setLastModifyTime(DateUtils.getCurrentTime());
            user.setProfilePicture("img/favicon.png");
            userRepository.save(user);
            getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
        } catch (Exception e) {
            // TODO: handle exception
            log.error("create user failed, ", e);
            return "Exception";
        }
        return "succeed";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @LoggerManage(description = "退出登陆")
    public Response logout(HttpServletRequest request, HttpServletResponse response)
    {
        if (getSession().getAttribute(Const.LOGIN_SESSION_KEY) != null) {
            getSession().removeAttribute(Const.LOGIN_SESSION_KEY);

            return new Response(ExceptionMsg.SUCCESS);
        }
        return new Response(ExceptionMsg.FAILED);
    }

}