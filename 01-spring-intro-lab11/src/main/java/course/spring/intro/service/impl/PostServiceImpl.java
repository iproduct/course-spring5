package course.spring.intro.service.impl;

import course.spring.intro.dao.PostRepository;
import course.spring.intro.exception.EntityNotFoundException;
import course.spring.intro.model.ErrorResposnse;
import course.spring.intro.model.Post;
import course.spring.intro.service.PostService;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(String id) {
        Post found = postRepository.findById(id);
        if (found == null) {
            throw new EntityNotFoundException(String.format("Entity with ID: %s not found.", id));
        }
        return found;
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.create(post);
    }

    @Override
    public Post updatePost(Post post) {
        Post updated = postRepository.update(post);
        if (updated == null) {
            throw new EntityNotFoundException(String.format("Entity with ID: %s not found.", post.getId()));
        }
        return updated;
    }

    @Override
    public Post deletePostById(String id) {
        Post found = postRepository.deleteById(id);
        if (found == null) {
            throw new EntityNotFoundException(String.format("Entity with ID: %s not found.", id));
        }
        return found;
    }

    @Override
    public long getCount() {
        return postRepository.count();
    }

}