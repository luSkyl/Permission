package com.java.lcy.Permission.Service;

import com.java.lcy.Permission.Enum.CacheKeyEnum;

public interface SysCacheService {
    public void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyEnum prefix);
    public String getFromCache(CacheKeyEnum prefix, String... keys);
    public void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyEnum prefix, String... keys);
}
