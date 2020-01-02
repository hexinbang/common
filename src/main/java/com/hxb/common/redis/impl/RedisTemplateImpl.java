package com.hxb.common.redis.impl;

import com.hxb.common.redis.IRedisTemplate;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author hexinbang@foxmail.com
 * @date 2019/9/11 20:04
 */

@Component
public class RedisTemplateImpl implements IRedisTemplate {

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean setExpire(@NonNull String key, long time) {
        try {
            if (time > 0) {
                this.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public long getExpire(@NonNull String key) {
        return this.redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    public boolean hasKey(@NonNull String key) {
        return this.redisTemplate.hasKey(key);
    }

    public void delete(@NonNull String... keys) {
        if(keys.length>0){
            if(keys.length==1){
                this.redisTemplate.delete(keys[0]);
            }else{
                this.redisTemplate.delete(Arrays.asList(keys));
            }
        }
    }

    public String get(@NonNull String key) {
        return this.redisTemplate.opsForValue().get(key).toString();
    }

    public boolean set(@NonNull String key, @NonNull Object value) {
        try {
            this.redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean set(@NonNull String key, @NonNull Object value, long time) {
        try {
            if(time>0){
                this.redisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);
            }else{
                set(key,value);
            }
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public Object hget(@NonNull String key, @NonNull String field) {
        return this.redisTemplate.opsForHash().get(key,field);
    }

    public Map<Object, Object> hmget(@NonNull String key) {
        return this.redisTemplate.opsForHash().entries(key);
    }

    public boolean hset(@NonNull String key, @NonNull String field, @NonNull Object value) {
        try{
            this.redisTemplate.opsForHash().put(key,field,value);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public void hdelete(@NonNull String key, @NonNull String... fields) {
        this.redisTemplate.opsForHash().delete(key,fields);
    }

    public boolean hHasKey(@NonNull String key, @NonNull String field) {
        return this.redisTemplate.opsForHash().hasKey(key,field);

    }

    public Set<Object> sget(@NonNull String key) {
        try {
            return this.redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean sHasKey(@NonNull String key, @NonNull Object value) {
        try {
            return this.redisTemplate.opsForSet().isMember(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public long sset(@NonNull String key, @NonNull Object... values) {
        try {
            return this.redisTemplate.opsForSet().add(key,values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long sset(@NonNull String key, long time, @NonNull Object... values) {
        try {
            long count =  this.redisTemplate.opsForSet().add(key,values);
            if(time > 0){
                this.setExpire(key,time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long sGetSetSize(@NonNull String key) {
        try {
            return this.redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long sRemove(@NonNull String key, @NonNull Object... values) {
        try {
            long count=this.redisTemplate.opsForSet().remove(key,values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Object> lget(@NonNull String key, long start, long end) {
        try {
            return this.redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object lgetByIndex(@NonNull String key, long index) {
        try {
            return this.redisTemplate.opsForList().index(key,index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public long lgetListSize(@NonNull String key) {
        try {
            return this.redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean lset(@NonNull String key,@NonNull  List<Object> value) {
        try {
            this.redisTemplate.opsForList().rightPush(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean lset(@NonNull String key, @NonNull List<Object> value, long time) {
        try {
            this.redisTemplate.opsForList().rightPush(key,value);
            if(time>0){
                this.setExpire(key,time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public long lRemove(@NonNull String key, long count, @NonNull Object value) {
        try {
            return this.redisTemplate.opsForList().remove(key,count,value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean hset(String key, String field, Object value, long time) {
        try{
            this.redisTemplate.opsForHash().put(key,field,value);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean hmset(String key, Map<Object, Object> map) {
        try {
            this.redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hmset(String key, Map<Object, Object> map, long time) {
        try {
            this.redisTemplate.opsForHash().putAll(key, map);
            if(time>0){
                this.setExpire(key,time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
