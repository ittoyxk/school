package com.xk.common.redis.cache;


import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 标记了缓存注解的方法类信息
 * 用于主动刷新缓存时调用原始方法加载数据
 *
 * @author hengxiaokang
 */
public final class CachedMethodInvocation implements Serializable {

    private static final long serialVersionUID = 4053574712960451939L;

    private Object key;
    private final Object targetBean;
    private final Method targetMethod;
    private Object[] arguments;

    public CachedMethodInvocation(Object key, Object targetBean, Method targetMethod, Object[] arguments)
    {
        this.key = key;
        this.targetBean = targetBean;
        this.targetMethod = targetMethod;
        if (arguments != null && arguments.length != 0) {
            this.arguments = Arrays.copyOf(arguments, arguments.length);
        }
    }

    public Object[] getArguments()
    {
        return arguments;
    }

    public Object getTargetBean()
    {
        return targetBean;
    }

    public Method getTargetMethod()
    {
        return targetMethod;
    }

    public Object getKey()
    {
        return key;
    }

}

