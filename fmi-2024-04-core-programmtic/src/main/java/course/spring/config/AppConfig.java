package course.spring.config;

import course.spring.dao.PostRepository;
import course.spring.dao.UserRepository;
import course.spring.dao.impl.LongKeyGenerator;
import course.spring.dao.impl.PostRepositoryInMemory;
import course.spring.dao.impl.UserRepositoryInMemory;
import course.spring.domain.PostProvider;
import course.spring.domain.impl.RepoPostProvider;
import course.spring.presenter.PostPresenter;
import course.spring.presenter.impl.ConsolePostPresenter;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:posts.properties")
@ComponentScan(basePackageClasses = {course.spring.Main.class})
public class AppConfig {

    @Bean("keyGen")
    @Scope("prototype")
    public LongKeyGenerator longKeyGenerator() {
        return new LongKeyGenerator();
    }

    @Bean
    public PostRepository postRepository(LongKeyGenerator keyGenerator) {
        return new PostRepositoryInMemory(keyGenerator);
    }

    @Bean
    @DependsOn("keyGen")
    public UserRepository userRepository() {
        return new UserRepositoryInMemory(longKeyGenerator());
    }

    @Bean(initMethod = "init")
    public RepoPostProvider repoPostProvider(PostRepository postRepo) {
        return new RepoPostProvider(postRepo);
    }

    @Bean
    public PostPresenter presenter(PostProvider[] providers) {
        var presenter =  new ConsolePostPresenter();
        presenter.setPostProviders(providers);
        return presenter;
    }
}
