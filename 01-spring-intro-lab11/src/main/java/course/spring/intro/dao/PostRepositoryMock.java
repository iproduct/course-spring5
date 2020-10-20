package course.spring.intro.dao;

import course.spring.intro.model.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepositoryMock implements PostRepository {
    private AtomicLong nextId = new AtomicLong(0L);
    private Map<String, Post> posts = new ConcurrentHashMap<>();

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(posts.values());
    }

    @Override
    public Post findById(String id) {
        return posts.get(id);
    }

    @Override
    public Post create(Post post) {
        post.setId(String.format("%024d", nextId.incrementAndGet()));
        return posts.put(post.getId(), post);
    }

    @Override
    public Post update(Post post) {
        Post oldPost = posts.replace(post.getId(), post);
        if(oldPost == null) {
            return null;
        }
        return post;
    }

    @Override
    public Post deleteById(String id) {
        return posts.remove(id);
    }

    @Override
    public long count() {
        return posts.size();
    }
}
