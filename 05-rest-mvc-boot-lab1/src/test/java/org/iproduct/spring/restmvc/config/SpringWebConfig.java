package org.iproduct.spring.restmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("org.iproduct.spring.restmvc.web")
@PropertySource("classpath:application.properties")
public class SpringWebConfig  implements WebMvcConfigurer {
}
