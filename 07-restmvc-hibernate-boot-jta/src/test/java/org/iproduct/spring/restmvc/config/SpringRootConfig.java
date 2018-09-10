package org.iproduct.spring.restmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"org.iproduct.spring.restmvc"})
@PropertySource("classpath:application.properties")
@Import(DataSourceConfig.class)
public class SpringRootConfig {
}
