package org.iproduct.spring.aop;

public interface UsageTracked {
    long incrementUseCount();
    long getUseCount();
}
