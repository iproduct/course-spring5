package course.spring.domain.impl;

import course.spring.domain.PostProvider;
import course.spring.model.Post;
import lombok.extern.java.Log;

import java.util.List;
import java.util.Set;

@Log
public class SimplePostProvider implements PostProvider {

    public static PostProvider createProvider(){
        log.info("Provider created by factory method.");
        return new SimplePostProvider();
    }
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