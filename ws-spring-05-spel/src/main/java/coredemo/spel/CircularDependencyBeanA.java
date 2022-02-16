package coredemo.spel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("beanA")
public class CircularDependencyBeanA {
    private CircularDependencyBeanB beanB;
    private String message = "Hi from Bean A!";

//    @Autowired
//    public CircularDependencyBeanA(@Lazy CircularDependencyBeanB beanB) {
//        this.beanB = beanB;
//    }

    public String getMessage() {
        return message;
    }

    public CircularDependencyBeanB getBeanB() {
        return beanB;
    }

    @Autowired
    public void setBeanB(CircularDependencyBeanB beanB) {
        this.beanB = beanB;
    }
}

