package org.iproduct.spring.gamification.handlers;

import org.iproduct.spring.gamification.domain.GameObject2D;
import org.iproduct.spring.gamification.services.ThorPositionsGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

@Component
public class ReactiveGameHandler {

    @Autowired
    private ThorPositionsGenerator generator;


    public Mono<ServerResponse> streamGameObjects(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_STREAM_JSON)
                .body(generator.getGameObjectsStream(Duration.ofMillis(1000)), GameObject2D.class);
    }

    public Mono<ServerResponse> streamGameObjectsSSE(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(TEXT_EVENT_STREAM)
                .body(generator.getGameObjectsStream(Duration.ofMillis(100)), GameObject2D.class);
    }

}