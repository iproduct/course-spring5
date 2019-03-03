package org.iproduct.spring.coredemo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@Scope("prototype")
@Data
@ToString(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
class Article implements EnvironmentAware, DisposableBean {
    private static int nextId = 0;

    private Environment env;
    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @Value("${articles.titles}")
    private String[] articleTiles;

    private int numAricles;

    @NonNull
    @ToString.Include
    private String title;

    @NonNull
    @ToString.Include
    private String content;

    @ToString.Include
    private LocalDateTime createdDate = LocalDateTime.now();

    @PostConstruct
    public void init() {
        numAricles = env.getProperty("articles.number", Integer.class, 0);
        title = articleTiles[nextId++ % articleTiles.length];
        content = title + " content ...";
        log.info("Article created: " + this);
    }

    @PreDestroy
    public void cleanup() {
        log.info("Article to be cleaned up: " + this);
    }

    @Override
    public void destroy() throws Exception {
        log.info("Article to be destroyed: " + this);
    }
}
