package course.spring.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("beanB")
public class CircularDependencyBeanB {
    private CircularDependencyBeanA beanA;
    private String message = "Hi from Bean B!";

//    @Autowired
//    public CircularDependencyBeanB(CircularDependencyBeanA beanA) {
//        this.beanA = beanA;
//    }

    public String getMessage() {
        return message;
    }

    public CircularDependencyBeanA getBeanA() {
        return beanA;
    }

    @Autowired
    public void setBeanA(CircularDependencyBeanA beanA) {
        this.beanA = beanA;
    }
}

