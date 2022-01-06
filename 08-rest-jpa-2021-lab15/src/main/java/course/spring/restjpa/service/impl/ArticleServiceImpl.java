package course.spring.restjpa.service.impl;


import course.spring.restjpa.dto.ArticleRepository;
import course.spring.restjpa.dto.UserRepository;
import course.spring.restjpa.exception.EntityNotFoundException;
import course.spring.restjpa.exception.UnauthorisedException;
import course.spring.restjpa.model.Article;
import course.spring.restjpa.model.User;
import course.spring.restjpa.service.ArticleService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    private static final String DEFAULT_AUTHOR_USERNAME = "author";
    private ArticleRepository articleRepo;
    private UserRepository userRepo;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepo, UserRepository userRepository) {
        this.articleRepo = articleRepo;
        this.userRepo = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> findAll() {
        List<Article> articles = articleRepo.findAll();
        return articles;
    }

    @Override
    @Transactional(readOnly = true)
    public Article findById(Long articleId) {
        Article article = articleRepo.findById(articleId).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Article with ID=%s not found.", articleId)));
        return article;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> findByTitle(String articlename) {
        return articleRepo.findByTitleContainingIgnoreCase(articlename);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> findByTags(List<String> filterTags) {
        return articleRepo.findByTagsInIgnoreCase(filterTags);
    }

    @Override
    public Article create(Article article) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            user = userRepo.findByUsername(DEFAULT_AUTHOR_USERNAME).orElseThrow(
                    () ->new UnauthorisedException("The user should be logged in to create blogs"));
        } else {
            user = userRepo.findByUsername(auth.getName()).orElseThrow(
                    () ->new UnauthorisedException("The user should be logged in to create blogs"));
        }
        if (user != null) {
            article.setAuthor(user);
            article.setId(null);
            article.setCreated(LocalDateTime.now());
            article.setModified(LocalDateTime.now());
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            return articleRepo.save(article);
        } else {
            throw new UnauthorisedException("The user should logged in to create blogs");
        }

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
    @Transactional(readOnly = true)
    public long count() {
        return articleRepo.count();
    }
}
