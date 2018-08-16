package org.iproduct.spring.comments;

import lombok.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Component
@PropertySource("classpath:comments.properties")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    private static AtomicLong id = new AtomicLong();
    @NonNull
    private String text;
    @NonNull
    private String email;
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();

    @PostConstruct
    private void init() {

    }

}
