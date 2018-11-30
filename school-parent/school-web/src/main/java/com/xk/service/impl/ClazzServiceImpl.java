package com.xk.service.impl;

import com.xk.domain.Clazz;
import com.xk.domain.IsDelete;
import com.xk.repository.ClazzRepository;
import com.xk.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by hengxiaokang
 * Date:2018/8/16
 * Time:16:45
 */
@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    ClazzRepository clazzRepository;

//    @Cacheable(value = "clazz", key = "#isDelete+''+#userId+''+#pageable")
    @Override
    public Page<Clazz> findClazzByIsDeleteAndUserId(IsDelete isDelete, Long userId, Pageable pageable)
    {
        return clazzRepository.findClazzByIsDeleteAndUserId(isDelete, userId, pageable);
    }

//    @Cacheable(value = "clazz", key = "#isDelete+''+#userId")
    @Override
    public List<Clazz> findClazzByIsDeleteAndUserId(IsDelete isDelete, Long userId)
    {
        return clazzRepository.findClazzByIsDeleteAndUserId(isDelete, userId);
    }

//    @CachePut(value = "clazz", key = "#id", unless = "#result == null")
    @Override
    public int update(String className, String classAdmin, Date updateTime, Long id)
    {
        return clazzRepository.update(className, classAdmin, updateTime, id);
    }

//    @CacheEvict(value = "clazz", key = "#id", allEntries = true)
    @Override
    public int del(Long id)
    {
        return clazzRepository.del(id);
    }

//    @CachePut(value = "clazz", key = "#id", unless = "#result == null")
    @Override
    public int updateCount(int counts, Long id)
    {
        return clazzRepository.updateCount(counts, id);
    }

//    @CachePut(value = "clazz", key = "#clazz", unless = "#result == null")
    @Override
    public Clazz save(Clazz clazz)
    {
        return clazzRepository.save(clazz);
    }

//    @Cacheable(value = "clazz", key = "#aLong")
    @Override
    public Optional<Clazz> findById(Long aLong)
    {
        return clazzRepository.findById(aLong);
    }

//    @Cacheable(value = "clazz", key = "#aLong")
    @Override
    public Clazz getOne(Long aLong)
    {
        return clazzRepository.getOne(aLong);
    }
}