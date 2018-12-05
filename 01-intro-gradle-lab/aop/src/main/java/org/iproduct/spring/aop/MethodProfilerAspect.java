package org.iproduct.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
//@Slf4j
public class MethodProfilerAspect {
    final static Logger log = LoggerFactory.getLogger(MethodProfilerAspect.class);

    @Pointcut("@within(org.springframework.stereotype.Repository)")
    public void repositoryClassMethods() {
    }

    @Around("repositoryClassMethods()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        Object retval = pjp.proceed();
        long end = System.nanoTime();
        String methodName = pjp.getSignature().getName();
        log.info("Execution of " + methodName + " took " +
                (end - start) + " ns");
        return retval;

        //Alternative implementation
//        String methodName = pjp.getSignature().getName();
//        StopWatch clock = new StopWatch("Profiling for '" + methodName);
//        try {
//            clock.start(pjp.toShortString());
//            return pjp.proceed();
//        } finally {
//            clock.stop();
//            log.info(clock.prettyPrint());
//        }
    }
}

