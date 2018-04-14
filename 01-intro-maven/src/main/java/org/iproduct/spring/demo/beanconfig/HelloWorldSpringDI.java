package org.iproduct.spring.demo.beanconfig;

import org.springframework.context.support.GenericApplicationContext;

public class HelloWorldSpringDI {
    public static void main(String[] args) {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext
//                ("spring/app-context.xml");
//        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
//        XmlBeanDefinitionReader rdr = new XmlBeanDefinitionReader(factory);
//        rdr.loadBeanDefinitions(new
//                ClassPathResource("spring/app-context.xml"));
//        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("spring/app-context.xml");
        GenericApplicationContext ctx = new GenericApplicationContext();
        ctx.registerBean("provider", MockArticleProvider.class);
        ctx.registerBean("presenter", ConsoleArticlePresenter.class,
                () -> new ConsoleArticlePresenter(ctx.getBean("provider", ArticleProvider.class)));
        ctx.refresh();

        ArticlePresenter mr = ctx.getBean("presenter", ArticlePresenter.class);
//        MessageRenderer mr = factory.getBean("renderer", MessageRenderer.class);
        mr.present();
    }
}
