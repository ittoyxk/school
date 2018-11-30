package com.xk.web;

import com.xk.common.Const;
import com.xk.common.aop.LoggerManage;
import com.xk.domain.*;
import com.xk.domain.result.ExceptionMsg;
import com.xk.domain.result.Response;
import com.xk.domain.result.ResponseData;
import com.xk.domain.result.ResultData;
import com.xk.service.ClazzService;
import com.xk.service.GroupContactService;
import com.xk.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaokang on 2018/8/7.
 */
@Slf4j
@RestController
@RequestMapping("/")
public class GroupContactController extends BaseController
{

    @Autowired
    ClazzService clazzRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    GroupContactService contactRepository;

    @RequestMapping(value = "/getStudentByCid", method = RequestMethod.GET)
    @LoggerManage(description = "进入添加分组")
    public ModelAndView getStudentByCid(Model model, @RequestParam("id") String cid)
    {
        ModelAndView addclass = getView("addgroup");
        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        List<Clazz> view = clazzRepository.findClazzByIsDeleteAndUserId(IsDelete.YES, attribute.getId());
        if (cid == null || "".equals(cid)&&view!=null&&view.size()>0)
            cid = Long.toString(view.get(0).getId());
        else
            cid="0";
        addclass.addObject("clazz", view);
        addclass.addObject("defaultFavorties", cid);


        List<ResultData<String, Object>> students = studentService.getStudentByCid(attribute.getId(), Long.valueOf(cid));
        addclass.addObject("students", students);
        return addclass;
    }

    @RequestMapping(value = "/group/getStudentByCid", method = RequestMethod.POST)
    @LoggerManage(description = "进入添加分组")
    public Response getStudentByCid(@RequestBody String cid)
    {
        ResponseData response;

        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        List<ResultData<String, Object>> students = studentService.getStudentByCid(attribute.getId(), Long.valueOf(cid));
        if (students != null && students.size() > 0) {
            response = new ResponseData(ExceptionMsg.SUCCESS);
            response.setData(students);
        } else {
            response = new ResponseData(ExceptionMsg.FAILED);
        }
        return response;
    }

    @RequestMapping(value = "/findGroup", method = RequestMethod.GET)
    @LoggerManage(description = "分组首页")
    public ModelAndView index(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size)
    {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

		User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        Page<ResultData<String, Object>> groupContactsByIsDelete = contactRepository.findGroupContactsByIsDelete(IsDelete.YES,attribute.getId(), pageable);

        model.addAttribute("group", groupContactsByIsDelete.getContent());
        model.addAttribute("uri", "/findGroup");
        model.addAttribute("id", 0);
        model.addAttribute("page", page >= 4 ? page + 1 : 5);
        return getView("group");
    }

    @RequestMapping(value = "/findGroupById", method = RequestMethod.GET)
    @LoggerManage(description = "分组首页")
    public ModelAndView findGroupById(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, @RequestParam("id") String gId)
    {
        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        Map<String, Object> groupContactsByIsDelete = contactRepository.findGroupContactsById(IsDelete.YES,attribute.getId(), Long.valueOf(gId));

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(groupContactsByIsDelete);
        model.addAttribute("group", list);
        model.addAttribute("uri", "/findGroupById");
        model.addAttribute("id", gId);
        model.addAttribute("page", page >= 4 ? page + 1 : 5);
        return getView("group");
    }


    @RequestMapping(value = "/group/del", method = RequestMethod.POST)
    @LoggerManage(description = "删除分组")
    public Response del(Model model, @RequestParam("id") String id)
    {
        try {
            contactRepository.del(Long.valueOf(id));
            return new Response(ExceptionMsg.SUCCESS);
        } catch (Exception e) {
            log.error("del Exception:{} ", e);
        }
        return new Response(ExceptionMsg.FAILED);
    }

    @RequestMapping(value = "/group/add", method = RequestMethod.POST)
    @LoggerManage(description = "add分组")
    public Response add(Model model, @RequestBody GroupContacts contacts)
    {
        contacts.setCreateTime(new Date());
        contacts.setIsDelete(IsDelete.YES);

        String stuId = contacts.getStuId();
        String[] split = stuId.split(",");
        contacts.setCounts(split.length);
        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        contacts.setUserId(attribute.getId());
        GroupContacts save = contactRepository.save(contacts);
        if (save != null) return new Response(ExceptionMsg.SUCCESS);
        else return new Response(ExceptionMsg.FAILED);
    }

    @RequestMapping(value = "/updateGroup", method = RequestMethod.GET)
    @LoggerManage(description = "进入修改分组")
    public ModelAndView edit(Model model, @RequestParam("id") String cid, @RequestParam("gid") String gid)
    {
        ModelAndView addclass = getView("showgroup");
        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        List<Clazz> view = clazzRepository.findClazzByIsDeleteAndUserId(IsDelete.YES, attribute.getId());
        addclass.addObject("clazz", view);
        addclass.addObject("defaultFavorties", cid);

        List<ResultData<String, Object>> students = studentService.getStudentByCid(attribute.getId(), Long.valueOf(cid));
        addclass.addObject("students", students);

        GroupContacts byId = contactRepository.getOne(Long.valueOf(gid));
        addclass.addObject("groupContacts", byId);
        return addclass;
    }

    @RequestMapping(value = "/group/update", method = RequestMethod.POST)
    @LoggerManage(description = "update分组")
    public Response update(Model model, @RequestBody GroupContacts contacts)
    {
        String stuId = contacts.getStuId();
        String[] split = stuId.split(",");
        contacts.setCounts(split.length);
        int save = contactRepository.update(contacts.getGroupName(), contacts.getClassId(), contacts.getCounts(), contacts.getStuId(), contacts.getId());
        if (save > 0) return new Response(ExceptionMsg.SUCCESS);
        else return new Response(ExceptionMsg.FAILED);
    }

    @RequestMapping(value = "/getGroup", method = RequestMethod.GET)
    @LoggerManage(description = "查看分组")
    public ModelAndView findStudentById(@RequestParam("gid") String gid)
    {
        ModelAndView addclass = getView("showgroup2");
        User attribute = (User) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        List<Clazz> view = clazzRepository.findClazzByIsDeleteAndUserId(IsDelete.YES, attribute.getId());
        addclass.addObject("clazz", view);
        GroupContacts byId = contactRepository.getOne(Long.valueOf(gid));
        addclass.addObject("defaultFavorties", byId.getClassId());

        List<Student> students = studentService.findStudentByIds(byId.getStuId());
        addclass.addObject("students", students);

        addclass.addObject("groupContacts", byId);
        return addclass;
    }
}
