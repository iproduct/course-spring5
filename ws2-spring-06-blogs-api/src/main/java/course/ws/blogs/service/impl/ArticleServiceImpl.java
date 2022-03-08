package course.ws.blogs.service.impl;

import course.ws.blogs.dao.ArticleRepository;
import course.ws.blogs.dao.UserRepository;
import course.ws.blogs.entity.Article;
import course.ws.blogs.entity.User;
import course.ws.blogs.exception.EntityNotFoundException;
import course.ws.blogs.exception.InsufficientPrivilegiesException;
import course.ws.blogs.exception.InvalidEntityDataException;
import course.ws.blogs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import java.util.List;

import static course.ws.blogs.entity.Role.ADMIN;
import static course.ws.blogs.entity.Role.AUTHOR;
import static course.ws.blogs.util.UserUtils.getUser;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepo;
    private UserRepository userRepo;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepo, UserRepository userRepo) {
        this.articleRepo = articleRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> findAllArticles() {
        return articleRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Article findArticleById(Long id) {
        return articleRepo.findById(id).orElseThrow(()->new EntityNotFoundException(
                String.format("Article with ID='%d' not found.", id)
        ));
    }

    @Override
    public Article create(Article article) {
        if(article.getId() != null) {
            throw new InvalidEntityDataException("New Article ID should be NULL.");
        }
        var now = LocalDateTime.now();
        article.setCreated(now);
        article.setModified(now);
        article = articleRepo.save(article);
        User author = userRepo.findById(article.getAuthor().getId()).orElseThrow(()->
                new EntityNotFoundException("Author not found."));
        var articles = author.getArticles();
        articles.add(article); // Don't forget to set the second end of the relation (if needed)!
        return article;
    }

    @Override
//    @PreAuthorize("authentication.principal.id == #article.author.id or hasRole('ADMIN')")
    @RolesAllowed({"AUTHOR", "ADMIN"})
    public Article update(Article article) {
        Article old = findArticleById(article.getId());
        User authenticated = getUser(SecurityContextHolder.getContext().getAuthentication());
        if(!old.getAuthor().getId().equals(authenticated.getId()) && !authenticated.getRole().equals(ADMIN)) {
            throw new InsufficientPrivilegiesException(
                    "You have are not allowed to edit atricle '"+ article.getTitle() +"'");
        }
        article.setAuthor(old.getAuthor());
        article.setCreated(old.getCreated());
        article.setModified(LocalDateTime.now());
        return articleRepo.save(article);
    }

    @Override
    public Article deleteArticleById(Long id) {
        Article deleted = findArticleById(id);
        articleRepo.deleteById(id);
        return deleted;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return articleRepo.count();
    }
}
