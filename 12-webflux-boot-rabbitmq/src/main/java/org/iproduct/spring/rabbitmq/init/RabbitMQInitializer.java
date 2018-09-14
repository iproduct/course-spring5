package org.iproduct.spring.rabbitmq.init;

import org.iproduct.spring.rabbitmq.config.DestinationsConfig;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQInitializer implements ApplicationRunner {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private DestinationsConfig destinationsConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        destinationsConfig.getQueues()
                .forEach((key, destination) -> {
                    Exchange ex = ExchangeBuilder.directExchange(
                            destination.getExchange())
                            .durable(true)
                            .build();
                    amqpAdmin.declareExchange(ex);
                    createDurableQueueAndBinding(destination, ex);
                });

        destinationsConfig.getTopics()
                .forEach((key, destination) -> {
                    Exchange ex = ExchangeBuilder
                            .topicExchange(destination.getExchange())
                            .durable(true)
                            .build();
                    amqpAdmin.declareExchange(ex);
                    createDurableQueueAndBinding(destination, ex);
                });
    }

    private void createDurableQueueAndBinding(DestinationsConfig.DestinationInfo destination, Exchange ex) {
        if (destination.isDurableQueue()) {
            Queue q = QueueBuilder.durable(
                    destination.getRoutingKey())
                    .build();
            amqpAdmin.declareQueue(q);
            Binding b = BindingBuilder.bind(q)
                    .to(ex)
                    .with(destination.getRoutingKey())
                    .noargs();
            amqpAdmin.declareBinding(b);
        }
    }
}