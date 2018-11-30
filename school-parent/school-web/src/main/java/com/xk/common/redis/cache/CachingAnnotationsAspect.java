package com.xk.common.redis.cache;

import com.xk.common.redis.ShareJedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 缓存拦截，用于注册方法信息
 *
 * @author hengxiaokang
 */
@Aspect
@Component
public class CachingAnnotationsAspect {

    private static final Logger logger = LoggerFactory.getLogger(CachingAnnotationsAspect.class);

    @Autowired
    private CacheSupport cacheSupport;

    @Autowired
    private ShareJedisService shareJedisService;

    private <T extends Annotation> List<T> getMethodAnnotations(AnnotatedElement ae, Class<T> annotationType)
    {
        List<T> anns = new ArrayList<T>(2);
        // look for raw annotation
        T ann = ae.getAnnotation(annotationType);
        if (ann != null) {
            anns.add(ann);
        }
        // look for meta-annotations
        for (Annotation metaAnn : ae.getAnnotations()) {
            ann = metaAnn.annotationType().getAnnotation(annotationType);
            if (ann != null) {
                anns.add(ann);
            }
        }
        return (anns.isEmpty() ? null : anns);
    }

    private Method getSpecificmethod(ProceedingJoinPoint pjp)
    {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        // The method may be on an interface, but we need attributes from the
        // target class. If the target class is null, the method will be
        // unchanged.
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(pjp.getTarget());
        if (targetClass == null && pjp.getTarget() != null) {
            targetClass = pjp.getTarget().getClass();
        }
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        // If we are dealing with method with generic parameters, find the
        // original method.
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
        return specificMethod;
    }

    @Pointcut("@annotation(org.springframework.cache.annotation.Cacheable)")
    public void pointcut()
    {
    }

    @Pointcut("@annotation(org.springframework.cache.annotation.CachePut)")
    public void update()
    {
    }

    @Pointcut("@annotation(org.springframework.cache.annotation.CacheEvict)")
    public void del()
    {
    }

    @Around("pointcut()")
    public Object registerInvocation(ProceedingJoinPoint joinPoint) throws Throwable
    {

        Method method = this.getSpecificmethod(joinPoint);

        List<Cacheable> annotations = this.getMethodAnnotations(method, Cacheable.class);

        Set<String> cacheSet = new HashSet<String>();
        for (Cacheable cacheables : annotations) {
            cacheSet.addAll(Arrays.asList(cacheables.value()));
        }
        cacheSupport.registerInvocation(joinPoint.getTarget(), method, joinPoint.getArgs(), cacheSet);
        return joinPoint.proceed();

    }

    @Around("update()")
    public Object update(ProceedingJoinPoint joinPoint) throws Throwable
    {

        Method method = this.getSpecificmethod(joinPoint);

        List<CacheEvict> annotations = this.getMethodAnnotations(method, CacheEvict.class);

        Set<String> cacheSet = new HashSet<String>();
        if (annotations != null) {
            for (CacheEvict cacheables : annotations) {
                del(cacheables);
            }
        }
        return joinPoint.proceed();

    }

    @Around("del()")
    public Object del(ProceedingJoinPoint joinPoint) throws Throwable
    {

        Method method = this.getSpecificmethod(joinPoint);

        List<CacheEvict> annotations = this.getMethodAnnotations(method, CacheEvict.class);

        if(annotations!=null) {
            for (CacheEvict cacheables : annotations) {
                del(cacheables);
                logger.info("");
            }
        }
        return joinPoint.proceed();

    }

    private void del(CacheEvict cacheables)
    {
        String[] value = cacheables.value();
        for (String keys : value) {
            Set<CachedMethodInvocation> map = cacheSupport.getMap(keys);
            for (final CachedMethodInvocation invocation : map) {
                Object key = invocation.getKey();
                shareJedisService.del(key);
            }
        }
    }
}