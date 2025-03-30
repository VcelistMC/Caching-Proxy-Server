package com.peteratef.caching_proxy.controller;

import com.peteratef.caching_proxy.service.ProxyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class ProxyController {

    private ProxyService proxyService;

    @GetMapping("/**")
    public String proxy(HttpServletRequest request) {
        return proxyService.forwardRequest(request);
    }

}
