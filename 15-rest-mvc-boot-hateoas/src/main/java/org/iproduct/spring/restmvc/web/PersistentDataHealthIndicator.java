package org.iproduct.spring.restmvc.web;

import org.iproduct.spring.restmvc.dao.ArticleRepository;
import org.iproduct.spring.restmvc.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PersistentDataHealthIndicator implements HealthIndicator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Health health() {
        return Health
                .up()
                .withDetail("articles.count", articleRepository.count())
                .withDetail("users.count", userRepository.count())
                .build();
    }

}
