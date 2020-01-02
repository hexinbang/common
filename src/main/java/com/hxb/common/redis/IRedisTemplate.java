package com.hxb.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author hexinbang@foxmail.com
 * @date 2019/9/11 19:52
 */
public interface IRedisTemplate {

    boolean setExpire(String key, long time);

    long getExpire(String key);

    boolean hasKey(String key);

    void delete(String... key);

    String get(String key);

    boolean set(String key, Object value);

    boolean set(String key, Object value, long time);

    Object hget(String key, String field);

    Map<Object,Object>hmget(String key);

    boolean hset(String key, String field, Object value);

    boolean hset(String key, String field, Object value, long time);

    boolean hmset(String key, Map<Object, Object> map);

    boolean hmset(String key, Map<Object, Object> map, long time);

    void hdelete(String key, String... fields);

    boolean hHasKey(String key, String field);

    Set<Object> sget(String key);

    boolean sHasKey(String key, Object value);

    long sset(String key, Object... values);

    long sset(String key, long time, Object... values);

    long sGetSetSize(String key);

    long sRemove(String key, Object... values);

    List<Object> lget(String key, long start, long end);

    Object lgetByIndex(String key, long index);

    long lgetListSize(String key);

    boolean lset(String key, List<Object> value);

    boolean lset(String key, List<Object> value, long time);

    long lRemove(String key, long count, Object value);

}
