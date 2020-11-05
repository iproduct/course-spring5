package course.spring.restmvc;

import course.spring.restmvc.dao.PostRepository;
import course.spring.restmvc.entity.Category;
import course.spring.restmvc.entity.Post;
import course.spring.restmvc.entity.Role;
import course.spring.restmvc.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static course.spring.restmvc.entity.Role.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class
)
@AutoConfigureMockMvc
public class RestmvcBlogsApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @Test
    void contextLoads() {
    }

    private static List<User> users = List.of(
            new User("Default", "Admin", "admin@mycompany.com", "admin", "admin",
                    Set.of(ADMIN)),
            new User("Default", "Author", "author@mycompany.com", "author", "author",
                    Set.of(AUTHOR, READER))
    );

      private static final List<Category> categories = List.of(
            new Category("Java EE", "Java Enterprise Edition"),
            new Category("Spring", "Java Spring Platofrm"),
            new Category("Reactive Programming", "High-performance functional declarative programming")
    );
    private static final Post newPost =
            new Post(1L, "New Title", "New content ...", users.get(1),
                    Set.of("spring", "di"), Set.of(categories.get(1)));

    private static final List<Post> mockPosts = List.of(
            new Post("New in Spring", "Spring 5 is here ...", users.get(0),
                    Set.of("spring", "java", "webflux"), Set.of(categories.get(1), categories.get(2))),
            new Post("DI in Spring", "Many ways to do it ...", users.get(1),
                    Set.of("spring", "di"), Set.of(categories.get(1))),
            new Post("Reactive Web Programmiing", "Webflux uses Reactor ...", users.get(1),
                    Set.of("spring", "reactor", "webflux"), Set.of(categories.get(1), categories.get(2))),
            new Post("JPA Made Easy", "Spring Data DSL automates JPQL creation ...", users.get(1),
                    Set.of("spring", "spring data", "jpql"), Set.of(categories.get(0), categories.get(1)))
    );
}
