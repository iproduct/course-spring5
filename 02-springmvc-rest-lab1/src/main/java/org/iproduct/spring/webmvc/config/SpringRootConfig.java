package org.iproduct.spring.webmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"org.iproduct.spring.webmvc.service", "org.iproduct.spring.webmvc.dao"})
@PropertySource("classpath:application.properties")
@Import(MongoConfig.class)
public class SpringRootConfig {
}
