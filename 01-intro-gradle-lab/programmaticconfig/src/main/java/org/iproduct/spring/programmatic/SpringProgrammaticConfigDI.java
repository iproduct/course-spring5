package org.iproduct.spring.programmatic;


import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

public class SpringProgrammaticConfigDI {
    public static void main(String[] args) {
        GenericApplicationContext ctx = new GenericApplicationContext();
        ctx.registerBean("provider", MockArticleProvider.class);
//                () -> new MockArticleProvider(new String[] {"A", "B", "C"}));
        ctx.registerBean("presenter", ConsoleArticlePresenter.class,
                () -> new ConsoleArticlePresenter(
                        ctx.getBean("provider", ArticleProvider.class)));

        AutowiredAnnotationBeanPostProcessor postProcessor = new AutowiredAnnotationBeanPostProcessor();
        postProcessor.setBeanFactory(ctx);

        ctx.refresh();

        ArticlePresenter mr = ctx.getBean("presenter", ArticlePresenter.class);
//        MessageRenderer mr = factory.getBean("renderer", MessageRenderer.class);
        mr.present();
    }

}
