package org.iproduct.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
//@Slf4j
public class UsageTracking {
    final static Logger log = LoggerFactory.getLogger(UsageTracking.class);

    @DeclareParents(value="org.iproduct.spring.aop.service..*)",
            defaultImpl=DefaultUsageTracked.class)
    public static UsageTracked mixin;

    @Before("org.iproduct.spring.aop.SystemArchitecture.inDomainLayer() " +
            "&& this(usageTracked) && target(target)")
    public void recordUsage(UsageTracked usageTracked, Object target) {
        log.info(">>> Incrementing usage count [{}]: {}",
                target.getClass().getName().toString(),
                usageTracked.incrementUseCount());
    }

}