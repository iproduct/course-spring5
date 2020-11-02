package course.spring.restmvc.service.impl;

import course.spring.restmvc.dao.PostRepository;
import course.spring.restmvc.entity.Post;
import course.spring.restmvc.exception.EntityNotFoundException;
import course.spring.restmvc.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepo;

    @Override
    public Collection<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Post getPostById(Long id) {
        return postRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Post with ID:%s not found.", id)));
    }

    @Override
    public Post addPost(Post post) {
        post.setId(null);
        post.setCreated(LocalDateTime.now());
        post.setModified(LocalDateTime.now());
        return postRepo.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        Post found = getPostById(post.getId());
        post.setModified(LocalDateTime.now());
        return postRepo.save(post);
    }

    @Override
    public Post deletePost(Long id) {
        Post deleted = postRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Post with ID:%s not found.", id)));
        postRepo.deleteById(id);
        return deleted;
    }

    @Override
    public long getCount() {
        return 0;
    }
}
