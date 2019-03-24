package org.iproduct.spring.streamingdemoslab4.web;

import org.iproduct.spring.streamingdemoslab4.model.Quote;
import org.iproduct.spring.streamingdemoslab4.service.QuotesGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

@Service
public class ReactiveQuotesHandler {

    @Autowired
    QuotesGenerator generator;

    public Mono<ServerResponse> streamQuotes(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_STREAM_JSON)
                .body(generator.getQuotesStream(Duration.ofMillis(250)), Quote.class);
    }

    public Mono<ServerResponse> streamQuotesSSE(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(TEXT_EVENT_STREAM)
                .body(generator.getQuotesStream(Duration.ofMillis(250)), Quote.class);
    }
}
