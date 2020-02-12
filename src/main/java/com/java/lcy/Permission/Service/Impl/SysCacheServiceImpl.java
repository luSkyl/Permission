package com.java.lcy.Permission.Service.Impl;

import com.google.common.base.Joiner;
import com.java.lcy.Permission.Enum.CacheKeyEnum;
import com.java.lcy.Permission.Service.SysCacheService;
import com.java.lcy.Permission.Util.JsonMapper;
import com.java.lcy.Permission.Util.RedisPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

@Service
@Slf4j
public class SysCacheServiceImpl implements SysCacheService {

    @Resource
    private RedisPool redisPool;

    @Override
    public void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyEnum prefix) {
        saveCache(toSavedValue, timeoutSeconds, prefix, null);
    }
    @Override
    public void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyEnum prefix, String... keys) {
        if (toSavedValue == null) {
            return;
        }
        Jedis jedis = null;
        try {
            String cacheKey = generateCacheKey(prefix, keys);
            jedis = redisPool.instance();
            jedis.setex(cacheKey, timeoutSeconds, toSavedValue);
        } catch (Exception e) {
            log.error("【保存缓存】 保存缓存异常, prefix:{}, keys:{}", prefix.name(), JsonMapper.obj2String(keys), e);
        } finally {
            redisPool.safeClose(jedis);
        }
    }

    @Override
    public String getFromCache(CacheKeyEnum prefix, String... keys) {
        Jedis jedis = null;
        String cacheKey = generateCacheKey(prefix, keys);
        try {
            jedis = redisPool.instance();
            String value = jedis.get(cacheKey);
            return value;
        } catch (Exception e) {
            log.error("【获取缓存】 获取缓存异常, prefix:{}, keys:{}", prefix.name(), JsonMapper.obj2String(keys), e);
            return null;
        } finally {
            redisPool.safeClose(jedis);
        }
    }

    private String generateCacheKey(CacheKeyEnum prefix, String... keys) {
        String key = prefix.name();
        if (keys != null && keys.length > 0) {
            key += "_" + Joiner.on("_").join(keys);
        }
        return key;
    }
}
