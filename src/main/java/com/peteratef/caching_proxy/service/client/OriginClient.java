package com.peteratef.caching_proxy.service.client;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Enumeration;

@AllArgsConstructor
@Component
public class OriginClient {

    @Autowired
    private WebClient webClient;

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
            if(headerName.equalsIgnoreCase("accept-encoding")) continue;
            headers.add(headerName, request.getHeader(headerName));
        }
        for(String headerName : headers.keySet()) {
            System.out.println(headerName + ": " + headers.get(headerName));
        }
        System.out.println("--------------------------------------------");
    }
}
