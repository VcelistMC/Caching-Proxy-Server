package com.peteratef.caching_proxy.cache;

import com.peteratef.caching_proxy.model.CachedResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class TimeoutCache extends ICache{

    @Override
    public void put(String key, CachedResponse cachedResponse) {
        cachedResponse.setTimeout(System.currentTimeMillis() + ttl);
        cache.put(key, cachedResponse);
    }

    @Override
    public void invalidate(){
        ArrayList<String> keysToRemove = new ArrayList<>();
        for(Map.Entry<String, CachedResponse> entry : cache.entrySet()){
            if(entry.getValue().getTimeout() > System.currentTimeMillis()){
                keysToRemove.add(entry.getKey());
            }
        }
        for(String key : keysToRemove){
            cache.remove(key);
        }
    }
}
