package com.xk.common.redis.impl;

import com.xk.common.redis.ShareJedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by hengxiaokang
 * Date:2018/6/15
 * Time:13:09
 */
@Service
public class ShareJedisServiceImpl implements ShareJedisService
{
    @Autowired
    private RedisTemplate redisTemplate;

    public Object getObject(final String key)
    {
        return redisTemplate.execute(new RedisCallback()
        {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException
            {
                byte[] bytes = redisConnection.get(key.getBytes());
                return SerializeUtil.unserialize(bytes);
            }
        });
    }

    public String setObject(final String key, final Object obj, final int seconds)
    {
        return (String) redisTemplate.execute(new RedisCallback()
        {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException
            {
                redisConnection.setEx(key.getBytes(), seconds, SerializeUtil.serialize(obj));
                return key;
            }
        });
    }

    @Override
    public String getString(String key)
    {
        return String.valueOf(redisTemplate.opsForValue().get(key));
    }

    @Override
    public String setString(String key, String obj, int seconds)
    {
        redisTemplate.opsForValue().set(key, obj, seconds, TimeUnit.SECONDS);
        return key;
    }

    @Override
    public Long getTTL(String key)
    {
        return redisTemplate.getExpire(key);
    }

    @Override
    public void del(Object key)
    {
        redisTemplate.delete(key);
    }


    static class SerializeUtil
    {
        public static byte[] serialize(Object object)
        {
            ObjectOutputStream oos = null;
            ByteArrayOutputStream baos = null;
            try {
                // 序列化
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                byte[] bytes = baos.toByteArray();
                return bytes;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public static Object unserialize(byte[] bytes)
        {
            if (bytes == null) return null;
            ByteArrayInputStream bais = null;
            try {
                // 反序列化
                bais = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                return ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}