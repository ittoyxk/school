package com.xk.common.redis;

/**
 * Created by hengxiaokang
 * Date:2018/6/14
 * Time:19:53
 */
public interface ShareJedisService
{

    /**
     * 获取缓存对象
     *
     * @param key
     * @return
     */
    public Object getObject(final String key);

    /**
     * 缓存对象
     *
     * @param key
     * @param obj
     * @param seconds
     * @return
     */
    public String setObject(final String key, final Object obj, final int seconds);


    /**
     * 获取缓存字符串
     *
     * @param key
     * @return
     */
    public String getString(final String key);

    /**
     * 设置缓存字符串
     *
     * @param key
     * @param obj
     * @param seconds
     * @return
     */
    public String setString(final String key, final String obj, final int seconds);

    public Long getTTL(final String key);

    public void del(final Object key);

}