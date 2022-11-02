package course.spring.core.service.impl;

import course.spring.core.dao.ArticleRepository;
import course.spring.core.dao.UserRepository;
import course.spring.core.dao.qualifiers.RepoProvider;
import course.spring.core.model.Article;
import course.spring.core.model.Role;
import course.spring.core.model.User;
import course.spring.core.service.ArticleProvider;
import course.spring.core.service.UserProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

//@Qualifier("repoProvider")
@RepoProvider
@Service
public class RepositoryUserProvider implements UserProvider {
    public static final List<User> SAMPLE_REPO_ARTICLES = List.of(
            new User("Default", "Admin", "admin", "admin", Role.ADMIN),
            new User("Default", "Author", "author", "author", Role.AUTHOR),
            new User("Default", "Reader", "reader", "reader", Role.READER)
            );

    private UserRepository userRepo;

    @PostConstruct
    public void init() throws Exception {
        SAMPLE_REPO_ARTICLES.forEach(userRepo::create);
    }

    @Autowired
    public void setArticleRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }
}
