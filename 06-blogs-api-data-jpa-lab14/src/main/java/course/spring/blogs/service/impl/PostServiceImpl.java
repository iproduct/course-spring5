package course.spring.blogs.service.impl;

import course.spring.blogs.dao.PostRepository;
import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.entity.Post;
import course.spring.blogs.exception.InvalidEntityDataException;
import course.spring.blogs.exception.NonexistingEntityException;
import course.spring.blogs.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new NonexistingEntityException(String.format("Post with ID=%d does not exist", id))
        );
    }

    @Override
    public Post addPost(Post post) {
        if (post.getAuthor() == null) {
            post.setAuthor(userRepository.findAll().get(0)); // set admin user as default author
        }
        post.setId(null);
        Post created = postRepository.save(post);
        post.getAuthor().getPosts().add(post);
        return created;
    }

    @Override
    public Post updatePost(Post post) {
        Post old = getPostById(post.getId());
        if (!post.getAuthor().equals(old.getAuthor())) {
            throw new InvalidEntityDataException(String.format("Post author can not be changed from ID=%d to ID=%d",
                    old.getId(), post.getId()));
        }
        Post updated = postRepository.save(post);
        return updated;
    }

    @Override
    public Post deletePostById(Long id) {
        Post deleted = getPostById(id);
        deleted.getAuthor().getPosts().remove(deleted);
        postRepository.deleteById(id);
        return deleted;
    }

    @Override
    public long getPostsCount() {
        return postRepository.count();
    }
}
