package course.spring.restjpa.service.impl;


import course.spring.restjpa.dto.ArticleRepository;
import course.spring.restjpa.dto.UserRepository;
import course.spring.restjpa.model.Article;
import course.spring.restjpa.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
        return articles;
    }

    @Override
    public Article findById(Long articleId) {
        Article article = articleRepo.findById(articleId).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Article with ID=%s not found.", articleId)));
        return article;
    }

    @Override
    public List<Article> findByTitle(String articlename) {
        return articleRepo.findByTitleContaining(articlename);
    }

    @Override
    public List<Article> findByTags(List<String> filterTags) {
        return articleRepo.findByTagsContaining(filterTags);
    }

    @Override
    public Article create(Article article) {
        article.setId(null);
        article.setCreated(LocalDateTime.now());
        article.setModified(LocalDateTime.now());
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return articleRepo.save(article);
    }

    @Override
    public Article update(Article article) {
        Article old = findById(article.getId());
        article.setCreated(old.getCreated());
        article.setModified(LocalDateTime.now());
        return articleRepo.save(article);
    }

    @Override
    public Article deleteById(Long articleId) {
        Article old = findById(articleId);
        articleRepo.deleteById(articleId);
        return old;
    }

    @Override
    public long count() {
        return articleRepo.count();
    }
}
