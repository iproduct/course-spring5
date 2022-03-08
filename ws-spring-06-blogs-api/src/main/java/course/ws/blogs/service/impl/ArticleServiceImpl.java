package course.ws.blogs.service.impl;

import course.ws.blogs.dao.ArticleRepository;
import course.ws.blogs.dao.UserRepository;
import course.ws.blogs.entity.Article;
import course.ws.blogs.entity.Role;
import course.ws.blogs.entity.User;
import course.ws.blogs.exception.EntityNotFoundException;
import course.ws.blogs.exception.ForbiddenException;
import course.ws.blogs.exception.InvalidEntityDataException;
import course.ws.blogs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static course.ws.blogs.entity.Role.ADMIN;
import static course.ws.blogs.util.UserUtil.getUser;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;
    private UserRepository userRepo;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepo) {
        this.articleRepository = articleRepository;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Article getById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Article with ID='%s' not found", id)));
    }

    @Override
    public Article create(Article article) {
        if(article.getId() != null) {
            throw new InvalidEntityDataException(
                    String.format("Article %d: '%s' should not have ID during creation.",
                            article.getId(), article.getTitle()));
        }
        LocalDateTime now = LocalDateTime.now();
        article.setCreated(now);
        article.setModified(now);
        article = articleRepository.save(article);
//        User author = userRepo.findById(article.getAuthor().getId()).orElseThrow(() ->
//                new EntityNotFoundException("Author can not be found."));
        User author = userRepo.save(article.getAuthor());
        author.getArticles().add(article);
        return article;
    }

    @Override
    public Article update(Article article) {
        Article old = getById(article.getId());
        User user = getUser(SecurityContextHolder.getContext().getAuthentication());
        // method level programmatic security
        if(!old.getAuthor().equals(user) && !user.getRole().equals(ADMIN)){
            throw new ForbiddenException(
                "To update the article '"+ article.getTitle() +"' you should be author or Administrator");
        }
        article.setAuthor(old.getAuthor());
        article.setCreated(old.getCreated());
        article.setModified(LocalDateTime.now());
        return articleRepository.save(article);
    }

    @Override
    public Article deleteById(Long id) {
        Article old = getById(id);
        articleRepository.deleteById(id);
        return old;
    }

    @Override
    @Transactional(readOnly = true)
    public long getCount() {
        return articleRepository.count();
    }
}
