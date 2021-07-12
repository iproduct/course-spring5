package course.spring.blogs.service.impl;

import course.spring.blogs.dao.PostRepository;
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

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post addPost(Post post) {
        return postRepository.create(post);
    }
}
