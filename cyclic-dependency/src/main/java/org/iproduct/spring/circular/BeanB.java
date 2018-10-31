package org.iproduct.spring.circular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BeanB {
    private BeanA beanA;

    public BeanA getBeanA() {
        return beanA;
    }

    public void setBeanA(BeanA beanA) {
        this.beanA = beanA;
    }

    @PostConstruct
    public void init() {
        beanA.setBeanB(this);
    }

    @Autowired
    public BeanB(BeanA beanA) {
        this.beanA = beanA;
    }
}
