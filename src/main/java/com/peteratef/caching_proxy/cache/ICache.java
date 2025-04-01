package com.peteratef.caching_proxy.cache;

import com.peteratef.caching_proxy.model.CachedResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public abstract class ICache {
    protected final Map<String, CachedResponse> cache = new HashMap<>();

    @Value("${global.ttl}")
    protected Long ttl;

    public boolean exists(String key){
        return cache.containsKey(key);
    }

    public CachedResponse get(String key){
        return cache.get(key);
    }

    public void put(String key, CachedResponse cachedResponse){
        cache.put(key, cachedResponse);
    }

    public abstract void invalidate();
}
