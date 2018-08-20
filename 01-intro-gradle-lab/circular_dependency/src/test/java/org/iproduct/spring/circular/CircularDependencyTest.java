package org.iproduct.spring.circular;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {TestConfig.class})
@SpringJUnitConfig(TestConfig.class)
@Slf4j
public class CircularDependencyTest {
//    @Autowired
//    ApplicationContext context;

//    @Bean
//    public CircularDependencyBeanA getCircularDependencyBeanA() {
//        return new CircularDependencyBeanA();
//    }

//    @org.junit.Test
    @Test
    public void givenCircularDependency_whenSetterInjection_thenItWorks(
            @Autowired ApplicationContext context) {
        // Empty test; we just want the context to load
        CircularDependencyBeanA beanA = context.getBean(CircularDependencyBeanA.class);
        log.info(beanA.getMessage());
        Assertions.assertEquals("Hi from Bean A!", beanA.getMessage());
        log.info(beanA.getBeanB().getMessage());
        Assertions.assertEquals("Hi from Bean B!", beanA.getBeanB().getMessage());
    }
}
