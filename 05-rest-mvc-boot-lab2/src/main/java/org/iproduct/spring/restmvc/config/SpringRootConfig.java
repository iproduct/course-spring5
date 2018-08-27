package org.iproduct.spring.restmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"org.iproduct.spring.restmvc.service", "org.iproduct.spring.restmvc.dao"})
@PropertySource("classpath:application.properties")
@Import({WebSecurityConfig.class, MongoConfig.class})
public class SpringRootConfig {
}
