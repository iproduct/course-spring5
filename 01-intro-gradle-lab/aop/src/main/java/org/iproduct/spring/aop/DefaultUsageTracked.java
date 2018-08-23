package org.iproduct.spring.aop;


import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class DefaultUsageTracked implements UsageTracked {
    private AtomicLong counter = new AtomicLong(0L);

    @Override
    public long incrementUseCount() {
        return counter.incrementAndGet();
    }

    @Override
    public long getUseCount() {
        return counter.get();
    }
}
