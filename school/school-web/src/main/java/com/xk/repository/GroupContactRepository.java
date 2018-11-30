package com.xk.repository;

import com.xk.domain.GroupContacts;
import com.xk.domain.IsDelete;
import com.xk.domain.result.ResultData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaokang on 2018/8/7.
 */
public interface GroupContactRepository extends JpaRepository<GroupContacts, Long>
{

    @Query("select gc.id as gid,c.id as cid, gc.groupName as groupName,gc.createTime as createTime,gc.counts as counts,c.className as className  from GroupContacts gc inner join Clazz c on c.id=gc.classId where c.isDelete='YES' and gc.isDelete=?1 and gc.userId=?2")
    Page<ResultData<String, Object>> findGroupContactsByIsDelete(IsDelete isDelete, Long userId, Pageable pageable);

    @Query("select gc.id as gid,c.id as cid,gc.groupName as groupName,gc.createTime as createTime,gc.counts as counts,c.className as className  from GroupContacts gc inner join Clazz c on c.id=gc.classId where c.isDelete='YES' and gc.isDelete=?1 and gc.id=?2 and gc.userId=?3 ")
    ResultData<String, Object> findGroupContactsById(IsDelete isDelete,Long userId,Long gId);
	
    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update GroupContacts set groupName=?1,classId=?2,counts=?3,stuId=?4 where id=?5 ")
    int update(String groupName, Long classId, int counts, String stuId, Long id);

    List<GroupContacts> findGroupContactsByUserIdAndIsDelete(Long id, IsDelete yes);

    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update GroupContacts set isDelete='NO' where id=?1 ")
    int del( Long id);
}
