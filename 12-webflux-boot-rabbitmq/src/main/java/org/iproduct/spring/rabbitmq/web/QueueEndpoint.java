package org.iproduct.spring.rabbitmq.web;

import org.iproduct.spring.rabbitmq.config.DestinationsConfig;
import org.iproduct.spring.rabbitmq.rabbitmq.MessageListenerContainerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class QueueEndpoint {

    @Autowired
    private DestinationsConfig destinationsConfig;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private MessageListenerContainerFactory messageListenerContainerFactory;

    @GetMapping(
            value = "/queue/{name}",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<?> receiveMessagesFromQueue(@PathVariable String name) {

        DestinationsConfig.DestinationInfo d = destinationsConfig
                .getQueues()
                .get(name);
        if (d == null) {
            return Flux.just(ResponseEntity.notFound()
                    .build());
        }

        MessageListenerContainer mlc = messageListenerContainerFactory
                .createMessageListenerContainer(d.getRoutingKey());

        Flux<String> f = Flux.<String> create(emitter -> {
            mlc.setupMessageListener((MessageListener) m -> {
                String payload = new String(m.getBody());
                emitter.next(payload);
            });
            emitter.onRequest(v -> {
                mlc.start();
            });
            emitter.onDispose(() -> {
                mlc.stop();
            });
        });

        return Flux.interval(Duration.ofSeconds(5))
                .map(v -> "No news is good news")
                .mergeWith(f);
    }

    @PostMapping(value = "/queue/{name}")
    public Mono<ResponseEntity<?>> sendMessageToQueue(
            @PathVariable String name, @RequestBody String payload) {

        DestinationsConfig.DestinationInfo d = destinationsConfig
                .getQueues().get(name);
        if (d == null) {
            return Mono.just(
                    ResponseEntity.notFound().build());
        }

        return Mono.fromCallable(() -> {
            amqpTemplate.convertAndSend(
                    d.getExchange(),
                    d.getRoutingKey(),
                    payload);
            return ResponseEntity.accepted().build();
        });
    }
}
