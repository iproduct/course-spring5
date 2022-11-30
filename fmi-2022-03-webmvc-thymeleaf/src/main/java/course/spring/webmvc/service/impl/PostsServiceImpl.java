package course.spring.webmvc.service.impl;

import course.spring.webmvc.dao.PostRepository;
import course.spring.webmvc.entity.Post;
import course.spring.webmvc.exception.NotFoundException;
import course.spring.webmvc.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostsServiceImpl implements PostsService {
    private PostRepository postRepo;

    @Autowired
    public PostsServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepo.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Post with ID='%d' not found", id)
        ));
    }

    @Override
    public Post add(Post post) {
        var now = LocalDateTime.now();
        post.setCreated(now);
        post.setModified(now);
        return postRepo.save(post);
    }

    @Override
    public Post update(Post post) {
        var old = getPostById(post.getId());
        post.setCreated(old.getCreated());
        post.setModified(LocalDateTime.now());
        return postRepo.save(post);
    }

    @Override
    public Post deleteById(Long id) {
        var deleted = getPostById(id);
        postRepo.deleteById(id);
        return deleted;
    }

}
