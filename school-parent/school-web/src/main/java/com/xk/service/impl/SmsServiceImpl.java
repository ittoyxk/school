package com.xk.service.impl;

import com.xk.service.SmsService;
import com.xk.utils.SMSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * Created by xiaokang on 2018/8/7.
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Async
    @Override
    public void sendSMS(String phone, String centont) throws InterruptedException, UnsupportedEncodingException
    {
        if (phone == null || "".equals(phone) || centont == null)
            return;
        log.info("短信发送内容:{}----{}", phone, centont);
        String s = SMSUtils.sendSMS(phone, centont, "", "", "", "");
        if (Long.valueOf(s) > 0)
            log.info("发送成功{}", Thread.currentThread().getName());
        else
            log.info("发送失败");
    }
}
