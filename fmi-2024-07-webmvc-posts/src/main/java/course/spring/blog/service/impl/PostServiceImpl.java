package course.spring.blog.service.impl;

import course.spring.blog.dao.PostRepository;
import course.spring.blog.dto.PostCreateDto;
import course.spring.blog.entity.Post;
import course.spring.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;

    @Autowired
    public PostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return null;
    }

    @Override
    public Post addPost(PostCreateDto post) {
        return null;
    }

    @Override
    public Post updatePost(PostCreateDto post) {
        return null;
    }

    @Override
    public Post deletePostById(Long id) {
        return null;
    }

    @Override
    public long getPostsCount() {
        return 0;
    }
}
