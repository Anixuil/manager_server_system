package com.anixuil.manager_system.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisCache {
    @Autowired
    public RedisTemplate redisTemplate;

    //缓存基本的对象，Integer、String、实体类等
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    //缓存基本的对象，Integer、String、实体类等
    public <T> void setCacheObject(final String key, final T value, final Integer timeout,final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    //设置有效时间
    public boolean expire(final String key,final long timeout){
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    //设置有效时间
    public boolean expire(final String key,final long timeout,final TimeUnit unit){
        return redisTemplate.expire(key, timeout, unit);
    }

    //获取缓存的基本对象。
    public <T> T getCacheObject(final String key) {
        ValueOperations<String,T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    //删除单个对象
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    //删除集合对象
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    //缓存list数据
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    //获得缓存的list对象
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    //缓存Set
    public <T> BoundSetOperations<String,T> setCacheSet(final String key, final Set<T> dataSet){
        BoundSetOperations<String,T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while(it.hasNext()){
            setOperation.add(it.next());
        }
        return setOperation;
    }

    //获得缓存的set
    public <T> Set<T> getCacheSet(final String key){
        return redisTemplate.opsForSet().members(key);
    }

    //缓存Map
    public <T> void setCacheMap(final String key, final Map<String,T> dataMap){
        if(dataMap != null){
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    //获得缓存的Map
    public <T> Map<String,T> getCacheMap(final String key){
        return redisTemplate.opsForHash().entries(key);
    }

    //往Hash中存入数据
    public <T> void setCacheMapValue(final String key, final String hashKey, final T value){
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    //获取hash中的数据
    public <T> T getCacheMapValue(final String key, final String hashKey){
        HashOperations<String,String,T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hashKey);
    }

    //删除hash中的数据
    public void delCacheMapValue(final String key, final String hashKey){
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hashKey);
    }

    //获取多个hash中的数据
    public <T> List<T> getCacheMapValue(final String key, final Collection<Object> hashKeys){
        return redisTemplate.opsForHash().multiGet(key, hashKeys);
    }

    //获取缓存的基本对象列表
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }
}
