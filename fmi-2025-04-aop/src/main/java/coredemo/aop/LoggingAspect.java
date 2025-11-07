package coredemo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Order(1)
//@Slf4j
public class LoggingAspect {
    final static Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@target(org.springframework.stereotype.Repository)")
    public void repositoryMethods() {
    }

    @Before("repositoryMethods()")
    public void logMethodCall(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        log.info("!!! Before invoking: " + methodName + " with args: " + Arrays.toString(jp.getArgs()));
    }
}
