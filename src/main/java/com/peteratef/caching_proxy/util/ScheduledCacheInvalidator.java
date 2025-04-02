package com.peteratef.caching_proxy.util;

import com.peteratef.caching_proxy.cache.TimeoutCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class ScheduledCacheInvalidator {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledCacheInvalidator.class);

    @Autowired
    private TimeoutCache timeoutCache;

    @Scheduled(fixedRate = 300000)
    public void invalidateCaches(){
        logger.info("invalidated caches");
        timeoutCache.invalidate();
    }
}
