package course.spring.webfluxdemo.domain;

import course.spring.webfluxdemo.model.Quote;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class QuotesService {

    private List<Quote> quotes = Arrays.asList(
            new Quote("VMW", 215.35),
            new Quote("GOOG", 309.17),
            new Quote("CTXS", 112.11),
            new Quote("DELL", 150.93),
            new Quote("MSFT", 125.19),
            new Quote("ORCL", 172.15),
            new Quote("RHT", 171.35)
    );

    public Flux<Quote> getQotesStream(Duration period){
        return Flux.interval(period)
            .map(index -> {
                Quote quote = quotes.get((int)(index % quotes.size()));
                return new Quote(quote.getId(), quote.getSymbol(),
                    quote.getPrice() * (0.95 + 0.1 * Math.random()));
            }).share()
            .log();
    }
}
