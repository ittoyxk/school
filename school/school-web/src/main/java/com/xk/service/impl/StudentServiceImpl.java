package com.xk.service.impl;

import com.xk.common.redis.ShareJedisService;
import com.xk.domain.Clazz;
import com.xk.domain.IsDelete;
import com.xk.domain.Student;
import com.xk.domain.result.ResultData;
import com.xk.repository.ClazzRepository;
import com.xk.repository.StudentRepository;
import com.xk.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by xiaokang on 2018/8/6.
 */
@Service
public class StudentServiceImpl implements StudentService {

    Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ClazzRepository clazzRepository;

    @Autowired
    ShareJedisService redisUtil;

//    @Cacheable(value = "stu", key = "#userId+''+#pageable", unless = "#result == null")
    @Override
    public Page<ResultData<String, Object>> findStudentByUserId(Long userId, Pageable pageable)
    {
        return studentRepository.findStudentByUserId(userId, pageable);
    }

//    @CachePut(value = "stu", key = "#stuName")
    @Override
    public int update(String stuCode, String stuName, int sex, long classId, String jiazhang, String phone, Long id)
    {
        return studentRepository.update(stuCode, stuName, sex, classId, jiazhang, phone, id);
    }

//    @CacheEvict(value = "stu", key = "#id", allEntries = true)
    @Transactional
    @Override
    public int del(Long id)
    {
        Student one = studentRepository.getOne(id);
        Clazz one1 = clazzRepository.getOne(one.getClassId());
        clazzRepository.updateCount(one1.getCounts() - 1, one1.getId());
        return studentRepository.del(id);
    }

//    @Cacheable(value = "stu", key = "#id+''+#aLong+''+#pageable", unless = "#result == null")
    @Override
    public Page<ResultData<String, Object>> findStudentByUserIdAndClassId(Long id, Long aLong, Pageable pageable)
    {
        return studentRepository.findStudentByUserIdAndClassId(id, aLong, pageable);
    }


//    @Cacheable(value = "stu", key = "#id+''+#id2")
    @Override
    public ResultData<String, Object> findStudentById(Long id, Long id2)
    {
        return studentRepository.findStudentById(id, id2);
    }

//    @Cacheable(value = "stu", key = "#id+''+#stuName+''+#pageable", unless = "#result == null")
    @Override
    public Page<ResultData<String, Object>> findStudentByName(Long id, String stuName, Pageable pageable)
    {
        return studentRepository.findStudentByName(id, stuName, pageable);
    }

//    @CachePut(value = "stu", key = "#student.id", unless = "#result == null")
    @Transactional
    @Override
    public Student save(Student student)
    {
        Clazz clazz = clazzRepository.getOne(student.getClassId());
        clazz.setCounts(clazz.getCounts() + 1);
        clazzRepository.updateCount(clazz.getCounts(), student.getClassId());
        return studentRepository.save(student);
    }

//    @Cacheable(value = "stu", key = "#aLong", unless = "#result == null")
    @Override
    public Optional<Student> findById(Long aLong)
    {
        return studentRepository.findById(aLong);
    }

//    @Cacheable(value = "stu", key = "#id+''+#aLong")
    @Override
    public List<ResultData<String, Object>> getStudentByCid(Long id, Long aLong)
    {
        return studentRepository.findStudentByUserIdAndClassId(id, aLong);
    }

//    @Cacheable(value = "stu", key = "#s", unless = "#result == null")
    @Override
    public List<Student> findStudentByIds(String s)
    {
        List<Long> strings = new ArrayList<Long>();
        String[] split = s.split(",");
        for (String id : split) {
            strings.add(Long.valueOf(id));
        }
        return studentRepository.findStudentByIsDeleteAndIdIn(IsDelete.YES, strings);
    }
}
