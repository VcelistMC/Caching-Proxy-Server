package com.peteratef.caching_proxy.cache;

import com.peteratef.caching_proxy.model.CachedResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class ICache {
    protected final Map<String, CachedResponse> cache = new HashMap<>();

    protected boolean exists(String key){
        return cache.containsKey(key);
    }

    public Optional<CachedResponse> get(String key){
        if(exists(key)){
            return Optional.of(cache.get(key));
        }else{
            return Optional.empty();
        }
    }

    public void put(String key, CachedResponse cachedResponse){
        cache.put(key, cachedResponse);
    }

    public abstract void invalidate();
}
