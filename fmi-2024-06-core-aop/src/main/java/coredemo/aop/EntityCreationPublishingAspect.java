package coredemo.aop;

import coredemo.events.EntityCreationEvent;
import coredemo.model.Article;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Aspect
public class EntityCreationPublishingAspect implements  ApplicationContextAware{
    final static Logger log = LoggerFactory.getLogger(EntityCreationPublishingAspect.class);

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Pointcut("@target(org.springframework.stereotype.Repository)")
    public void repositoryMethods() {
    }

    @Pointcut("(execution(* *..add*(coredemo.model.Article,..)) || execution(* *..create*(..))) && args(article, ..)")
    public void addMethods(Article article) {
    }

    @Pointcut("repositoryMethods() && addMethods(article)")
    public void entityCreationMethods(Article article) {
    }

    @AfterReturning(value = "entityCreationMethods(article)", returning = "retVal")
    public void logMethodCall(JoinPoint jp, Article article, Object retVal) {
        Pattern p = Pattern.compile("[^.]+$");
//        Matcher m = p.matcher(jp.getArgs()[0].getClass().getName());
        Matcher m = p.matcher(article.getClass().getName());
        String entityName = m.find() ? m.group(): "";

        log.info(eventPublisher + "");
        if(eventPublisher != null) {
            eventPublisher.publishEvent(
                    new EntityCreationEvent(this, entityName, retVal));
        }
    }

    @AfterThrowing(value = "entityCreationMethods(article)", throwing = "exception")
    public void logException(JoinPoint jp, Article article, Exception exception) {
        Pattern p = Pattern.compile("[^.]+$");
//        Matcher m = p.matcher(jp.getArgs()[0].getClass().getName());
        Matcher m = p.matcher(article.getClass().getName());
        String entityName = m.find() ? m.group(): "";
        log.error("Error creating entity " +  entityName + ":" + article, exception);
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        eventPublisher = (ApplicationEventPublisher) applicationContext;
    }
}
