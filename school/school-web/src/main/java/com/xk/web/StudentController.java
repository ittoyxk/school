package com.xk.web;

import com.xk.common.Const;
import com.xk.common.aop.LoggerManage;
import com.xk.domain.*;
import com.xk.domain.result.ExceptionMsg;
import com.xk.domain.result.Response;
import com.xk.domain.result.ResultData;
import com.xk.service.ClazzService;
import com.xk.service.MoneyService;
import com.xk.service.StudentService;
import com.xk.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by xiaokang on 2018/8/5.
 */
@Slf4j
@RestController
@RequestMapping("/")
public class StudentController extends BaseController {

    @Autowired
    ClazzService clazzRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    MoneyService moneyService;

    @Value("${rsa.PrivateKey}")
    String rsaPrivateKey;
    @Value("${rsa.PublicKey}")
    String rsaPublicKey;

    @RequestMapping(value = "/stuShow", method = RequestMethod.GET)
    @LoggerManage(description = "进入學生信息")
    public ModelAndView stuShow(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size)
    {
        Sort sort = new Sort(Sort.Direction.ASC, "stuCode");
        Pageable pageable = PageRequest.of(page, size, sort);

        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        Page<ResultData<String, Object>> students = studentService.findStudentByUserId(attribute.getId(), pageable);
        ModelAndView student = getView("student");
        List<ResultData<String, Object>> content = students.getContent();
        List<ResultData<String, Object>> decript = decript(content);
        student.addObject("students", decript);
        student.addObject("uri", "/stuShow");
        student.addObject("id", 0);
        model.addAttribute("page", page >= 4 ? page + 1 : 5);
        return student;
    }


