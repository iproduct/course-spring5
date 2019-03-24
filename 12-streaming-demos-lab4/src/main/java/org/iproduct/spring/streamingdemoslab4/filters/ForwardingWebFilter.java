package org.iproduct.spring.streamingdemoslab4.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class ForwardingWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getRequest().getURI().getPath().equals("/")) {
            return chain.filter(
                    exchange.mutate().request(exchange.getRequest().mutate().path("/quotes.html").build())
                    .build());
        }

        return chain.filter(exchange);
    }
}
