package course.spring.restmvc.service.impl;

import course.spring.restmvc.dao.PostRepository;
import course.spring.restmvc.entity.Post;
import course.spring.restmvc.exception.EntityNotFoundException;
import course.spring.restmvc.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepo;

    @Override
    public Collection<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Post with ID:%s not found.", id)));
    }

    @Override
    public Post addPost(Post post) {
        return null;
    }

    @Override
    public Post updatePost(Post post) {
        return null;
    }

    @Override
    public Post deletePost(Long id) {
        return null;
    }

    @Override
    public long getCount() {
        return 0;
    }
}