    @RequestMapping(value = "/findStudentByClassId", method = RequestMethod.GET)
    @LoggerManage(description = "进入學生信息")
    public ModelAndView findStudentByClassId(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, @RequestParam("id") String classId)
    {
        Sort sort = new Sort(Sort.Direction.ASC, "stuCode");
        Pageable pageable = PageRequest.of(page, size, sort);

        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        Page<ResultData<String, Object>> students = studentService.findStudentByUserIdAndClassId(attribute.getId(), Long.valueOf(classId), pageable);
        List<ResultData<String, Object>> content = students.getContent();
        List<ResultData<String, Object>> decript = decript(content);
        model.addAttribute("students", decript);
        model.addAttribute("uri", "/findStudentByClassId");
        model.addAttribute("id", classId);
        model.addAttribute("page", page >= 4 ? page + 1 : 5);
        return getView("student");
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @LoggerManage(description = "进入學生信息")
    public ModelAndView findById(Model model, @RequestParam("id") String stuId)
    {
        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        ResultData<String, Object> byId = studentService.findStudentById(attribute.getId(), Long.valueOf(stuId));
        List<ResultData<String, Object>> list = new ArrayList<ResultData<String, Object>>();
        list.add(byId);
        List<ResultData<String, Object>> decript = decript(list);
        model.addAttribute("students", decript);
        model.addAttribute("uri", "/findById");
        model.addAttribute("id", stuId);
        model.addAttribute("page", 5);
        return getView("student");
    }

    @RequestMapping(value = "/findByName", method = RequestMethod.GET)
    @LoggerManage(description = "进入學生信息")
    public ModelAndView findByName(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, @RequestParam("stuName") String stuName) throws UnsupportedEncodingException
    {
        if (stuName != null)
            stuName = URLDecoder.decode(stuName, "UTF-8");
        Sort sort = new Sort(Sort.Direction.ASC, "stuCode");
        Pageable pageable = PageRequest.of(page, size, sort);

        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        Page<ResultData<String, Object>> byId = studentService.findStudentByName(attribute.getId(), "%" + stuName + "%", pageable);
        List<ResultData<String, Object>> content = byId.getContent();
        List<ResultData<String, Object>> decript = decript(content);
        model.addAttribute("students", decript);
        model.addAttribute("uri", "/findByName");
        model.addAttribute("id", 0);
        model.addAttribute("stuName", stuName == null ? "" : URLEncoder.encode(stuName, "UTF-8"));
        model.addAttribute("page", page >= 4 ? page + 1 : 5);
        return getView("student");
    }

    private List<ResultData<String, Object>> decript(List<ResultData<String, Object>> content)
    {
        List<ResultData<String, Object>> list=new ArrayList<>();
        try {
            ResultData<String, Object> mp=null;
            for (Map<String, Object> map : content) {
                mp=new ResultData<>();
                mp.putAll(map);
                if(map.get("money")!=null)
                    mp.put("money", Double.valueOf(new String(RSAUtil.decryptByPrivateKey(org.apache.tomcat.util.codec.binary.Base64.decodeBase64(map.get("money").toString()), rsaPrivateKey))));
                mp.put("phone", new String(RSAUtil.decryptByPrivateKey(org.apache.tomcat.util.codec.binary.Base64.decodeBase64(map.get("phone").toString()), rsaPrivateKey)));
                list.add(mp);
            }
        } catch (Exception e) {
            log.error("{}", e);
        }
        return list;
    }

    @RequestMapping(value = "/stu/add", method = RequestMethod.POST)
    @LoggerManage(description = "添加學生")
    public Response addClass(Model model, @RequestBody Student student)
    {
        student.setCreateTime(new Date());
        student.setIsDelete(IsDelete.YES);
        Student save = studentService.save(student);
        if (save != null) return new Response(ExceptionMsg.SUCCESS);
        else return new Response(ExceptionMsg.FAILED);
    }

    @RequestMapping(value = "/addStu", method = RequestMethod.GET)
    @LoggerManage(description = "进入添加學生")
    public ModelAndView addStu(Model model)
    {
        ModelAndView addclass = getView("addstu");
        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        List<Clazz> view = clazzRepository.findClazzByIsDeleteAndUserId(IsDelete.YES, attribute.getId());
        addclass.addObject("clazz", view);
        Long cid = 0L;
        if (view != null && view.size() > 0 && view.get(0) != null)
            cid = view.get(0).getId();
        addclass.addObject("defaultFavorties", cid);
        return addclass;
    }


    @RequestMapping(value = "/editStudents", method = RequestMethod.GET)
    @LoggerManage(description = "进入修改學生")
    public ModelAndView editStudents(Model model, @RequestParam("id") String id) throws Exception
    {
        ModelAndView addclass = getView("editstu");
        Optional<Student> byId = studentService.findById(Long.valueOf(id));
        Student stu = byId.get();
        stu.setPhone(new String(RSAUtil.decryptByPrivateKey(org.apache.tomcat.util.codec.binary.Base64.decodeBase64(stu.getPhone()), rsaPrivateKey)));
        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        List<Clazz> view = clazzRepository.findClazzByIsDeleteAndUserId(IsDelete.YES, attribute.getId());
        addclass.addObject("clazz", view);
        addclass.addObject("defaultFavorties", stu.getClassId());
        addclass.addObject("stu", stu);
        return addclass;
    }

    @RequestMapping(value = "/stu/update", method = RequestMethod.POST)
    @LoggerManage(description = "修改學生")
    public Response updateClass(Model model, @RequestBody Student student)
    {
        int save = studentService.update(student.getStuCode(), student.getStuName(), student.getSex(), student.getClassId(), student.getJiazhang(), student.getPhone(), student.getId());
        if (save > 0) return new Response(ExceptionMsg.SUCCESS);
        else return new Response(ExceptionMsg.FAILED);
    }

    @RequestMapping(value = "/stu/del", method = RequestMethod.POST)
    @LoggerManage(description = "删除學生")
    public Response delClass(Model model, @RequestParam("id") String id) throws Exception
    {
        PinMoney pinMoneyByStuId = moneyService.findPinMoneyByStuId(Long.valueOf(id));

        byte[] bytes = RSAUtil.decryptByPrivateKey(org.apache.tomcat.util.codec.binary.Base64.decodeBase64(pinMoneyByStuId.getMoney()), rsaPrivateKey);
        Double detailMoney = Double.valueOf(new String(bytes));
        if (pinMoneyByStuId != null && detailMoney < 0) {
            return new Response("000010", "还有钱未还，不能删除！");
        }
        int save = studentService.del(Long.valueOf(id));
        if (save > 0) return new Response(ExceptionMsg.SUCCESS);
        else return new Response(ExceptionMsg.FAILED);
    }


}
