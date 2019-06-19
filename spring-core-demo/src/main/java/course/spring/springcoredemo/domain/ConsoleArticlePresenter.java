package course.spring.springcoredemo.domain;

import course.spring.springcoredemo.dao.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

//@Component("presenter")
//@DependsOn({"provider"})
//@Lazy
public class ConsoleArticlePresenter implements ArticlePresenter{
    private ArticlesRepository provider;
    private String title;

//    public ConsoleArticlePresenter(ArticlesRepository provider) {
//        this.provider = provider;
//    }

    public ArticlesRepository getProvider() {
        return provider;
    }

    public void setProvider(ArticlesRepository provider) {
        this.provider = provider;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void present() {
        provider.findAll().stream().forEach(System.out::println);
    }
}
