package com.xk.repository;

import com.xk.domain.IsDelete;
import com.xk.domain.Student;
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
 * Created by hengxiaokang
 * Date:2018/6/24
 * Time:17:19
 */
public interface StudentRepository extends JpaRepository<Student, Long>
{

    @Query("select s.id as id,s.stuCode as stuCode,s.stuName as stuName,s.sex as sex ,s.age as age,s.classId as classid ,s.jiazhang as jiazhang,s.phone as phone ,c.className as className,c.classAdmin as classAdmin,s.createTime as createTime,pm.money as money from Student s INNER JOIN Clazz c ON s.classId = c.id LEFT JOIN PinMoney pm ON s.id = pm.stuId where  c.isDelete='YES' and c.userId=?1 and s.isDelete='YES'")
    Page<ResultData<String,Object>> findStudentByUserId(Long userId , Pageable pageable);

    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update Student set stuCode=?1,stuName=?2,sex=?3,classId=?4,jiazhang=?5 ,phone=?6 where id=?7 ")
    int update(String stuCode, String stuName, int sex, long classId,String jiazhang,String phone,Long id);

    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update Student set isDelete='NO' where id=?1")
    int del(Long id);

    @Query("select s.id as id,s.stuCode as stuCode,s.stuName as stuName,s.sex as sex ,s.age as age,s.classId as classid ,s.jiazhang as jiazhang,s.phone as phone ,c.className as className,c.classAdmin as classAdmin,s.createTime as createTime,pm.money as money from Student s INNER JOIN Clazz c ON s.classId = c.id LEFT JOIN PinMoney pm ON s.id = pm.stuId where  c.isDelete='YES' and c.userId=?1 and s.isDelete='YES' and c.id=?2 ")
    Page<ResultData<String,Object>> findStudentByUserIdAndClassId(Long id, Long aLong, Pageable pageable);

    @Query("select s.id as id,s.stuCode as stuCode,s.stuName as stuName,s.sex as sex ,s.age as age,s.classId as classid ,s.jiazhang as jiazhang,s.phone as phone ,c.className as className,c.classAdmin as classAdmin,s.createTime as createTime,pm.money as money from Student s INNER JOIN Clazz c ON s.classId = c.id LEFT JOIN PinMoney pm ON s.id = pm.stuId where  c.isDelete='YES' and c.userId=?1 and s.isDelete='YES' and s.id=?2 ")
    ResultData<String,Object> findStudentById(Long id,Long id2);

    @Query("select s.id as id,s.stuCode as stuCode,s.stuName as stuName,s.sex as sex ,s.age as age,s.classId as classid ,s.jiazhang as jiazhang,s.phone as phone ,c.className as className,c.classAdmin as classAdmin,s.createTime as createTime,pm.money as money from Student s INNER JOIN Clazz c ON s.classId = c.id LEFT JOIN PinMoney pm ON s.id = pm.stuId where  c.isDelete='YES' and c.userId=?1 and s.isDelete='YES' and s.stuName like ?2 ")
    Page<ResultData<String,Object>> findStudentByName(Long id, String stuName, Pageable pageable);

    @Query("select s.id as id,s.stuCode as stuCode,s.stuName as stuName,s.classId as classid  from Student s INNER JOIN Clazz c ON s.classId = c.id  where  c.isDelete='YES' and c.userId=?1 and s.isDelete='YES' and s.classId=?2 ")
    List<ResultData<String,Object>> findStudentByUserIdAndClassId(Long id, Long aLong);

    List<Student> findStudentByIsDeleteAndIdIn(IsDelete isDelete,List<Long> strings);
}
