package com.xk.service;

import com.xk.domain.Student;
import com.xk.domain.result.ResultData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Created by xiaokang on 2018/8/6.
 */
public interface StudentService {

    Page<ResultData<String, Object>> findStudentByUserId(Long userId, Pageable pageable);

    int update(String stuCode, String stuName, int sex, long classId, String jiazhang, String phone, Long id);

    int del(Long id);

    Page<ResultData<String, Object>> findStudentByUserIdAndClassId(Long id, Long aLong, Pageable pageable);

    ResultData<String,Object> findStudentById(Long id, Long id2);

    Page<ResultData<String,Object>> findStudentByName(Long id, String stuName, Pageable pageable);

    Student save(Student student);

    Optional<Student> findById(Long aLong);

    List<ResultData<String,Object>> getStudentByCid(Long id, Long aLong);

    List<Student> findStudentByIds(String s);
}
