package course.spring.service;

import course.spring.dao.ArticleRepositoryJpa;
import course.spring.dao.UserRepositoryJpa;
import course.spring.exception.NonexisitngEntityException;
import course.spring.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ArticlesServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepositoryJpa articleRepository;
    @Autowired
    private UserRepositoryJpa userRepository;

    @Override
    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Article getArticleById(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new NonexisitngEntityException(
                String.format("Article with ID='%s' does not exist.", articleId)));
    }

    @Override
    public Article createArticle(Article article) {
        var defaultUser = userRepository.findByUsername("admin");
        article.setAuthor(defaultUser.orElseThrow());
        return articleRepository.save(article);
    }

    @Override
//    @PreAuthorize("(#article.authorId == authentication.principal.id) or hasRole('ADMIN')")
    public Article updateArticle(Article article) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
        Article old = getArticleById(article.getId());
//        if(user == null ||
//                (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) && !old.getAuthorId().equals(user.getId())) ) {
//            throw new UnauthorisedModificationException("You have no permissions to edit article: " + old.getTitle());
//        }
        article.setCreated(old.getCreated());
        article.setModified(LocalDateTime.now());
        return articleRepository.save(article);
    }

    @Override
    public Article removeArticle(Long articleId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
        Article old = getArticleById(articleId);
//        if(user == null ||
//                (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) && !old.getAuthorId().equals(user.getId())) ) {
//            throw new UnauthorisedModificationException("You have no permissions to delete article: " + old.getTitle());
//        }
        articleRepository.deleteById(articleId);
        return old;
    }

    @Override
    public long getArticleCount() {
        return articleRepository.count();
    }
}
