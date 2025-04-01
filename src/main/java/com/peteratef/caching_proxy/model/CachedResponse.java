package com.peteratef.caching_proxy.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;

@Setter
@Getter
public class CachedResponse {
    private HttpStatusCode status;
    private String body;
    private HttpHeaders headers;
    private Long timeout;
}
