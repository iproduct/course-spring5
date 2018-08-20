package org.iproduct.spring.webmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("org.iproduct.spring.webmvc.service")
@PropertySource("classpath:application.properties")
public class SpringRootConfig {
}
