package org.iproduct.spring.streamingdemoslab4.service;

import org.iproduct.spring.streamingdemoslab4.model.Quote;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class QuotesGenerator {
    private Random rand = new Random();

    List<Quote> quotes = Arrays.asList(
            new Quote("VMW", 215.35),
            new Quote("GOOG", 309.17),
            new Quote("CTXS", 112.11),
            new Quote("DELL", 92.93),
            new Quote("MSFT", 255.19),
            new Quote("ORCL", 115.72),
            new Quote("RHT", 111.27)
    );

    public Flux<Quote> getQuotesStream(Duration period) {
        return Flux.interval(period)
            .map(index -> {
                Quote quote = quotes.get(index.intValue() % quotes.size());
                quote.setPrice(quote.getPrice() * (0.9 + 0.2 * rand.nextDouble()));
                return new Quote(quote.getSymbol(), quote.getPrice());
            })
            .share()
            .log();
    }
}
