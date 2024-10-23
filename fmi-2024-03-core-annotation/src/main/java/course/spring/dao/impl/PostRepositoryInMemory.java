package course.spring.dao.impl;

import course.spring.dao.KeyGenerator;
import course.spring.dao.PostRepository;
import course.spring.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class PostRepositoryInMemory extends InMemoryRepository<Post, Long> implements PostRepository {
    @Autowired
    public PostRepositoryInMemory(KeyGenerator<Long> generator) {
        super(generator);
    }

    @Override
    public List<Post> findByTags(Set<String> tags) {
        return null;
    }
}
