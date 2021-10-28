package course.spring.service.impl;

import course.spring.dao.ArticleRepository;
import course.spring.model.Article;
import course.spring.service.ArticleProvider;
import org.springframework.beans.factory.InitializingBean;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MockArticleProvider implements ArticleProvider {
//    public static final List<Article> MOCK_ARTICLES =List.of(
//        new Article("New in Spring", "Web Flux is here ..."),
//        new Article("DI in Action", "Should I use DI or Service Locator? ..."),
//        new Article("Bean Scopes", "Singleton, Prototype, ...")
//    );

    private ArticleRepository articleRepo;

    public MockArticleProvider(ArticleRepository articleRepo) {
        this.articleRepo = articleRepo;
    }

    public ArticleRepository getArticleRepo() {
        return articleRepo;
    }

    public void setArticleRepo(ArticleRepository articleRepo) {
        this.articleRepo = articleRepo;
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        MOCK_ARTICLES.forEach(articleRepo::create);
//    }

//    public void setSampleArticles(Map<String, String> sampleTitlesToContent) {
//        sampleTitlesToContent.forEach((title, content) -> articleRepo.create(new Article(title, content)));
//    }

    public void setSampleTitles(String[] sampleTitles) {
        Arrays.stream(sampleTitles).forEach(title-> articleRepo.create(new Article(title, title + " content ...")));
    }

    @Override
    public Collection<Article> getArticles() {
        return articleRepo.findAll();
    }
}
