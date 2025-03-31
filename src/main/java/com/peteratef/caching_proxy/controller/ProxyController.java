package com.peteratef.caching_proxy.controller;

import com.peteratef.caching_proxy.service.ProxyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class ProxyController {

    private ProxyService proxyService;

    @GetMapping("/**")
    public ResponseEntity<String> proxyGet(HttpServletRequest request) {
        return proxyService.getRequest(request);
    }

}
