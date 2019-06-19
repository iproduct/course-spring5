package course.spring.springcoredemo.config;

import course.spring.springcoredemo.dao.AlternativeArticleRepository;
import course.spring.springcoredemo.dao.ArticlesRepository;
import course.spring.springcoredemo.dao.ArticlesRepositoryImpl;
import course.spring.springcoredemo.domain.ArticlePresenter;
import course.spring.springcoredemo.domain.ConsoleArticlePresenter;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource(value = "classpath:articles.properties", ignoreResourceNotFound = true)
@ComponentScan("course.spring.springcoredemo")
@ImportResource("classpath:app-config.xml")
public class AppConfig {
//    @Bean(name="provider", initMethod = "init")
//    public ArticlesRepository getArticlesRepository() {
//        return new ArticlesRepositoryImpl();
//    }
//
//    @Bean(name="alternativeProvider")
//    public ArticlesRepository getAlternativeRepository() {
//        return new AlternativeArticleRepository();
//    }
//
//    @Bean("presenter")
//    @DependsOn("provider")
//    public ArticlePresenter getArticlePresenter() {
//        ConsoleArticlePresenter presenter = new ConsoleArticlePresenter();
//        presenter.setPresenterData(getAlternativeRepository(), getTitle());
//        return presenter;
//    }
//
    @Bean("title")
    public String getTitle() {
        return "New DI Title";
    }

}
