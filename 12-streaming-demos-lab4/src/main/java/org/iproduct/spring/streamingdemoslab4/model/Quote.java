package org.iproduct.spring.streamingdemoslab4.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Quote{
    String id;
    @NonNull
    private String symbol;
    @NonNull
    private double price;
    Instant instant = Instant.now();
}

