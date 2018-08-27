package org.iproduct.spring.demo.beanconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAspectJAutoProxy
//@PropertySource(value = "classpath:articles.properties", ignoreResourceNotFound = true)
@ComponentScan(basePackages = "org.iproduct.spring.demo.beanconfig")
public class SpringProgrammaticAnnotationConfig {

}
