package com.peteratef.caching_proxy.service;

import jakarta.servlet.http.HttpServletRequest;

public interface ProxyService {
    public String forwardRequest(HttpServletRequest path);
}
