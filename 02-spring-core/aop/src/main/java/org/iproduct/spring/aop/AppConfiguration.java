package org.iproduct.spring.aop;

import org.iproduct.spring.aop.service.ArticlePresenter;
import org.iproduct.spring.aop.service.ArticleProvider;
import org.iproduct.spring.aop.service.ConsoleArticlePresenter;
import org.iproduct.spring.aop.service.MockArticleProvider;
import org.springframework.context.annotation.*;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
//@EnableAspectJAutoProxy
//@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
@ComponentScan(basePackages = "org.iproduct.spring.aop")
public class AppConfiguration {

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster
                = new SimpleApplicationEventMulticaster();

        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor()); //or Executors.newCachedThreadPool()
        return eventMulticaster;
    }
}
