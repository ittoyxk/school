package com.xk.web;

import com.alibaba.fastjson.JSONObject;
import com.xk.common.Const;
import com.xk.common.aop.LoggerManage;
import com.xk.domain.PinMoney;
import com.xk.domain.PinMoneyDetail;
import com.xk.domain.Student;
import com.xk.domain.User;
import com.xk.domain.result.ExceptionMsg;
import com.xk.domain.result.Response;
import com.xk.domain.result.ResultData;
import com.xk.service.MoneyService;
import com.xk.service.StudentService;
import com.xk.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xiaokang on 2018/8/6.
 */
@Slf4j
@RestController
@RequestMapping("/")
public class MoneyController extends BaseController {


    @Autowired
    MoneyService moneyService;

    @Autowired
    StudentService studentService;

    @Value("${rsa.PrivateKey}")
    String rsaPrivateKey;
    @Value("${rsa.PublicKey}")
    String rsaPublicKey;


    @RequestMapping(value = "/money", method = RequestMethod.GET)
    @LoggerManage(description = "进入個人存款")
    public ModelAndView money(Model model, @RequestParam("id") String stuId) throws Exception
    {
        Optional<Student> byId = studentService.findById(Long.valueOf(stuId));
        model.addAttribute("stu", byId.get());

        PinMoney pinMoney = moneyService.findPinMoneyByStuId(Long.valueOf(stuId));
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(pinMoney));
        if (pinMoney != null && pinMoney.getMoney() != null) {
            jsonObject.put("money",Double.valueOf(new String(RSAUtil.decryptByPrivateKey(Base64.decodeBase64(pinMoney.getMoney()), rsaPrivateKey))));
        }
        model.addAttribute("pinMoney", jsonObject);

        return getView("money");
    }

    @RequestMapping(value = "/money/update", method = RequestMethod.POST)
    @LoggerManage(description = "存钱或取钱")
    public Response addMoney(Model model, @RequestBody PinMoneyDetail moneyDetail) throws Exception
    {
        moneyDetail.setCreateTime(new Date());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");

        String content = "";
        if (moneyDetail.getFlag() == 1)
            content = "存入";
        else if (moneyDetail.getFlag() == 0)
            content = "取出";
        byte[] bytes = RSAUtil.decryptByPrivateKey(Base64.decodeBase64(moneyDetail.getMoney()), rsaPrivateKey);
        
        moneyDetail.setContent("在" + sd.format(new Date()) + content + (new String(bytes)) + "元");
        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        moneyDetail.setUserId(attribute.getId());
        PinMoney save = moneyService.save(moneyDetail);
        if (save != null)
            return new Response(ExceptionMsg.SUCCESS);
        else return new Response(ExceptionMsg.FAILED);
    }

    @RequestMapping(value = "/moneyDetail", method = RequestMethod.GET)
    @LoggerManage(description = "进入學生信息")
    public ModelAndView moneyDetail(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "20") Integer size) throws Exception
    {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        List<ResultData<String, Object>> all = moneyService.findAll(attribute.getId(), pageable);
        List<ResultData<String, Object>> descript = descript(all);
        model.addAttribute("money", descript);
        model.addAttribute("uri", "/moneyDetail");
        model.addAttribute("id", 0);
        model.addAttribute("page", page >= 4 ? page + 1 : 5);
        return getView("moneyDetail");
    }

    private List<ResultData<String, Object>> descript(List<ResultData<String, Object>> all) throws Exception
    {
        List<ResultData<String, Object>> list = new ArrayList<>();
        ResultData<String, Object> mp = null;
        for (ResultData<String, Object> map : all) {
            mp = new ResultData<>();
            mp.putAll(map);
            if (map.get("money") != null)
                mp.put("money", Double.valueOf(new String(RSAUtil.decryptByPrivateKey(Base64.decodeBase64(map.get("money").toString()), rsaPrivateKey))));
            if (map.get("moneyDetail") != null)
                mp.put("moneyDetail", Double.valueOf(new String(RSAUtil.decryptByPrivateKey(Base64.decodeBase64(map.get("moneyDetail").toString()), rsaPrivateKey))));
            list.add(mp);
        }
        return list;
    }

    @RequestMapping(value = "/moneyDetailById", method = RequestMethod.GET)
    @LoggerManage(description = "进入學生信息")
    public ModelAndView moneyDetailByStuId(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "20") Integer size, @RequestParam("id") String stuId) throws Exception
    {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        List<ResultData<String, Object>> all = moneyService.findPinMoneyDetailByStuId(Long.valueOf(stuId), attribute.getId(), pageable);
        List<ResultData<String, Object>> descript = descript(all);
        model.addAttribute("money", descript);
        model.addAttribute("uri", "/moneyDetailById");
        model.addAttribute("id", stuId);
        model.addAttribute("page", page >= 4 ? page + 1 : 5);
        return getView("moneyDetail");
    }

    @RequestMapping(value = "/findMoneyByMoney", method = RequestMethod.GET)
    @LoggerManage(description = "查询欠款的学生")
    public ModelAndView findMoneyByMoney(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "20") Integer size) throws Exception
    {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        List<ResultData<String, Object>> all = moneyService.findMoneyByMoney(attribute.getId(), pageable);

        List<ResultData<String, Object>> list = new ArrayList<>();
        ResultData<String, Object> mp = null;
        for (ResultData<String, Object> map : all) {
            if (map.get("money") != null) {
                Double money = Double.valueOf(new String(RSAUtil.decryptByPrivateKey(Base64.decodeBase64(map.get("money").toString()), rsaPrivateKey)));
                if(money<0){
                    mp = new ResultData<>();
                    mp.putAll(map);
                    mp.put("money", money);
                    list.add(mp);
                }
            }
        }
        model.addAttribute("money", list);
        model.addAttribute("uri", "/findMoneyByMoney");
        model.addAttribute("id", 0);
        model.addAttribute("page", page >= 4 ? page + 1 : 5);
        return getView("moneyDetail2");
    }

}
