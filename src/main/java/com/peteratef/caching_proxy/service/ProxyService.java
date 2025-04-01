package com.peteratef.caching_proxy.service;

import com.peteratef.caching_proxy.cache.TimeoutCache;
import com.peteratef.caching_proxy.model.CachedResponse;
import com.peteratef.caching_proxy.service.client.OriginClient;
import com.peteratef.caching_proxy.util.Hasher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Service
public class ProxyService {
    private OriginClient originClient;
    private TimeoutCache cache;


    public ResponseEntity<String> getRequest(HttpServletRequest request) {
        String hashKey = Hasher.generateMD5HashForURI(request.getRequestURI());
        if (cache.exists(hashKey)) {
            CachedResponse cachedResponse = cache.get(hashKey);;
            Date date = new Date(cachedResponse.getTimeout());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(cachedResponse.getHeaders())
                    .header("X-Cache", "HIT")
                    .header("X-Cache-TTL", date.toString())
                    .body(cachedResponse.getBody());
        }else{
            var response = originClient.forwardGetRequest(request);
            if(response.getStatus().is2xxSuccessful()){
                cache.put(hashKey, response);
            }

            return ResponseEntity
                    .status(response.getStatus().value())
                    .headers(response.getHeaders())
                    .header("X-Cache", "MISS")
                    .body(response.getBody());
        }
    }
}
