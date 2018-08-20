package org.iproduct.spring.webmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan("org.iproduct.spring.webmvc.web")
@PropertySource("classpath:application.properties")
public class SpringWebConfig {
}
