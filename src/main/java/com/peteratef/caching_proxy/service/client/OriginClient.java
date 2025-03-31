package com.peteratef.caching_proxy.service.client;

import com.peteratef.caching_proxy.model.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Enumeration;

@AllArgsConstructor
@Component
public class OriginClient {

    @Autowired
    private WebClient webClient;

    public Response forwardGetRequest(HttpServletRequest request) {
        var response = new Response();

        return webClient
                .get()
                .uri(request.getRequestURI())
                .headers(httpHeaders -> buildHeadersFromServletRequest(request, httpHeaders))
                .exchangeToMono(clientResponse -> clientResponse
                        .bodyToMono(String.class)
                        .publishOn(Schedulers.boundedElastic())
                        .doOnTerminate(() -> {
                            response.setStatus(clientResponse.statusCode());
                            response.setHeaders(clientResponse.headers().asHttpHeaders());
                        })
                        .doOnNext(response::setBody)
                        .then(Mono.just(response)))
                .block();
    }

    private void buildHeadersFromServletRequest(HttpServletRequest request, HttpHeaders headers) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            // print headers
            String headerName = headerNames.nextElement();
            if(headerName.equalsIgnoreCase("host")) continue;
            headers.add(headerName, request.getHeader(headerName));
        }
    }
}
