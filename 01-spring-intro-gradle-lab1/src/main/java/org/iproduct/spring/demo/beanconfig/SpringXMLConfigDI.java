package org.iproduct.spring.demo.beanconfig;

import org.iproduct.spring.demo.beanconfig.service.ArticlePresenter;
import org.iproduct.spring.demo.beanconfig.service.ArticleProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class SpringXMLConfigDI {
    public static void main(String[] args) {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(SpringProgrammaticAnnotationConfig.class);
        ArticlePresenter presenter = ctx.getBean("presenter", ArticlePresenter.class);
        presenter.present();

        ArticleProvider provider = ctx.getBean("provider", ArticleProvider.class);
        provider.addArticle(new Article("Spring AOP Article", "Spring AOP Article contents ..."));
        provider.addArticle(new Article("Spring Eventing Article", "Spring eventing article contents ..."));
        provider.addArticle(new Article("Spring AOP Forever", "Spring AOP can is a whole new universe ..."));
        presenter.present();

//        ExpressionParser parser = new SpelExpressionParser();
//        double value = parser.parseExpression("((42 div 5) % 3 + 1) * 10").getValue(Double.class);
//        System.out.println(value);

//        StandardEvaluationContext context = new StandardEvaluationContext();
//        context.setBeanResolver(new BeanFactoryResolver(ctx.getBeanFactory()));
//        List<Article> articles = parser.parseExpression(
//                "@provider.articles").getValue(context, List.class);
//        System.out.println(articles);
//
//        String beanPropWithDefault = parser.parseExpression(
//                "@beanA.message ?: 'default message'").getValue(context, String.class);
//        System.out.println(beanPropWithDefault);
//
//        List<Article> springTitles = parser.parseExpression(
//                "@provider.articles.?[title matches '.*Spring.*'].![title]")
//                .getValue(context, List.class);
//        System.out.println(springTitles);
//
//        CarPark park2 = parser.parseExpression("T(org.iproduct.spring.demo.beanconfig.CarPark).create({" +
//                "new org.iproduct.spring.demo.beanconfig.Car(\"Opel\", 3, 2550, 2013)," +
//                "new org.iproduct.spring.demo.beanconfig.Car(\"VW\", 2, 1350, 2010)," +
//                "new org.iproduct.spring.demo.beanconfig.Car(\"Audi\", 5, 2200, 2017)" +
//                "})").getValue(CarPark.class);
//        EvaluationContext context3 = new StandardEvaluationContext(park2);
//        Expression expression3 = parser.parseExpression("cars.![make]");
//        List result3 = (List) expression3.getValue(context3);
//        System.out.println("SpEL:" +result3);

    }
}
