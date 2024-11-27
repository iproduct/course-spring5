package course.spring.blog.service.impl;

import course.spring.blog.dao.PostRepository;
import course.spring.blog.entity.Post;
import course.spring.blog.exception.EntityNotFoundException;
import course.spring.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;

    @Autowired
    public PostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        return postRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Post with ID = '%d' not found", id)));
    }

    @Override
    public Post addPost(Post post) {
        return postRepo.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        var oldPost = getPostById(post.getId());
        return postRepo.save(post);
    }

    @Override
    public Post deletePostById(Long id) {
        var oldPost = getPostById(id);
        postRepo.deleteById(id);
        return oldPost;
    }

    @Override
    @Transactional(readOnly = true)
    public long getPostsCount() {
        return postRepo.count();
    }
}
