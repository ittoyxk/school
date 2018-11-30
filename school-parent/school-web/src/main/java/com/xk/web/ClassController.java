package com.xk.web;

import com.xk.common.Const;
import com.xk.common.aop.LoggerManage;
import com.xk.domain.Clazz;
import com.xk.domain.IsDelete;
import com.xk.domain.User;
import com.xk.domain.result.ExceptionMsg;
import com.xk.domain.result.Response;
import com.xk.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * Created by hengxiaokang
 * Date:2018/6/24
 * Time:15:11
 */
@Slf4j
@RestController
@RequestMapping("/")
public class ClassController extends BaseController
{

    @Autowired
    ClazzService clazzRepository;

    @RequestMapping(value = "/class", method = RequestMethod.GET)
    @LoggerManage(description = "班级管理首页")
    public ModelAndView index(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size)
    {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        User attribute =(User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        Page<Clazz> view = clazzRepository.findClazzByIsDeleteAndUserId(IsDelete.YES, attribute.getId(),pageable);

        model.addAttribute("class", view.getContent());
        model.addAttribute("size", view.getSize());

        model.addAttribute("uri", "/class");
        model.addAttribute("id", 0);
        model.addAttribute("page", page >= 4 ? page + 1 : 5);
        return getView("class");
    }

    @RequestMapping(value = "/class/add", method = RequestMethod.POST)
    @LoggerManage(description = "添加班级")
    public Response addClass(Model model, @RequestBody Clazz clazz)
    {
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmSS");
        clazz.setClassCode("C_" + sd.format(new Date()));
        clazz.setCreateTime(new Date());
        clazz.setDelete(IsDelete.YES);
        User attribute =(User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        clazz.setUserid(attribute.getId());
        Clazz save = clazzRepository.save(clazz);
        if (save != null) return new Response(ExceptionMsg.SUCCESS);
        else return new Response(ExceptionMsg.FAILED);
    }

    @RequestMapping(value = "/addclass", method = RequestMethod.GET)
    @LoggerManage(description = "进入添加班级")
    public ModelAndView addclass(Model model)
    {
        return getView("addclass");
    }

    @RequestMapping(value = "/editClass", method = RequestMethod.GET)
    @LoggerManage(description = "进入修改班级")
    public ModelAndView editClass(Model model,@RequestParam("id") String id)
    {
        ModelAndView addclass = getView("editclass");
        Optional<Clazz> byId = clazzRepository.findById(Long.valueOf(id));
        Clazz clazz = byId.get();
        addclass.addObject("clazz",clazz);
        return addclass;
    }

    @RequestMapping(value = "/class/update", method = RequestMethod.POST)
    @LoggerManage(description = "修改班级")
    public Response updateClass(Model model, @RequestBody Clazz clazz)
    {
        clazz.setUpdateTime(new Date());
        int save = clazzRepository.update(clazz.getClassName(),clazz.getClassAdmin(),clazz.getUpdateTime(),clazz.getId());
        if (save >0) return new Response(ExceptionMsg.SUCCESS);
        else return new Response(ExceptionMsg.FAILED);
    }

    @RequestMapping(value = "/class/del", method = RequestMethod.POST)
    @LoggerManage(description = "删除班级")
    public Response delClass(Model model, @RequestParam("id") String id)
    {
        Clazz one = clazzRepository.getOne(Long.valueOf(id));
        if(one!=null&&one.getCounts()>0){
            return new Response("000009","班级还有学生，无法删除");
        }
        int save = clazzRepository.del(Long.valueOf(id));
        if (save >0) return new Response(ExceptionMsg.SUCCESS);
        else return new Response(ExceptionMsg.FAILED);
    }
}