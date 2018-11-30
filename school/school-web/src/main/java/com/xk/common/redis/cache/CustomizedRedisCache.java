package com.xk.common.redis.cache;

import com.xk.common.redis.lock.DistributedLock;
import com.xk.common.redis.lock.RedisDistributedLock;
import com.xk.common.redis.util.SpringContextUtils;
import com.xk.common.redis.util.ThreadTaskUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 自定义的redis缓存
 *
 * @author hengxiaokang
 */
public class CustomizedRedisCache extends RedisCache {

    private static final Logger logger = LoggerFactory.getLogger(CustomizedRedisCache.class);

    private RedisOperations redisOperations;

    private CacheSupport getCacheSupport() {
        return SpringContextUtils.getBean(CacheSupport.class);
    }

    public CustomizedRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig, RedisOperations redisOperations) {
        super(name, cacheWriter,cacheConfig);
        this.redisOperations=redisOperations;
    }

    /**
     * 重写get方法，获取到缓存后再次取缓存剩余的时间，如果时间小余我们配置的刷新时间就手动刷新缓存。
     * 为了不影响get的性能，启用后台线程去完成缓存的刷。
     * 并且只放一个线程去刷新数据。
     *
     * @param key
     * @return
     */
    @Override
    public ValueWrapper get(final Object key) {
        ValueWrapper valueWrapper= super.get(key);
        String cacheKeyStr = this.createCacheKey(key);

        if (null != valueWrapper) {
            // 刷新缓存数据
            refreshCache(key, cacheKeyStr);
        }
        return valueWrapper;
    }


    /**
     * 刷新缓存数据
     */
    private void refreshCache(Object key, String cacheKeyStr) {
        CacheItemConfig cacheItemConfig=CacheContainer.getCacheItemConfigByCacheName(cacheKeyStr.substring(0,cacheKeyStr.indexOf("::")));
        Long ttl = this.redisOperations.getExpire(cacheKeyStr);
        if (null != ttl && ttl <= cacheItemConfig.getPreLoadTimeSecond()) {
            // 尽量少的去开启线程，因为线程池是有限的
            ThreadTaskUtils.run(()->{
                // 加一个分布式锁，只放一个请求去刷新缓存
                DistributedLock redisLock = new RedisDistributedLock((RedisTemplate) redisOperations);
                try {
                    logger.info("refreshCache:{}",cacheKeyStr);
                    if (redisLock.lock(cacheKeyStr + "_lock",5000L,0,0L)) {
                        logger.info("refreshCache lock:{}",cacheKeyStr);
                        // 获取锁之后再判断一下过期时间，看是否需要加载数据
                        Long ttl2 = CustomizedRedisCache.this.redisOperations.getExpire(cacheKeyStr);
                        if (null != ttl2 && ttl <= cacheItemConfig.getPreLoadTimeSecond()) {
                            // 通过获取代理方法信息重新加载缓存数据
                            CustomizedRedisCache.this.getCacheSupport().refreshCacheByKey(CustomizedRedisCache.super.getName(), cacheKeyStr);
                            logger.info("refreshCache OK:{}",cacheKeyStr);
                        }
                    }
                } catch (Exception e) {
                    logger.info(e.getMessage(), e);
                } finally {
                    redisLock.releaseLock(cacheKeyStr + "_lock");
                }
            });
        }
    }

}
