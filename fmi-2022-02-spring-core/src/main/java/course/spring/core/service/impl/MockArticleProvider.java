package course.spring.core.service.impl;

import course.spring.core.dao.qualifiers.MockProvider;
import course.spring.core.model.Article;
import course.spring.core.service.ArticleProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
//@Qualifier("mockProvider")
@MockProvider
@Service
public class MockArticleProvider implements ArticleProvider {
    public static final List<Article> SAMPLE_ARTICLES = List.of(
            new Article(1L, "New in Spring 5", "WebFlux is here ...",
            "https://st.depositphotos.com/1642482/1904/i/950/depositphotos_19049237-stock-photo-leaf.jpg",
                    Set.of("spring", "novelties", "webflux"), "Trayan Iliev"),
            new Article(2L, "Spring Data", "Hibrnate is easy ...",
        "https://st.depositphotos.com/1642482/1904/i/950/depositphotos_19049237-stock-photo-leaf.jpg",
                Set.of("spring data", "hibernate"), "Trayan Iliev"),
            new Article(3L, "Reactive Spring", "Project Reactor is an infrastructire for Spring ...",
        "https://st.depositphotos.com/1642482/1904/i/950/depositphotos_19049237-stock-photo-leaf.jpg",
                Set.of("spring", "reactor", "webflux"), "Trayan Iliev")
            );
    @Override
    public List<Article> getArticles() {
        return SAMPLE_ARTICLES;
    }
}
