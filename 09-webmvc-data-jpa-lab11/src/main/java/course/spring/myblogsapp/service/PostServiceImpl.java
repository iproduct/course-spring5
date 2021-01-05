package course.spring.myblogsapp.service;

import course.spring.myblogsapp.dao.PostRepository;
import course.spring.myblogsapp.entity.Post;
import course.spring.myblogsapp.exception.NonExisitingEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


import javax.validation.Valid;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Validated
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepo;

    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepo.findById(id).orElseThrow(
                () -> new NonExisitingEntityException(
                        String.format("Post with ID='%d' does not exists", id)));
    }

    @Override
    @Transactional
    public Post addPost(@Valid Post post) {
        post.setId(null);
        post.getAuthor().getPosts().add(post);
        return postRepo.save(post);
    }

    @Override
    @Transactional
    public Post updatePost(@Valid Post post) {
        Post old = getPostById(post.getId());
        return postRepo.save(post);
    }

    @Override
    @Transactional
    public Post deletePost(Long id) {
        Post deleted = getPostById(id);
        postRepo.deleteById(id);
        return deleted;
    }

    @Override
    public long getPostsCount() {
        return postRepo.count();
    }
}
