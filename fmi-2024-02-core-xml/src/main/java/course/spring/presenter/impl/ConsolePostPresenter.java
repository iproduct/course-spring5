package course.spring.presenter.impl;

import course.spring.model.Post;
import course.spring.presenter.PostPresenter;

import java.util.List;

public class ConsolePostPresenter implements PostPresenter {
    private List<Post> posts;

    public ConsolePostPresenter(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public void present() {
        posts.forEach(System.out::println);
    }
}
