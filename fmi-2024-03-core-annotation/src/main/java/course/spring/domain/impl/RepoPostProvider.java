package course.spring.domain.impl;

import course.spring.dao.PostRepository;
import course.spring.domain.PostProvider;
import course.spring.model.Post;
import course.spring.qualifiers.RepoProvider;
import lombok.extern.java.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Log
@Service("repoProvider")
//@Qualifier("repoProv")
@RepoProvider
@Order(2)
public class RepoPostProvider implements PostProvider { //InitializingBean {
    private PostRepository postRepository;

    @PostConstruct
    public void init() throws Exception {
        postRepository.create(new Post("New Post 1", "New Post 1 content", Set.of("new", "post", "one")));
        postRepository.create(new Post("New Post 2", "New Post 2 content", Set.of("new", "post", "two")));
        postRepository.create(new Post("New Post 3", "New Post 3 content", Set.of("new", "post", "three")));
    }
    @Autowired
    public RepoPostProvider(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getByTags(Collection<String> tags) {
        return postRepository.findByTags(tags);
    }

    @Override
    public Post addPost(Post post) {
        return postRepository.create(post);
    }
}
