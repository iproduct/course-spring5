package org.iproduct.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.aop.service.ArticlePresenter;
import org.iproduct.spring.aop.service.ArticleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//@Slf4j
public class MainApplication {
    final static Logger log = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String... args) throws InterruptedException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ArticlePresenter presenter = ctx.getBean("presenter", ArticlePresenter.class);
        presenter.present();

        ArticleProvider provider = ctx.getBean("provider", ArticleProvider.class);
        provider.addArticle(new Article("Spring AOP Article", "Spring AOP Article contents ..."));
        provider.addArticle(new Article("Spring Eventing Article", "Spring eventing article contents ..."));
        provider.addArticle(new Article("Spring AOP Forever", "Spring AOP can is a whole new universe ..."));
        presenter.present();
//
        System.out.println();

        if(ctx.getBean("provider") instanceof UsageTracked) {
            UsageTracked providerUsage = (UsageTracked) ctx.getBean("provider");
            log.info("Provider usages: {}", providerUsage.getUseCount());

        }
        if(ctx.getBean("presenter") instanceof UsageTracked) {
            UsageTracked presenterUsage =  (UsageTracked) ctx.getBean("presenter");
            log.info("Presenter usages: {}", presenterUsage.getUseCount());
        }


    }
}
