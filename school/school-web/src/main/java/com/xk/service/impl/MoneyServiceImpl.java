package com.xk.service.impl;

import com.xk.domain.PinMoney;
import com.xk.domain.PinMoneyDetail;
import com.xk.domain.result.ResultData;
import com.xk.repository.PinMoneyDetailRepository;
import com.xk.repository.PinMoneyRepository;
import com.xk.service.MoneyService;
import com.xk.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by xiaokang on 2018/8/6.
 */
@Slf4j
@Service
public class MoneyServiceImpl implements MoneyService {


    @Autowired
    PinMoneyDetailRepository detailRepository;

    @Autowired
    PinMoneyRepository pinMoneyRepository;

    @Value("${rsa.PrivateKey}")
    String rsaPrivateKey;
    @Value("${rsa.PublicKey}")
    String rsaPublicKey;

//    @Cacheable(value = "money", key = "#aLong")
    @Override
    public PinMoney findPinMoneyByStuId(Long aLong)
    {
        return pinMoneyRepository.findPinMoneyByStuId(aLong);
    }

//    @CachePut(value = "money", key = "#moneyDetail.stuId", unless = "#result == null")
    @Transactional
    @Override
    public PinMoney save(PinMoneyDetail moneyDetail)
    {
        try {
            PinMoney pinMoneyByStuId = pinMoneyRepository.findPinMoneyByStuId(moneyDetail.getStuId());
            byte[] bytes = RSAUtil.decryptByPrivateKey(Base64.decodeBase64(moneyDetail.getMoney()), rsaPrivateKey);
            Double  detailMoney = Double.valueOf(new String(bytes));
            if (pinMoneyByStuId == null) {
                pinMoneyByStuId = new PinMoney();
                pinMoneyByStuId.setStuId(moneyDetail.getStuId());
                if (moneyDetail.getFlag() == 0) {
                    Double l = -1 * detailMoney;
                    byte[] bytes1 = RSAUtil.encryptByPublicKey(Double.toString(l).getBytes(), rsaPublicKey);
                    pinMoneyByStuId.setMoney(Base64.encodeBase64String(bytes1));
                }
                else
                    pinMoneyByStuId.setMoney(moneyDetail.getMoney());

                pinMoneyRepository.save(pinMoneyByStuId);
            } else {
                byte[] bytes1 = RSAUtil.decryptByPrivateKey(Base64.decodeBase64(pinMoneyByStuId.getMoney()), rsaPrivateKey);
                Double  pinMoney = Double.valueOf(new String(bytes1));
                if (moneyDetail.getFlag() == 0) {
                    Double l = pinMoney - detailMoney;
                    byte[] bytes2 = RSAUtil.encryptByPublicKey(Double.toString(l).getBytes(), rsaPublicKey);
                    pinMoneyByStuId.setMoney(Base64.encodeBase64String(bytes2));
                }
                else {
                    Double l = pinMoney + detailMoney;
                    byte[] bytes2 = RSAUtil.encryptByPublicKey(Double.toString(l).getBytes(), rsaPublicKey);
                    pinMoneyByStuId.setMoney(Base64.encodeBase64String(bytes2));
                }

                pinMoneyRepository.updatePinMoneyById(pinMoneyByStuId.getMoney(),pinMoneyByStuId.getId());
            }
            detailRepository.save(moneyDetail);

            return pinMoneyByStuId;
        } catch (Exception e) {
            log.error("{}",e);
        }
        return null;
    }

//    @Cacheable(value = "money", key = "#userId+''+#pageable")
    @Override
    public List<ResultData<String, Object>> findAll(Long userId, Pageable pageable)
    {
        Page<ResultData<String, Object>> all = detailRepository.findPinMoneyDetail(userId,pageable);
        return all.getContent();
    }

//    @Cacheable(value = "money", key = "#aLong+''+userId+''+#pageable")
    @Override
    public List<ResultData<String, Object>> findPinMoneyDetailByStuId(Long aLong,Long userId, Pageable pageable)
    {
        return detailRepository.findPinMoneyDetailByStuId(aLong,userId, pageable).getContent();
    }

//    @Cacheable(value = "money", key = "#userId+''+#pageable")
    @Override
    public List<ResultData<String, Object>> findMoneyByMoney(Long userId,Pageable pageable)
    {
        return pinMoneyRepository.findPinMoneyByMoney(userId,pageable).getContent();
    }


}
