package coredemo.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SystemArchitecture {

    @Pointcut("within(coredemo.domain..*) && @within(org.springframework.stereotype.Repository)")
    public void inDaoLayer() {}

    @Pointcut("within(coredemo.domain..*) && @within(org.springframework.stereotype.Service)")
    public void inServiceLayer() {}

    @Pointcut("within(coredemo.model..*)")
    public void inModelLayer() {}

    @Pointcut("inDaoLayer() || inServiceLayer() || inModelLayer()")
    public void inDomainLayer() {}
}
