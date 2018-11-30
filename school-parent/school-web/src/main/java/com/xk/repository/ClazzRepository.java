package com.xk.repository;

import com.xk.domain.Clazz;
import com.xk.domain.IsDelete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by hengxiaokang
 * Date:2018/6/24
 * Time:17:19
 */
public interface ClazzRepository extends JpaRepository<Clazz, Long>
{
//    @Query(" select userId from Clazz where isDelete=?1 and userId=?2 ")
    Page<Clazz> findClazzByIsDeleteAndUserId(IsDelete isDelete,Long userId,Pageable pageable);


    List<Clazz> findClazzByIsDeleteAndUserId(IsDelete isDelete, Long userId);

    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update Clazz set className=?1,classAdmin=?2,updateTime=?3 where id=?4")
    int update(String className,String classAdmin,Date updateTime,Long id);

    @Transactional
    @Modifying(clearAutomatically=true)
    @Query("update Clazz set isDelete='NO' where id=?1")
    int del(Long id);


    @Modifying(clearAutomatically=true)
    @Query("update Clazz set counts=?1 where id=?2 ")
    int updateCount(int counts,Long id);
}
