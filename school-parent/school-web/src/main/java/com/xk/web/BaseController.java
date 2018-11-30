package com.xk.web;

import com.xk.common.Const;
import com.xk.utils.Des3EncryptionUtil;
import com.xk.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by hengxiaokang
 * Date:2018/6/22
 * Time:17:18
 */
@Slf4j
@RestController
public class BaseController
{

    protected ModelAndView getView(String viewName)
    {
        return new ModelAndView(viewName);
    }

    protected String cookieSign(String value)
    {
        try {
            value = value + Const.PASSWORD_KEY;
            String sign = Des3EncryptionUtil.encode(Const.DES3_KEY, value);
            return sign;
        } catch (Exception e) {
            log.error("cookie签名异常：", e);
        }
        return null;
    }

    protected HttpServletRequest getRequest()
    {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected HttpSession getSession()
    {
        return getRequest().getSession();
    }

    protected String getPwd(String password){
        try {
            String pwd = MD5Util.encrypt(password+Const.PASSWORD_KEY);
            return pwd;
        } catch (Exception e) {
            log.error("密码加密异常：",e);
        }
        return null;
    }

}