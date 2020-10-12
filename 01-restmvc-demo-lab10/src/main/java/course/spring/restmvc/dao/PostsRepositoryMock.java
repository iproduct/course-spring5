package course.spring.restmvc.dao;

import course.spring.restmvc.model.Post;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostsRepositoryMock implements PostsRepository{
    private Map<Long, Post> posts = new ConcurrentHashMap<>();

    public PostsRepositoryMock() {
        Arrays.asList(new Post[]{
                new Post("New in Spring 5", "WebFlux is here ...", "Trayan Iliev"),
                new Post("DI in Spring", "Many ways to do it ...", "Trayan Iliev"),
                new Post("Autowiring", "To autowire or not to autowire ...", "Trayan Iliev")
        }).forEach(post -> create(post));
    }

    @Override
    public List<Post> findAll() {
        return null;
    }

    @Override
    public Post findById(Long id) {
        return null;
    }

    @Override
    public Post create(Post post) {
        return null;
    }

    @Override
    public Post update(Post post) {
        return null;
    }

    @Override
    public Post deleteById(Long id) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
