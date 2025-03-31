package com.peteratef.caching_proxy.service;

import com.peteratef.caching_proxy.entity.CachedResponseEntity;
import com.peteratef.caching_proxy.repo.ProxyRepository;
import com.peteratef.caching_proxy.service.client.OriginClient;
import com.peteratef.caching_proxy.util.Hasher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ProxyService {
    private OriginClient originClient;
    private ProxyRepository repository;

    @SneakyThrows
    public ResponseEntity<String> getRequest(HttpServletRequest request) {
        String hashKey = Hasher.generateMD5HashForURI(request.getRequestURI());
        Optional<CachedResponseEntity> possibleCacheHit = repository.findById(hashKey);
        if (possibleCacheHit.isPresent()) {
            CachedResponseEntity cachedResponseEntity = possibleCacheHit.get();
            return ResponseEntity.ok(cachedResponseEntity.getResponse());
        }else{
            Thread.sleep(10000);
            var response = originClient.forwardGetRequest(request);
            if(response.getStatus().is2xxSuccessful()){
                var cachedResponseEntity = CachedResponseEntity.createCachedResponseEntity(request.getRequestURI(), response.getBody());
                repository.save(cachedResponseEntity);
            }

            return ResponseEntity.status(response.getStatus().value()).headers(response.getHeaders()).body(response.getBody());
        }
    }
}
