package org.iproduct.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.iproduct.spring.aop.events.EntityCreationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@Component
@Aspect
public class EntityCreationPublishingAspect222 implements  ApplicationContextAware{
    final static Logger log = LoggerFactory.getLogger(EntityCreationPublishingAspect222.class);

//    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Pointcut("@target(org.springframework.stereotype.Repository)")
    public void repositoryMethods() {
    }

    @Pointcut("execution(* *..add*(..)) && args(article, ..)")
    public void addMethods(Article article) {
    }

    @Pointcut("repositoryMethods() && addMethods(article)")
    public void entityCreationMethods(Article article) {
    }

    @AfterReturning(value = "entityCreationMethods(article)")
    public void logMethodCall(JoinPoint jp, Article article) {
        Pattern p = Pattern.compile("[^.]+$");
//        Matcher m = p.matcher(jp.getArgs()[0].getClass().getName());
        Matcher m = p.matcher(article.getClass().getName());
        String entityName = m.find() ? m.group(): "";

        log.info(eventPublisher + "");
        if(eventPublisher != null) {
            eventPublisher.publishEvent(
                    new EntityCreationEvent(this, entityName, article));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        eventPublisher = (ApplicationEventPublisher) applicationContext;
    }
}
