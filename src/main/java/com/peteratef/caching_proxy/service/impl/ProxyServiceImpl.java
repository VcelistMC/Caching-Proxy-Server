package com.peteratef.caching_proxy.service.impl;

import com.peteratef.caching_proxy.service.ProxyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Enumeration;

@Service
public class ProxyServiceImpl implements ProxyService {


    @Autowired
    private WebClient webClient;

    @Override
    public String forwardRequest(HttpServletRequest request) {
        return webClient
                .get()
                .uri(request.getRequestURI())
                .headers(httpHeaders -> buildHeadersFromServletRequest(request, httpHeaders))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private void buildHeadersFromServletRequest(HttpServletRequest request, HttpHeaders headers) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            // print headers
            String headerName = headerNames.nextElement();
            if(headerName.equalsIgnoreCase("host")) continue;
            System.out.println(headerName + ": " + request.getHeader(headerName));
            headers.add(headerName, request.getHeader(headerName));
        }
    }
}
