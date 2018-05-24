package org.iproduct.spring.webflux.reactive;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ReactiveHello {

    public Mono<ServerResponse> sayHelloReactive(ServerRequest req) {
        return ServerResponse.ok().body(
            Mono.just(String.format("Hello, %s from Spring Boot!",
                    req.queryParam("name").orElse("Stranger"))),
            String.class
        );
    }
}
