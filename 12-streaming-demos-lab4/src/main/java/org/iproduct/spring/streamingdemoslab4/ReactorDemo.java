package org.iproduct.spring.streamingdemoslab4;

import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.MILLIS;

public class ReactorDemo {

    public static void main(String[] args) throws InterruptedException {
        Flux<Long> ints = Flux.interval(Duration.ofSeconds(0), Duration.ofSeconds(1))
            .map(i -> {
                if( i < 5 ) return i;
                throw new RuntimeException("Got to be 4");
            });
//        ints.subscribe(
//            i -> System.out.println(i),
//            err -> System.err.println(err.getMessage()),
//            () -> System.out.println("Stream completed.")
//        );

        Flux<String> flux = Flux.generate(
                () -> 0,
                (state, sink) -> {
                    sink.next("3 x " + state + " = " + 3*state);
                    if(state == 10)
                        sink.complete();
                    return state + 1;
                }
        );

//        flux.delayElements(Duration.ofSeconds(1))
//            .subscribe(
//                System.out::println,
//                err -> System.err.println(err.getMessage()),
//                () -> System.out.println("Stream completed.")
//            );

        EmitterProcessor<String> emitter = EmitterProcessor.create();
        FluxSink<String> sink = emitter.sink();
        Flux<String> result = emitter.publishOn(Schedulers.single())
                .map(String::toUpperCase)
                .delayElements(Duration.of(1000, MILLIS))
                .filter(s -> s.startsWith("HELLO"));


        result.map(data -> "Subscriber 1: " + data).subscribe(System.out::println);
        result.map(data -> "Subscriber 2: " + data).subscribe(System.out::println);

        sink.next("Hello World!"); // emit - non blocking
        sink.next("Goodbye World!");
        sink.next("Hello Trayan!");

        Thread.sleep(18000);
    }
}
