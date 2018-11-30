package com.xk.web;

import com.alibaba.fastjson.JSONObject;
import com.xk.common.Const;
import com.xk.common.aop.LoggerManage;
import com.xk.domain.GroupContacts;
import com.xk.domain.IsDelete;
import com.xk.domain.Student;
import com.xk.domain.User;
import com.xk.domain.result.ExceptionMsg;
import com.xk.domain.result.Response;
import com.xk.service.GroupContactService;
import com.xk.service.SmsService;
import com.xk.service.StudentService;
import com.xk.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by hengxiaokang
 * Date:2018/8/8
 * Time:15:35
 */
@Slf4j
@RestController
@RequestMapping("/")
public class SMSController extends BaseController {

    @Autowired
    GroupContactService contactRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    SmsService smsService;

    @Value("${rsa.PrivateKey}")
    String rsaPrivateKey;

    @RequestMapping(value = "/tosms", method = RequestMethod.GET)
    @LoggerManage(description = "编辑短信")
    public ModelAndView findStudentById()
    {
        ModelAndView addclass = getView("sms");
        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        List<GroupContacts> list = contactRepository.findGroupContactsByUserIdAndIsDelete(attribute.getId(), IsDelete.YES);

        String gid = getRequest().getParameter("gid");
        if ((gid == null || "".equals(gid)) && list.size() > 0) {
            gid = list.get(0) == null ? "" : list.get(0).getId().toString();
        }

        addclass.addObject("groups", list);
        addclass.addObject("defaultFavorties", gid);
        return addclass;
    }

    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    @LoggerManage(description = "发短信")
    public Response update(Model model, @RequestBody JSONObject json)
    {
        String gid = json.getString("gid");
        String centent = json.getString("centent");
        centent="【杨老师】 "+centent;
        try {
            GroupContacts one = contactRepository.getOne(Long.valueOf(gid));
            String stuId = one.getStuId();

            List<Student> studentByIds = studentService.findStudentByIds(stuId);

            User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
            if (attribute.getPhone() != null)
                smsService.sendSMS(new String(RSAUtil.decryptByPrivateKey(Base64.decodeBase64(attribute.getPhone()), rsaPrivateKey)), centent);
            for (Student s : studentByIds) {
                String phone = s.getPhone();
                if (phone != null) {
                    phone = new String(RSAUtil.decryptByPrivateKey(Base64.decodeBase64(phone), rsaPrivateKey));
                    smsService.sendSMS(phone, centent);
                }
            }

            return new Response(ExceptionMsg.SUCCESS);
        } catch (Exception e) {
            log.error("---{}", e);
        }
        return new Response(ExceptionMsg.FAILED);
    }
}