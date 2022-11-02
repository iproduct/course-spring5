package course.spring.core.service.impl;

import course.spring.core.dao.ArticleRepository;
import course.spring.core.dao.qualifiers.RepoProvider;
import course.spring.core.model.Article;
import course.spring.core.service.ArticleProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

//@Qualifier("repoProvider")
//@RepoProvider
//@Service
public class RepositoryArticleProvider implements ArticleProvider, InitializingBean {
    public static final List<Article> SAMPLE_REPO_ARTICLES = List.of(
            new Article("New in Spring Data", "WebFlux is here ...",
            "https://st.depositphotos.com/1642482/1904/i/950/depositphotos_19049237-stock-photo-leaf.jpg",
                    Set.of("spring", "novelties", "webflux"), "Trayan Iliev"),
            new Article( "Spring Data for All", "Hibrnate is easy ...",
        "https://st.depositphotos.com/1642482/1904/i/950/depositphotos_19049237-stock-photo-leaf.jpg",
                Set.of("spring data", "hibernate"), "Trayan Iliev"),
            new Article( "Reactive Spring Data", "Spring Data provides reactive DB clients ...",
        "https://st.depositphotos.com/1642482/1904/i/950/depositphotos_19049237-stock-photo-leaf.jpg",
                Set.of("spring", "reactor", "webflux"), "Trayan Iliev")
            );

    private ArticleRepository articleRepo;

    @Override
    public void afterPropertiesSet() throws Exception {
        SAMPLE_REPO_ARTICLES.forEach(articleRepo::create);
    }

//    @Autowired
    public void setArticleRepo(ArticleRepository articleRepo) {
        this.articleRepo = articleRepo;
    }

    @Override
    public List<Article> getArticles() {
        return articleRepo.findAll();
    }
}
