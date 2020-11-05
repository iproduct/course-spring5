package course.spring.restmvc;

import course.spring.restmvc.dao.PostRepository;
import course.spring.restmvc.entity.Category;
import course.spring.restmvc.entity.Post;
import course.spring.restmvc.entity.Role;
import course.spring.restmvc.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static course.spring.restmvc.entity.Role.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class
)
@AutoConfigureMockMvc
@Slf4j
public class RestmvcBlogsApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void givePosts_whenGetPosts_thenStatus200andJsonArray() throws Exception {
        given(postRepository.findAll()).willReturn(mockPosts);
        mockMvc.perform(get("/api/posts")
                    .with(user("admin").password("admin123").roles("ADMIN"))
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> log.info(result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.length()").value(4))
//                .andExpect(jsonPath("$.length()").value(greaterThan(2)))
                .andExpect(jsonPath("$[0].title").value("New in Spring"))
                .andExpect(jsonPath("$[1].title").value("DI in Spring"))
                .andExpect(jsonPath("$[2].title").value("Reactive Web Programming"))
                .andExpect(jsonPath("$[3].title").value("JPA Made Easy"));

        then(postRepository).should(times(1)).findAll();
    }






    private static List<User> users = List.of(
            new User("Default", "Admin", "admin@mycompany.com", "admin", "admin",
                    Set.of(ADMIN)),
            new User("Default", "Author", "author@mycompany.com", "author", "author",
                    Set.of(AUTHOR, READER))
    );

      private static final List<Category> categories = List.of(
            new Category(1L,"Java EE", "Java Enterprise Edition"),
            new Category(2L,"Spring", "Java Spring Platofrm"),
            new Category(3L,"Reactive Programming", "High-performance functional declarative programming")
    );
    private static final Post newPost =
            new Post(1L, "New Title", "New content ...", users.get(1),
                    Set.of("spring", "di"), Set.of(categories.get(1)));

    private static final List<Post> mockPosts = List.of(
            new Post("New in Spring", "Spring 5 is here ...", users.get(0),
                    Set.of("spring", "java", "webflux"), Set.of(categories.get(1), categories.get(2))),
            new Post("DI in Spring", "Many ways to do it ...", users.get(1),
                    Set.of("spring", "di"), Set.of(categories.get(1))),
            new Post("Reactive Web Programming", "Webflux uses Reactor ...", users.get(1),
                    Set.of("spring", "reactor", "webflux"), Set.of(categories.get(1), categories.get(2))),
            new Post("JPA Made Easy", "Spring Data DSL automates JPQL creation ...", users.get(1),
                    Set.of("spring", "spring data", "jpql"), Set.of(categories.get(0), categories.get(1)))
    );
}
