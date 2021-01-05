package coredemo.spel;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SpelDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext("coredemo.spel");

        //@Value test
        ArticlePresenter mr = ctx.getBean("presenter", ArticlePresenter.class);
        mr.present();

        SpelParserConfiguration config = new SpelParserConfiguration(
                SpelCompilerMode.IMMEDIATE,
                SpelDemo.class.getClassLoader());

        ExpressionParser expressionParser = new SpelExpressionParser(config);

        double value = expressionParser.parseExpression("((42 div 5) % 3 + 1) * 10").getValue(Double.class);
        System.out.println(value);

        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(ctx.getBeanFactory()));
        String beanPropWithDefault = expressionParser.parseExpression(
                "@beanA.message != null ? @beanA.message : 'default message'")
                .getValue(context, String.class);
        System.out.println(beanPropWithDefault);

        Expression expression = expressionParser.parseExpression(
                "'valid alphabetic string' matches '[a-zA-Z\\s]+'");
        Boolean result = expression.getValue(Boolean.class);
        System.out.println(result);

        String result2 = expressionParser.parseExpression(
                "@beanA.beanB.message ?: 'default message'")
                .getValue(context, String.class);
        System.out.println(result2);

        List<String> springTitles = expressionParser.parseExpression(
                "@provider.articles.?[#this.title matches '.*Spring.*'].![title]")
                .getValue(context, List.class);
        System.out.println(springTitles);

//        //collections and properties
        CarPark park = new CarPark();
        park.getCars().add(new Car("Opel", "Astra",
                new Engine(4, 2500, 16, "engine model1"),2500, 2013 ));
        park.getCars().add(new Car("Opel1", "Zefira",
                new Engine(4, 2500, 16, "engine model2"),2500, 2013 ));
        park.getCars().add(new Car("Opel2", "Tigra",
                new Engine(4, 2500, 16, "engine model3"),2500, 2013 ));
        park.getCars().add(new Car("Opel3", "Cadet",
                new Engine(4, 2500, 16, "engine model4"),2500, 2013 ));

        StandardEvaluationContext carParkContext = new StandardEvaluationContext(park);
        List<String> result3 = expressionParser.parseExpression(
                "cars.![model][1]")
                .getValue(carParkContext, List.class);
        System.out.println(result3);

        // #this
        List<Integer> primes = new ArrayList<Integer>();
        primes.addAll(Arrays.asList(2,3,5,7,11,13,17));

        carParkContext.setVariable("primes", primes);
        List<Integer> result4 = expressionParser.parseExpression(
                "#primes.?[#this > 4]")
                .getValue(carParkContext, List.class);
        System.out.println(result4);

        // json like map initialization
        EvaluationContext context4 = new StandardEvaluationContext();
        Expression teslaExpr = expressionParser
                .parseExpression(
            "{" +
                    "{name:{first:'Nikola',last:'Tesla'},dob:{day:10,month:'July',year:1856}}," +
                    "{name:{first:'Isac',last:'Newton'},dob:{day:10,month:'July',year:1756}}" +
                "}");

        List listOfMaps = (List) teslaExpr.getValue(context4);
        System.out.println(listOfMaps);
        context4.setVariable("names", listOfMaps);
//        EvaluationContext context6 = new StandardEvaluationContext(listOfMaps);
        Expression exp4 = expressionParser.parseExpression(
                "#names.![#this['name']['first'] + ' ' + #this['name']['last']]");
        System.out.println("SpEL:" + exp4.getValue(context4));
    }
}
