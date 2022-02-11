package ws.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ws.spring.dao.ArticleRepository;
import ws.spring.dao.UserRepository;
import ws.spring.model.Article;
import ws.spring.model.User;
import ws.spring.qualifier.RepositoryProvider;
import ws.spring.service.ArticleProvider;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("repoProvider")
//@Primary
@RepositoryProvider
@DependsOn("userService")
public class RepositoryArticleProvider implements ArticleProvider {
    private ArticleRepository articleRepository;
    private UserRepository userRepository;

    @Autowired
    public RepositoryArticleProvider(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        User defaultAuthor = userRepository.findByUsername("author").orElse(null);
        List.of(
                new Article("Spring Data is Awesome", "Spring Data is great beacuse ..."),
                new Article("Spring Data is Bold", "Should I use Spring Data ..."),
                new Article("Spring Data is Easy", "There are several ways to configure Spring Data.")
        ).forEach(art -> {
            art.setAuthor(defaultAuthor);
            articleRepository.create(art);
        });
    }

//    public void setArticleRepository(ArticleRepository articleRepository) {
//        this.articleRepository = articleRepository;
//    }

    @Override
    public Collection<Article> getArticles() {
        return articleRepository.findAll();
    }
}
