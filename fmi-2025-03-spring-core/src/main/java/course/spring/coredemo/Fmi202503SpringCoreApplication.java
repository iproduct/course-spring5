package course.spring.coredemo;

import course.spring.coredemo.client.ArticlePresenterClient;
import course.spring.coredemo.config.AppConfig;
import course.spring.coredemo.model.Article;
import course.spring.coredemo.service.ArticleProvider;
import course.spring.coredemo.service.impl.ArticleProviderInMemory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Set;

//@SpringBootApplication
public class Fmi202503SpringCoreApplication {
    public static final List<Article> SAMPLE_ARTICLES = List.of(
            new Article("New in Spring 6", "API versions updated.", "Trayan Iliev", "Programming",
                    Set.of("spring 6", "novelty")),
            new Article("Hibernate API Intrinsics", "Hibernate provides many important customization options.",
                    "Trayan Iliev", "Programming", Set.of("hibernate", "jpa", "spring data jpa")),
            new Article("Spring AI Integration", "Spring Boot provides OLLAMA starter.", "Trayan Iliev", "AI",
                    Set.of("spring", "spring boot", "ollama", "ai"))
    );

    public static void main(String[] args) {
//        SpringApplication.run(Fmi202503SpringCoreApplication.class, args);
//        ApplicationContext ctx = new AnnotationConfigApplicationContext("course.spring.coredemo");
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        // Use provider to add articles
        ArticleProvider provider = ctx.getBean("articleProviderInMemory", ArticleProvider.class);
//        ArticleProvider provider = ctx.getBean("propertyArticleProvider", ArticleProvider.class);
        SAMPLE_ARTICLES.forEach(provider::createArticle);

        // Lookup presenter and present articles
        ArticlePresenterClient client = ctx.getBean(ArticlePresenterClient.class);
        client.presentArticles();
    }

}
