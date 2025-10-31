package course.spring.coredemo.service.impl;

import course.spring.coredemo.model.Article;
import course.spring.coredemo.service.ArticleProvider;
import course.spring.coredemo.service.UserService;
import course.spring.coredemo.util.IdGenerator;
import course.spring.coredemo.util.impl.LongIdGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service("propertyArticleProvider")
@Qualifier("fromProperties")
//@PropertySource("classpath:articles.properties")
public class PropertyFileArticleProvider implements ArticleProvider {
    @Value("${articles.titles}")
    private String[] articlesTitles;

    private List<Article> articles;

    private UserService userService;

    private IdGenerator<Long> idGenerator;

    @Autowired
    public PropertyFileArticleProvider(UserService userService, IdGenerator<Long> idGenerator) {
        this.userService = userService;
        this.idGenerator = idGenerator;
    }

    @PostConstruct
    public void init() {
        articles = Arrays.stream(articlesTitles)
                .map(title ->
                        new Article(idGenerator.getNextId(), title, title + " content ...",
                                userService.getUsers().get(0), "Programming",
                                new HashSet(Arrays.asList(title.split("\\W+")))))
                .collect(Collectors.toList());
    }

    @Override
    public List<Article> getArticles() {
        return articles;
    }

    @Override
    public Article createArticle(Article article) {
        return null;
    }
}
