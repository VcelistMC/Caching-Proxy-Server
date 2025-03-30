package com.peteratef.caching_proxy.service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${global.origin}")
    private String originUrl;

    @Bean
    public WebClient provideWebClient(){
        return WebClient.builder().baseUrl(originUrl).build();
    }
}
