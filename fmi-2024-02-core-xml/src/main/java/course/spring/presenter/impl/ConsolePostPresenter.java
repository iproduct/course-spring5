package course.spring.presenter.impl;

import course.spring.domain.PostProvider;
import course.spring.model.Post;
import course.spring.presenter.PostPresenter;

import java.util.List;

public class ConsolePostPresenter implements PostPresenter {
    private PostProvider postProvider;
    private String title;

    public ConsolePostPresenter(PostProvider postProvider) {
        this.postProvider = postProvider;
    }

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
        postProvider.getAllPosts().forEach(System.out::println);
    }
}
