package course.spring.intro.config;

import course.spring.intro.dao.ArticleRepository;
import course.spring.intro.service.ArticleService;
import course.spring.intro.service.impl.ArticleServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ArticleService articleService(ArticleRepository articleRepo) {
        return ArticleServiceImpl.createArticleService(articleRepo);
    }
}
