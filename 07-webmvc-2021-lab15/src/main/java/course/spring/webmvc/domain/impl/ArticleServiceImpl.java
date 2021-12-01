package course.spring.webmvc.domain.impl;

import course.spring.webmvc.dao.ArticleRepository;
import course.spring.webmvc.dao.UserRepository;
import course.spring.webmvc.domain.ArticleService;
import course.spring.webmvc.entity.Article;
import course.spring.webmvc.entity.User;
import course.spring.webmvc.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepo;
    private UserRepository userRepo;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepo, UserRepository userRepository) {
        this.articleRepo = articleRepo;
        this.userRepo = userRepository;
    }

    @Override
    public List<Article> findAll() {
        List<Article> articles = articleRepo.findAll();
        articles.forEach(article -> article.setAuthorName(
                userRepo.findById(article.getAuthorId()).orElseGet(
                        () ->new User("", "", "", "")).getName())
        );
        return articles;
    }

    @Override
    public Article findById(String articleId) {
        Article article = articleRepo.findById(articleId).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Article with ID=%s not found.", articleId)));
        article.setAuthorName(userRepo.findById(article.getAuthorId()).orElseGet(
                () ->new User("", "", "", "")).getName());
        return article;
    }

    @Override
    public Article findByArticlename(String articlename) {
        Article article = articleRepo.findById(articlename).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Article '%s' not found.", articlename)));
        article.setAuthorName(userRepo.findById(article.getAuthorId()).orElseGet(
                () ->new User("", "", "", "")).getName());
        return article;
    }

    @Override
    public Article create(Article article) {
        article.setId(null);
        article.setCreated(LocalDateTime.now());
        article.setModified(LocalDateTime.now());
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return articleRepo.insert(article);
    }

    @Override
    public Article update(Article article) {
        Article old = findById(article.getId());
        article.setCreated(old.getCreated());
        article.setModified(LocalDateTime.now());
        return articleRepo.save(article);
    }

    @Override
    public Article deleteById(String articleId) {
        Article old = findById(articleId);
        articleRepo.deleteById(articleId);
        return old;
    }

    @Override
    public long count() {
        return articleRepo.count();
    }
}
