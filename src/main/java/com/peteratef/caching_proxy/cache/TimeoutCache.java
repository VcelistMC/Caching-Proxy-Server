package com.peteratef.caching_proxy.cache;

import com.peteratef.caching_proxy.model.CachedResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Component
@Primary
public class TimeoutCache extends ICache{

    @Value("${global.ttl}")
    protected Long ttl;

    @Override
    public void put(String key, CachedResponse cachedResponse) {
        cachedResponse.setTimeout(System.currentTimeMillis() + ttl);
        cache.put(key, cachedResponse);
    }

    @Override
    public Optional<CachedResponse> get(String key) {
        if(!exists(key)) {return Optional.empty();}
        var cachedResponse = cache.get(key);
        if(isStale(cachedResponse)) {return Optional.empty();}
        return Optional.of(cachedResponse);
    }

    private boolean isStale(CachedResponse cachedResponse) {
        return cachedResponse.getTimeout() < System.currentTimeMillis();
    }

    @Override
    public void invalidate(){
        ArrayList<String> keysToRemove = new ArrayList<>();
        for(Map.Entry<String, CachedResponse> entry : cache.entrySet()){
            if(isStale(entry.getValue())){
                keysToRemove.add(entry.getKey());
            }
        }
        for(String key : keysToRemove){
            cache.remove(key);
        }
    }
}
