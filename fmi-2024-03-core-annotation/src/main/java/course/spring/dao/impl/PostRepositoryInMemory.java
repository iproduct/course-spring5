package course.spring.dao.impl;

import course.spring.dao.KeyGenerator;
import course.spring.dao.PostRepository;
import course.spring.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public class PostRepositoryInMemory extends InMemoryRepository<Post, Long> implements PostRepository {
    @Autowired
    public PostRepositoryInMemory(KeyGenerator<Long> generator) {
        super(generator);
    }

    @Override
    public List<Post> findByTags(Collection<String> tags) {
        var tagsSet = Set.copyOf(tags);
        return findAll().stream().filter(p -> {
            tagsSet.retainAll(p.getTags());
            return !tagsSet.isEmpty();
        }).toList();
    }
}
