package org.iproduct.spring.comments;

import lombok.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
@PropertySource(value = "classpath:comments.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:emails.properties", ignoreResourceNotFound = true)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Value("${comments.texts}")
    @ToString.Exclude
    private transient String[] texts;

    @Value("${emails}")
    @ToString.Exclude
    private transient String[] emails;

    private static AtomicInteger id = new AtomicInteger();

    @NonNull
    private String text;
    @NonNull
    private String email;
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();

    @PostConstruct
    private void init() {
        text = texts[id.getAndIncrement() % texts.length];
        email = emails[id.getAndIncrement() % emails.length];
    }

}
