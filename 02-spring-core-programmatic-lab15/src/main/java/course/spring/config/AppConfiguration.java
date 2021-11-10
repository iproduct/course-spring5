package course.spring.config;

import course.spring.qualifiers.Alternative;
import course.spring.qualifiers.Mock;
import course.spring.service.ArticlePresenter;
import course.spring.service.ArticleProvider;
import course.spring.service.impl.AlternativeArticleProvider;
import course.spring.service.impl.ConsoleArticlePresenter;
import course.spring.service.impl.MockArticleProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.inject.Qualifier;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackageClasses = course.spring.AnnotationConfigDemo.class)
public class AppConfiguration {
    @Bean(name = "mockProvider")
    @Mock
    public ArticleProvider mockProvider() {
        return new MockArticleProvider();
    }
    @Bean(name = "alternativeProvider")
    @Alternative
    public ArticleProvider alternativeProvider() {
        return new AlternativeArticleProvider();
    }

    @Bean(name = "presenter")
    public ArticlePresenter presenter(@Alternative ArticleProvider provider) {
        return new ConsoleArticlePresenter(provider);
//        return new ConsoleArticlePresenter(mockProvider());
    }
}
