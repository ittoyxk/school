package com.xk.repository;

import com.xk.domain.PinMoney;
import com.xk.domain.result.ResultData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by xiaokang on 2018/8/6.
 */
public interface PinMoneyRepository extends JpaRepository<PinMoney, Long> {
    PinMoney findPinMoneyByStuId(Long aLong);


    @Modifying(clearAutomatically=true)
    @Query("update PinMoney set money=?1 where id=?2 ")
    void updatePinMoneyById(String money, Long id);

    @Query("SELECT c.className as className,s.id as stuId,s.stuName as stuName,pm.money as money FROM PinMoney pm INNER JOIN Student s ON s.id = pm.stuId INNER JOIN Clazz c ON c.id = s.classId where c.userId=?1")
    Page<ResultData<String, Object>> findPinMoneyByMoney(Long userId, Pageable pageable);
}
