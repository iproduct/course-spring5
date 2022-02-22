package course.ws.service.impl;

import course.ws.dao.ArticleRepository;
import course.ws.model.Article;
import course.ws.qualifiers.Default;
import course.ws.service.ArticleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ArticleProviderImpl implements ArticleProvider {
    public static final List<Article> SAMPLE_ARTICLES = List.of(
            new Article("Spring Data JPA Intro", "Spring Data JPA is easy ...",
                    "Trayan Iliev", Set.of("spring", "mvc", "boot", "intro")),
            new Article("Spring Data JPA and Hibernate", "Hibernate provides powerful ORM implementation ...",
                    "Georgi Petrov", Set.of("hibernate", "performance")),
            new Article( "Spring Data is Going Reactive", "Spring Data provides reactive db integrations for a number of databases ...",
                    "Trayan Iliev", Set.of("spring", "boot", "intro"))
    );

    private ArticleRepository articleRepo;

    public ArticleProviderImpl(ArticleRepository articleRepo) {
        this.articleRepo = articleRepo;
        SAMPLE_ARTICLES.forEach(articleRepo::create);
    }

//    @Autowired
//    public void setArticleRepo(ArticleRepository articleRepo) {
//        this.articleRepo = articleRepo;
//    }

    @Override
    public List<Article> getArticles() {
        return new ArrayList<>(articleRepo.findAll());
    }
}
