package course.spring.config;

import course.spring.dao.ArticlesRepository;
import course.spring.dao.IdGenerator;
import course.spring.dao.impl.ArticleRepositoryInMemory;
import course.spring.dao.impl.IdGeneratorLong;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:article.properties")
@ComponentScan("course.spring")
//@ImportResource("classpath:beans.xml")
public class AppConfig {
    @Value("${initialGeneratedId:0}")
    private Long initialGeneratedId = 0L;

    @Bean
    @Scope("prototype")
    public IdGenerator<Long> idGenerator() {
        return new IdGeneratorLong(initialGeneratedId);
    }

    @Bean
    public ArticlesRepository articleRepo(IdGenerator<Long> idGenerator) {
        return new ArticleRepositoryInMemory(idGenerator);
    }
}
