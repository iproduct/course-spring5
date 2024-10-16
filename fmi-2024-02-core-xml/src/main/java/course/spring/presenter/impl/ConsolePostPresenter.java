package course.spring.presenter.impl;

import course.spring.domain.PostProvider;
import course.spring.model.Post;
import course.spring.presenter.PostPresenter;

import java.util.List;

public class ConsolePostPresenter implements PostPresenter {
    private PostProvider postProvider;

//    public ConsolePostPresenter(PostProvider postProvider) {
//        this.postProvider = postProvider;
//    }

//    public PostProvider getPostProvider() {
//        return postProvider;
//    }

    public void setPostProvider(PostProvider postProvider) {
        this.postProvider = postProvider;
    }

    @Override
    public void present() {
        postProvider.getAllPosts().forEach(System.out::println);
    }
}
