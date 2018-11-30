package com.xk.service;

import com.xk.domain.PinMoney;
import com.xk.domain.PinMoneyDetail;
import com.xk.domain.result.ResultData;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by xiaokang on 2018/8/6.
 */
public interface MoneyService {

    PinMoney findPinMoneyByStuId(Long aLong);

    PinMoney save(PinMoneyDetail moneyDetail);

    List<ResultData<String, Object>> findAll(Long userId,Pageable pageable);

    List<ResultData<String,Object>> findPinMoneyDetailByStuId(Long aLong,Long userId, Pageable pageable);

    List<ResultData<String,Object>> findMoneyByMoney(Long userId, Pageable pageable);
}
