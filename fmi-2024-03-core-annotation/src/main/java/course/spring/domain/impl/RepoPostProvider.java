package course.spring.domain.impl;

import course.spring.dao.PostRepository;
import course.spring.domain.PostProvider;
import course.spring.model.Post;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Log
@Service
public class RepoPostProvider implements PostProvider {
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPosts() {
        return List.of(
                new Post(1L, "New in Spring 6", "API versions updated.", Set.of("spring 6", "novelty")),
                new Post(2L, "Hibernate API Intrinsics", "Hibernate provides many important customization options.",
                        Set.of("hibernate", "jpa", "spring data jpa")),
                new Post(3L, "Spring AI Integration", "Spring Boot provides OLLAMA starter.",
                        Set.of("spring", "spring boot", "ollama", "ai"))
        );
    }
}
