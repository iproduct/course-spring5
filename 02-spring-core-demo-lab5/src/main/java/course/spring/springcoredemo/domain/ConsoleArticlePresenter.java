package course.spring.springcoredemo.domain;

import course.spring.springcoredemo.dao.ArticlesRepository;
import course.spring.springcoredemo.qualifiers.AlternativeProvider;
import course.spring.springcoredemo.qualifiers.MockProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.Set;

//@Component("presenter")
//@DependsOn({"provider"})
//@Lazy
//@Service("presenter")
public class ConsoleArticlePresenter implements ArticlePresenter{
//    @Autowired
//    private Set<ArticlesRepository> providers;
    private ArticlesRepository provider;
    private String title;

//    public ConsoleArticlePresenter(ArticlesRepository provider) {
//        this.provider = provider;
//    }

    public ArticlesRepository getProvider() {
        return provider;
    }

//    @Resource(name="articlesRepositoryImpl")
//    @Inject
//    @MockProvider
//    @Autowired
//    @Qualifier("alternativeRepo")
    public void setProvider(ArticlesRepository provider) {
        this.provider = provider;
    }

    public String getTitle() {
        return title;
    }

    @Autowired
    public void setPresenterData(ArticlesRepository provider, @Qualifier("title") String title){
        this.provider = provider;
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void present() {
//        providers.stream().flatMap(p -> p.findAll().stream()).forEach(System.out::println);
        provider.findAll().stream().forEach(System.out::println);
    }
}
