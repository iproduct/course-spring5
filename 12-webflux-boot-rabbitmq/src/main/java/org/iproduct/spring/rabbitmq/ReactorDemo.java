package org.iproduct.spring.rabbitmq;

import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

import static java.time.temporal.ChronoUnit.MILLIS;

public class ReactorDemo {

    public static void main(String[] args) throws InterruptedException {
//        EmitterProcessor<String> emitter = EmitterProcessor.create();
//        FluxSink<String> sink = emitter.sink();
//        Flux<String> flux = emitter.publishOn(Schedulers.single())
//                .map(String::toUpperCase)
////                .filter(s -> s.startsWith("HELLO"))
//                .delayElements(Duration.of(1000, MILLIS));
//        flux.subscribe(data -> System.out.println("Subscriber 1:" + data));
//        sink.next("Hello World!"); // emit - non blocking
//        Thread.sleep(1500);
//        flux.subscribe(data -> System.out.println("Subscriber 2:" + data));
//        sink.next("Goodbye World!");
//        sink.next("Hello Trayan!");

//        Flux<Integer> ints = Flux.range(1, 10)
//                .map(i -> {
//                    if (i <= 11) return i;
//                    throw new RuntimeException("Got to 5");
//                });
//
//        ints.subscribe(System.out::println,
//                error -> System.err.println("Error: " + error),
//                () -> {
//                    System.out.println("Done");
//                });
//
//        Flux<String> flux = Flux.generate(
//                () -> 0,
//                (state, sink) -> {
//                    sink.next("3 x " + state + " = " + 3*state);
//                    if (state == 10) sink.complete();
//                    return state + 1;
//                });
//        Flux<String> flux = Flux.generate(
//                AtomicLong::new,
//                (state, sink) -> {
//                    long i = state.getAndIncrement();
//                    sink.next("3 x " + i + " = " + 3*i);
//                    if (i == 10) sink.complete();
//                    return state;
//                });
        Flux<String> flux = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3*i);
                    if (i == 10) sink.complete();
                    return state;
                }, (state) -> System.out.println("state: " + state));
        flux.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error: " + error),
                () -> {
                    System.out.println("Done");
                });

        Thread.sleep(4000);
    }


}
