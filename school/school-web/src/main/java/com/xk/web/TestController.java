package com.xk.web;

import com.xk.common.aop.LoggerManage;
import com.xk.common.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hengxiaokang
 * Date:2018/6/24
 * Time:15:35
 */
@Slf4j
@RestController
@RequestMapping("/redis")
public class TestController extends BaseController
{
    @Autowired
    RedisUtil redisUtil;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @LoggerManage(description = "get")
    public Object get(@RequestParam("key") String key)
    {
        return redisUtil.get(key);
    }

    @RequestMapping(value = "/set", method = RequestMethod.GET)
    @LoggerManage(description = "set")
    public Object set(@RequestParam("key") String key,@RequestParam("value") String value,@RequestParam("time") String time)
    {
        return redisUtil.set(key,value,Long.valueOf(time));
    }

    @RequestMapping(value = "/ttl", method = RequestMethod.GET)
    @LoggerManage(description = "set")
    public Long getTTL(@RequestParam("key") String key)
    {
        return redisUtil.getTTL(key);
    }
}