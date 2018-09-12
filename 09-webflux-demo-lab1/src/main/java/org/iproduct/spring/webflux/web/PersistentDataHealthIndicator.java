package org.iproduct.spring.webflux.web;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PersistentDataHealthIndicator implements HealthIndicator {

//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ArticleRepository articleRepository;

    @Override
    public Health health() {
        return Health
                .up()
                .withDetail("articles.count", 1)
                .withDetail("users.count", 2)
                .build();
    }

}
