package org.iproduct.spring.programmatic;


import org.springframework.context.support.GenericApplicationContext;

public class SpringProgrammaticConfigDI {
    public static void main(String[] args) {
        GenericApplicationContext ctx = new GenericApplicationContext();
        ctx.registerBean("provider", MockArticleProvider.class);
        ctx.registerBean("presenter", ConsoleArticlePresenter.class,
                () -> new ConsoleArticlePresenter(
                        ctx.getBean("provider", ArticleProvider.class)));
        ctx.refresh();

        ArticlePresenter mr = ctx.getBean("presenter", ArticlePresenter.class);
//        MessageRenderer mr = factory.getBean("renderer", MessageRenderer.class);
        mr.present();
    }

}
