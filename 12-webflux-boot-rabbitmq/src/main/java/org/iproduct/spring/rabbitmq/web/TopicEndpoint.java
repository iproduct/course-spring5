package org.iproduct.spring.rabbitmq.web;

import org.iproduct.spring.rabbitmq.config.DestinationsConfig;
import org.iproduct.spring.rabbitmq.rabbitmq.MessageListenerContainerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class TopicEndpoint {

    @Autowired
    private DestinationsConfig destinationsConfig;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private MessageListenerContainerFactory messageListenerContainerFactory;

    @GetMapping(
            value = "/topic/{name}",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<?> receiveMessagesFromTopic(@PathVariable String name) {
        DestinationsConfig.DestinationInfo d = destinationsConfig.getTopics().get(name);
        if (d == null) {
            return Flux.just(ResponseEntity.notFound()
                    .build());
        }
        Queue topicQueue = createTopicQueue(d);
        String qname = topicQueue.getName();
        MessageListenerContainer mlc = messageListenerContainerFactory.createMessageListenerContainer(qname);
        Flux<String> f = Flux.<String> create(emitter -> {
            mlc.setupMessageListener((MessageListener) m -> {
                String payload = new String(m.getBody());
                emitter.next(payload);
            });
            emitter.onRequest(v -> {
                mlc.start();
            });
            emitter.onDispose(() -> {
                amqpAdmin.deleteQueue(qname);
                mlc.stop();
            });
        });

        return Flux.interval(Duration.ofSeconds(5))
                .map(v -> "No news is good news")
                .mergeWith(f);
    }

    private Queue createTopicQueue(DestinationsConfig.DestinationInfo destination) {
        Exchange ex = ExchangeBuilder
                .topicExchange(destination.getExchange())
                .durable(true)
                .build();
        amqpAdmin.declareExchange(ex);
        Queue q = QueueBuilder
                .nonDurable()
                .build();
        amqpAdmin.declareQueue(q);
        Binding b = BindingBuilder.bind(q)
                .to(ex)
                .with(destination.getRoutingKey())
                .noargs();
        amqpAdmin.declareBinding(b);
        return q;
    }


    @PostMapping(value = "/topic/{name}")
    public Mono<ResponseEntity<?>> sendMessageToTopic(
            @PathVariable String name, @RequestBody String payload) {

        DestinationsConfig.DestinationInfo d = destinationsConfig
                .getTopics()
                .get(name);

        if (d == null) {
            return Mono.just(ResponseEntity.notFound().build());
        }

        return Mono.fromCallable(() -> {
            amqpTemplate.convertAndSend(d.getExchange(), d.getRoutingKey(), payload);
            return ResponseEntity.accepted().build();
        });
    }
}
