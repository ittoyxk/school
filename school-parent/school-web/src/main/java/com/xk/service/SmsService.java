package com.xk.service;

import java.io.UnsupportedEncodingException;

/**
 * Created by xiaokang on 2018/8/7.
 */
public interface SmsService {

    void sendSMS(String phone,String centont) throws InterruptedException, UnsupportedEncodingException;
}
