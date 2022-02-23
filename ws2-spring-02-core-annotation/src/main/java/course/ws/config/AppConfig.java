package course.ws.config;

import course.ws.client.ArticlePresenter;
import course.ws.client.impl.ConsoleArticlePresenter;
import course.ws.dao.ArticleRepository;
import course.ws.dao.IdGenerator;
import course.ws.dao.UserRepository;
import course.ws.dao.impl.ArticleRepositoryMemoryImpl;
import course.ws.dao.impl.LongIdGenerator;
import course.ws.dao.impl.UserRepositoryMemoryImpl;
import course.ws.service.ArticleProvider;
import course.ws.service.UserService;
import course.ws.service.impl.ArticleProviderImpl;
import course.ws.service.impl.MockArticleProvider;
import course.ws.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan("course.ws")
public class AppConfig {

    @Bean("mockProvider")
    public ArticleProvider mockArticleProvider() {
        return new MockArticleProvider();
    }

    @Bean("longIdGen")
//    @Scope("prototype")
    public IdGenerator longIdGenerator(){
        return new LongIdGenerator();
    }

    @Bean
    public ArticleRepository articleRepositoryMemoryImpl(){
        return new ArticleRepositoryMemoryImpl(longIdGenerator());
    }

    @Bean("defaultProvider")
    public ArticleProvider defualtArticleProvider(ArticleRepository articleRepository) {
        return new ArticleProviderImpl(articleRepository);
    }

    @Bean("presenter")
    public ArticlePresenter articlePresenter(@Qualifier("defaultProvider") ArticleProvider provider) {
        ConsoleArticlePresenter presenter = new ConsoleArticlePresenter();
        presenter.setProvider(provider);
        return presenter;
    }

    @Bean
    public UserRepository userRepository(){
        return new UserRepositoryMemoryImpl(longIdGenerator());
    }

//    @Bean(initMethod = "init")
//    public UserServiceImpl userServiceImpl(){
//        return new UserServiceImpl(userRepository());
//    }
}
