package com.xk.service;

import com.xk.domain.GroupContacts;
import com.xk.domain.IsDelete;
import com.xk.domain.result.ResultData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by xiaokang on 2018/8/7.
 */
public interface GroupContactService
{

    Page<ResultData<String, Object>> findGroupContactsByIsDelete(IsDelete isDelete, Long userId, Pageable pageable);

    ResultData<String, Object> findGroupContactsById(IsDelete isDelete, Long userId, Long gId);
	
    int update(String groupName, Long classId, int counts, String stuId, Long id);

    List<GroupContacts> findGroupContactsByUserIdAndIsDelete(Long id, IsDelete yes);

    int del(Long id);

    GroupContacts save(GroupContacts contacts);

    GroupContacts getOne(Long aLong);
}
