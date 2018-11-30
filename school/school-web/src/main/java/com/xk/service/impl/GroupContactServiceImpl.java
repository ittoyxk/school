package com.xk.service.impl;

import com.xk.domain.GroupContacts;
import com.xk.domain.IsDelete;
import com.xk.domain.result.ResultData;
import com.xk.repository.GroupContactRepository;
import com.xk.service.GroupContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hengxiaokang
 * Date:2018/8/16
 * Time:16:50
 */
@Service
public class GroupContactServiceImpl implements GroupContactService
{

    @Autowired
    GroupContactRepository contactRepository;

//    @Cacheable(value = "group", key = "#isDelete+''+#userId+''+#pageable")
    @Override
    public Page<ResultData<String, Object>> findGroupContactsByIsDelete(IsDelete isDelete, Long userId, Pageable pageable)
    {
        return contactRepository.findGroupContactsByIsDelete(isDelete, userId, pageable);
    }

//    @Cacheable(value = "group", key = "#isDelete+''+#userId+''+#gId")
    @Override
    public ResultData<String, Object> findGroupContactsById(IsDelete isDelete, Long userId, Long gId)
    {
        return contactRepository.findGroupContactsById(isDelete, userId, gId);
    }

//    @CachePut(value = "group", key = "#id", unless = "#result == null")
    @Override
    public int update(String groupName, Long classId, int counts, String stuId, Long id)
    {
        return contactRepository.update(groupName, classId, counts, stuId, id);
    }

//    @Cacheable(value = "group", key = "#id+''+#yes")
    @Override
    public List<GroupContacts> findGroupContactsByUserIdAndIsDelete(Long id, IsDelete yes)
    {
        return contactRepository.findGroupContactsByUserIdAndIsDelete(id, yes);
    }

//    @CacheEvict(value = "group", key = "#id", allEntries = true)
    @Override
    public int del(Long id)
    {
        return contactRepository.del(id);
    }

//    @CachePut(value = "group", key = "#contacts", unless = "#result == null")
    @Override
    public GroupContacts save(GroupContacts contacts)
    {
        return contactRepository.save(contacts);
    }

//    @Cacheable(value = "group", key = "#aLong")
    @Override
    public GroupContacts getOne(Long aLong)
    {
        return contactRepository.getOne(aLong);
    }
}