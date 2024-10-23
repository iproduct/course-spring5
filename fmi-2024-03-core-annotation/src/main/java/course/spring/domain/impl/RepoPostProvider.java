package course.spring.domain.impl;

import course.spring.dao.PostRepository;
import course.spring.domain.PostProvider;
import course.spring.model.Post;
import course.spring.qualifiers.RepoProvider;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Log
@Service("repoProvider")
//@Qualifier("repoProv")
@RepoProvider
public class RepoPostProvider implements PostProvider {
    private PostRepository postRepository;

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
