package org.iproduct.spring.demo.beanconfig;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProfilingAspect {
    final static Logger log = LoggerFactory.getLogger(ProfilingAspect.class);

    @Pointcut("@within(org.springframework.stereotype.Repository)")
    public void repositoryClassMethods(){}

    @Around("repositoryClassMethods()")
    public Object measureExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        Object retVal = pjp.proceed();
        long end = System.nanoTime();
        String methodName = pjp.getSignature().getName();
        log.info("Execution of " + methodName + " took " + (end-start) + " ns");
        return retVal;
    }
}
