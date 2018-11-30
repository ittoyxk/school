package com.xk.common.redis.cache;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.util.MethodInvoker;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 手动刷新缓存实现类
 *
 * @author hengxiaokang
 */
@Component
public class CacheSupportImpl implements CacheSupport {
    private static final Logger logger = LoggerFactory.getLogger(CacheSupportImpl.class);

    /**
     * 记录容器与所有执行方法信息
     */
    private Map<String, Set<CachedMethodInvocation>> cacheToInvocationsMap;

    @Autowired
    private CacheManager cacheManager;

    private void refreshCache(CachedMethodInvocation invocation, String cacheName)
    {

        boolean invocationSuccess;
        Object computed = null;
        try {
            computed = invoke(invocation);
            invocationSuccess = true;
        } catch (Exception ex) {
            invocationSuccess = false;
        }
        if (invocationSuccess) {
            if (cacheToInvocationsMap.get(cacheName) != null) {
                cacheManager.getCache(cacheName).put(invocation.getKey(), computed);
            }
        }
    }

    private Object invoke(CachedMethodInvocation invocation)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        final MethodInvoker invoker = new MethodInvoker();
        invoker.setTargetObject(invocation.getTargetBean());
        invoker.setArguments(invocation.getArguments());
        invoker.setTargetMethod(invocation.getTargetMethod().getName());
        invoker.prepare();
        return invoker.invoke();
    }


    @PostConstruct
    public void initialize()
    {
        cacheToInvocationsMap = new ConcurrentHashMap<String, Set<CachedMethodInvocation>>(cacheManager.getCacheNames().size());
        for (final String cacheName : cacheManager.getCacheNames()) {
            cacheToInvocationsMap.put(cacheName, new CopyOnWriteArraySet<CachedMethodInvocation>());
        }
    }

    @Override
    public void registerInvocation(Object targetBean, Method targetMethod, Object[] arguments, Set<String> annotatedCacheNames)
    {

        StringBuilder sb = new StringBuilder();
        for (Object obj : arguments) {
            sb.append(obj.toString());
        }

        Object key = sb.toString();

        final CachedMethodInvocation invocation = new CachedMethodInvocation(key, targetBean, targetMethod, arguments);
        for (final String cacheName : annotatedCacheNames) {
            String[] cacheParams = cacheName.split("#");
            String realCacheName = cacheParams[0];
            if (!cacheToInvocationsMap.containsKey(realCacheName)) {
                this.initialize();
            }
            cacheToInvocationsMap.get(realCacheName).add(invocation);
        }
    }

    @Override
    public void refreshCache(String cacheName)
    {
        this.refreshCacheByKey(cacheName, null);
    }

    @Override
    public Set<CachedMethodInvocation> getMap(String cacheName)
    {
        return cacheToInvocationsMap == null ? new ConcurrentHashMap<String, Set<CachedMethodInvocation>>().get(cacheName) : cacheToInvocationsMap.get(cacheName);
    }

    @Override
    public void refreshCacheByKey(String cacheName, String cacheKey)
    {
        if (cacheToInvocationsMap.get(cacheName) != null) {
            for (final CachedMethodInvocation invocation : cacheToInvocationsMap.get(cacheName)) {
                if (!StringUtils.isBlank(cacheKey)) {
                    cacheKey = cacheKey.substring(cacheKey.indexOf("::") + 2);
                    if (invocation.getKey().toString().equals(cacheKey)) {
                        refreshCache(invocation, cacheName);
                    }
                }
            }
        }
    }
}
