package com.peteratef.caching_proxy.service;

import com.peteratef.caching_proxy.repo.ProxyRepository;
import com.peteratef.caching_proxy.service.client.OriginClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@AllArgsConstructor
@Service
public class ProxyService {
    private OriginClient originClient;
    private ProxyRepository repository;


    public String forwardRequest(HttpServletRequest request) {
        var response = originClient.forwardRequest(request);
//        System.out.println(response);
        return response;
    }
}
