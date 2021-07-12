package course.spring.blogs.service.impl;

import course.spring.blogs.dao.PostRepository;
import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.entity.Post;
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
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post addPost(Post post) {
        if(post.getAuthor() == null) {
            post.setAuthor(userRepository.findAll().get(0)); // set admin user as default author
        }
        Post created = postRepository.create(post);
        post.getAuthor().getPosts().add(post);
        return created;
    }

    @Override
    public long getCount() {
        return postRepository.count();
    }
}
