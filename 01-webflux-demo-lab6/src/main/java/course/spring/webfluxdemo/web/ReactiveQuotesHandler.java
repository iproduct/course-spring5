package course.spring.webfluxdemo.web;

import course.spring.webfluxdemo.domain.QuotesService;
import course.spring.webfluxdemo.model.Article;
import course.spring.webfluxdemo.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

@Component
public class ReactiveQuotesHandler {
    @Autowired
    private QuotesService generator;

    public Mono<ServerResponse> streamQuotes(ServerRequest req) {
        return  ServerResponse.ok()
                .contentType(APPLICATION_STREAM_JSON)
                .body(generator.getQotesStream(Duration.ofMillis(500)), Quote.class);
    }

    public Mono<ServerResponse> streamQuotesSSE(ServerRequest req) {
        return  ServerResponse.ok()
                .contentType(TEXT_EVENT_STREAM)
                .body(generator.getQotesStream(Duration.ofMillis(500)), Quote.class);
    }

}
