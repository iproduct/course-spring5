package course.spring.webmvc.domain;

import course.spring.restmvc.exception.NonexisitngEntityException;
import course.spring.webmvc.dao.ArticlesRepository;
import course.spring.webmvc.dao.UsersRepository;
import course.spring.webmvc.exception.UnauthorisedModificationException;
import course.spring.webmvc.model.Article;
import course.spring.webmvc.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ArticlesServiceImpl implements ArticlesService {
    @Autowired
    private ArticlesRepository articlesRepository;
    private UsersRepository usersRepository;

    @Override
    public List<Article> findAll() {
        return articlesRepository.findAll();
    }

    @Override
    public Article findById(String articleId) {
        return articlesRepository.findById(articleId).orElseThrow(() -> new NonexisitngEntityException(
                String.format("Article with ID='%s' does not exist.", articleId)));
    }

    @Override
    public Article add(Article article) {
        return articlesRepository.insert(article);
    }

    @Override
    @PreAuthorize("(#article.authorId == authentication.principal.id) or hasRole('ADMIN')")
    public Article update(Article article) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Article old = findById(article.getId());
        if(user == null ||
                (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) && !old.getAuthorId().equals(user.getId())) ) {
            throw new UnauthorisedModificationException("You have no permissions to edit article: " + old.getTitle());
        }
        article.setCreated(old.getCreated());
        article.setModified(LocalDateTime.now());
        return articlesRepository.save(article);
    }

    @Override
    public Article remove(String articleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Article old = findById(articleId);
        if(user == null ||
                (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) && !old.getAuthorId().equals(user.getId())) ) {
            throw new UnauthorisedModificationException("You have no permissions to delete article: " + old.getTitle());
        }
        articlesRepository.deleteById(articleId);
        return old;
    }

    @Override
    public long count() {
        return articlesRepository.count();
    }
}
