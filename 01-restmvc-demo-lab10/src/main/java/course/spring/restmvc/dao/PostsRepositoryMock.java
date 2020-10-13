package course.spring.restmvc.dao;

import course.spring.restmvc.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostsRepositoryMock implements PostsRepository{
    private AtomicLong nextId = new AtomicLong(0L);
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
        return new ArrayList<>(posts.values());
    }

    @Override
    public Post findById(Long id) {
        return posts.get(id);
    }

    @Override
    public Post create(Post post) {
        post.setId(nextId.incrementAndGet());
        posts.put(post.getId(), post);
        return post;
    }

    @Override
    public Post update(Post post) {
        Post old = posts.replace(post.getId(), post);
        return old != null ? post: null;
    }

    @Override
    public Post deleteById(Long id) {
        return posts.remove(id);
    }

    @Override
    public long count() {
        return posts.size();
    }
}
