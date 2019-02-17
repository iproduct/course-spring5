package org.iproduct.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SystemArchitecture {

    @Pointcut("within(org.iproduct.spring.aop..*) && @within(org.springframework.stereotype.Repository)")
    public void inDaoLayer() {}

    @Pointcut("within(org.iproduct.spring.aop..*) && @within(org.springframework.stereotype.Service)")
    public void inServiceLayer() {}

    @Pointcut("inDaoLayer() || inServiceLayer()")
    public void inDomainLayer() {}
}
