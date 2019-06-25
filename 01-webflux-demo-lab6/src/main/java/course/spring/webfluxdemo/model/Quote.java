package course.spring.webfluxdemo.model;

import lombok.*;

import java.util.concurrent.atomic.AtomicLong;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Quote {
    private static final AtomicLong nextId = new AtomicLong(0);
    private long id = nextId.incrementAndGet();
    @NonNull
    private String symbol;
    @NonNull
    private double price;
}
