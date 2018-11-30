package com.xk.common.redis.cache;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 注册和刷新缓存接口
 *
 * @author hengxiaokang
 */
public interface CacheSupport {


    /**
     * 按容器以及指定键更新缓存
     *
     * @param cacheName
     * @param cacheKey
     */
    void refreshCacheByKey(String cacheName, String cacheKey);


    void registerInvocation(Object invokedBean, Method invokedMethod, Object[] invocationArguments, Set<String> cacheNames);

    /**
     * 刷新容器中所有值
     * @param cacheName
     */
    void refreshCache(String cacheName);

    public Set<CachedMethodInvocation> getMap(String cacheName);

}