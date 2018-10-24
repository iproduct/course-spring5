package org.iproduct.spring.circular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanA {
    private BeanB beanB;

    private String  content = "Bean A content ...";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BeanB getBeanB() {
        return beanB;
    }

    public void setBeanB(BeanB beanB) {
        this.beanB = beanB;
    }

    @Autowired
    public BeanA(BeanB beanB) {
        this.beanB = beanB;
    }
}
