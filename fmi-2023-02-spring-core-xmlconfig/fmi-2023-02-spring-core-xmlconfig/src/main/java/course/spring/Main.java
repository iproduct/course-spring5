package course.spring;

import course.spring.dao.ArticleRepository;
import course.spring.model.Article;
import course.spring.presenter.ArticlePresenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");
        // Lookup presenter using Service Locator pattern
        ArticleRepository repo = ctx.getBean(ArticleRepository.class);
        List<Article> repoArticles = List.of(
                new Article("Article 1", "Article 1 content ...", "Trayan Iliev", Set.of("article 1")),
                new Article("Article 2", "Article 2 content ...", "Trayan Iliev", Set.of("article 2")),
                new Article("Article 3", "Article 3 content ...", "Trayan Iliev", Set.of("article 3"))
        );
        repoArticles.forEach(repo::create);

        ArticlePresenter presenter = ctx.getBean(ArticlePresenter.class);
        presenter.present();
    }
}
