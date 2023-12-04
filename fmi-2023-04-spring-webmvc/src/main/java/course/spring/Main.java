package course.spring;

import course.spring.dao.ArticleRepository;
import course.spring.dao.UserRepository;
import course.spring.model.ArticleCreateDTO;
import course.spring.model.User;
import course.spring.presenter.ArticlePresenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Set;

import static course.spring.model.Role.*;

public class Main {
    static final List<ArticleCreateDTO> repoArticles = java.util.List.of(
            new ArticleCreateDTO("Article 1", "Article 1 content ...", "", "trayan", Set.of("article 1")),
            new ArticleCreateDTO("Article 2", "Article 2 content ...", "", "john", Set.of("article 2")),
            new ArticleCreateDTO("Article 3", "Article 3 content ...", "", "jane", Set.of("article 3"))
    );
    static final List<User> repoUsers = List.of(
            new User("Trayan", "Iliev", "trayan", "trayan123", Set.of(READER, AUTHOR, ADMIN)),
            new User("John", "Doe", "john", "john123", Set.of(READER)),
            new User("Jane", "Doe", "jane", "jane123", Set.of(READER, AUTHOR))
    );
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("course.spring");
        // Lookup presenter using Service Locator pattern
        UserRepository userRepo = ctx.getBean(UserRepository.class);
        repoUsers.forEach(userRepo::create);
        System.out.println(userRepo.findAll());
        ArticleRepository repo = ctx.getBean(ArticleRepository.class);
        repoArticles.forEach(repo::create);

        ArticlePresenter presenter = ctx.getBean(ArticlePresenter.class);
        presenter.present();
    }
}
