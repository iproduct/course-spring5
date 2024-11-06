package course.spring.presenter.impl;

import course.spring.domain.PostProvider;
import course.spring.model.Post;
import course.spring.presenter.PostPresenter;
import course.spring.qualifiers.RepoProvider;
import course.spring.qualifiers.SimpleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component("presenter")
public class ConsolePostPresenter implements PostPresenter {
//    @Resource(name = "repoProvider")
    @Value("#{@simpleProvider}")
    private PostProvider postProvider;

    @Value("${presenter.title}")
    private String title;

//    @Autowired
//    public ConsolePostPresenter(PostProvider[] postProvider) {
//        this.postProvider = postProvider;
//    }

    public void setTitle(String title) {
        this.title = title;
    }


//    public PostProvider getPostProvider() {
//        return postProvider;
//    }

//    public void setPostProvider(PostProvider postProvider) {
//        this.postProvider = postProvider;
//    }

    @Override
    public void present() {
        System.out.printf("Title: %s\n", title);
//        var allPosts = Arrays.stream(postProviders).flatMap(p -> p.getAllPosts().stream()).toList();
        var allPosts = postProvider.getAllPosts();
        allPosts.forEach(System.out::println);
    }
}
