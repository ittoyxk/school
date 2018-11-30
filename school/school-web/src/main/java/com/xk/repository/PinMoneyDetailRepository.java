package com.xk.repository;

import com.xk.domain.PinMoneyDetail;
import com.xk.domain.result.ResultData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by xiaokang on 2018/8/6.
 */
public interface PinMoneyDetailRepository extends JpaRepository<PinMoneyDetail, Long> {

    @Query("SELECT c.className as className,s.id as stuId,s.stuName as stuName,pm.money as money,md.flag as flag,md.money as moneyDetail,md.createTime as createTime,md.remark as remark, md.content as content FROM PinMoneyDetail md INNER JOIN Student s ON s.id = md.stuId INNER JOIN Clazz c ON c.id = s.classId INNER JOIN PinMoney pm ON s.id = pm.stuId where c.isDelete='YES' and s.isDelete='YES' and c.userId=?1")
    Page<ResultData<String, Object>> findPinMoneyDetail(Long userId,Pageable pageable);


    @Query("SELECT c.className as className,s.id as stuId,s.stuName as stuName,pm.money as money,md.flag as flag,md.money as moneyDetail,md.createTime as createTime,md.remark as remark, md.content as content FROM PinMoneyDetail md INNER JOIN Student s ON s.id = md.stuId INNER JOIN Clazz c ON c.id = s.classId INNER JOIN PinMoney pm ON s.id = pm.stuId where c.isDelete='YES' and s.isDelete='YES' and md.stuId=?1 and c.userId=?2")
    Page<ResultData<String, Object>> findPinMoneyDetailByStuId(Long stuId, Long userId, Pageable pageable);

}
