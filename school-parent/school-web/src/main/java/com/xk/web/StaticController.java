package com.xk.web;

import com.xk.common.Const;
import com.xk.common.aop.LoggerManage;
import com.xk.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by hengxiaokang
 * Date:2018/6/24
 * Time:15:35
 */
@Slf4j
@RestController
@RequestMapping("/")
public class StaticController extends BaseController
{
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @LoggerManage(description = "登录页")
    public ModelAndView login(Model model)
    {
        return getView("login");
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @LoggerManage(description = "首页")
    public ModelAndView home(Model model)
    {
        ModelAndView index = getView("index");
        User user = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        index.addObject("user", user);
        return index;
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    @LoggerManage(description = "错误页")
    public ModelAndView error(Model model)
    {
        return getView("error");
    }


}