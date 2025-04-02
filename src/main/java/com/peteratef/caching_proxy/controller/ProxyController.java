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

    @PostMapping("/**")
    public ResponseEntity<String> proxyPost(HttpServletRequest request) {
        return proxyService.postRequest(request);
    }

    @PatchMapping("/**")
    public ResponseEntity<String> proxyPatch(HttpServletRequest request) {
        return proxyService.patchRequest(request);
    }

    @PutMapping("/**")
    public ResponseEntity<String> proxyPut(HttpServletRequest request) {
        return proxyService.putRequest(request);
    }

    @DeleteMapping("/**")
    public ResponseEntity<String> proxyDelete(HttpServletRequest request) {
        return proxyService.deleteRequest(request);
    }

}
