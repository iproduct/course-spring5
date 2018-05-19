package org.iproduct.spring.demo.beanconfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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

public class HelloWorldSpringAnnotated {
    public static void main(String... args) {
//        ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
        ApplicationContext ctx = new AnnotationConfigApplicationContext("org.iproduct.spring.demo.beanconfig");
        ArticlePresenter mr = ctx.getBean("presenter", ArticlePresenter.class);
        mr.present();

        //SpEL test
        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, HelloWorldSpringAnnotated.class.getClassLoader());
        ExpressionParser expressionParser = new SpelExpressionParser(config);
        Expression expression = expressionParser.parseExpression("'Any string !'.replace(\" \", \"\").length()");
        int result = expression.getValue(Integer.class);


        CarPark park = new CarPark();
        park.getCars().add(new Car("Opel", 5,
                new Engine(4, 2500, 16, "engine model1"),2500, 2013 ));
        park.getCars().add(new Car("Opel1", 5,
                new Engine(4, 2500, 16, "engine model2"),2500, 2013 ));
        park.getCars().add(new Car("Opel2", 5,
                new Engine(4, 2500, 16, "engine model3"),2500, 2013 ));
        park.getCars().add(new Car("Opel3", 5,
                new Engine(4, 2500, 16, "engine model4"),2500, 2013 ));


        Expression expression2 = expressionParser.parseExpression("cars.![make]");

        EvaluationContext context = new StandardEvaluationContext(park);
//        expression2.setValue(context,"new engine model");
        List result2 = (List) expression2.getValue(context);
        System.out.println("SpEL:" +result2);

        EvaluationContext context2 = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        Map mapOfMaps = (Map) expressionParser
                .parseExpression("{name:{first:'Nikola',last:'Tesla'},dob:{day:10,month:'July',year:1856}}")
                .getValue(context2);
        System.out.println("SpEL:" +mapOfMaps.get("name"));

        // create an array of integers
        List<Integer> primes = new ArrayList<Integer>();
        primes.addAll(Arrays.asList(2,3,5,7,11,13,17));

// create parser and set variable 'primes' as the array of integers
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context3 = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        context.setVariable("primes", primes);

// all prime numbers > 10 from the list (using selection ?{...})
// evaluates to [11, 13, 17]
        List<Integer> primesGreaterThanTen = (List<Integer>) parser.parseExpression(
                "#primes.?[#this>10]").getValue(context);

        System.out.println("SpEL:" + primesGreaterThanTen);
    }
}