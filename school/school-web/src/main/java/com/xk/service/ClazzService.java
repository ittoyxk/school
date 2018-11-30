package com.xk.service;

import com.xk.domain.Clazz;
import com.xk.domain.IsDelete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by hengxiaokang
 * Date:2018/6/24
 * Time:17:19
 */
public interface ClazzService
{
    Page<Clazz> findClazzByIsDeleteAndUserId(IsDelete isDelete, Long userId, Pageable pageable);


    List<Clazz> findClazzByIsDeleteAndUserId(IsDelete isDelete, Long userId);

    int update(String className, String classAdmin, Date updateTime, Long id);

    int del(Long id);

    int updateCount(int counts, Long id);

    Clazz save(Clazz clazz);

    Optional<Clazz> findById(Long aLong);

    Clazz getOne(Long aLong);
}
