package course.spring.blogs;

import com.fasterxml.jackson.databind.ObjectMapper;
import course.spring.blogs.dao.PostRepository;
import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.entity.Post;
import course.spring.blogs.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static course.spring.blogs.entity.Role.ADMIN;
import static course.spring.blogs.entity.Role.AUTHOR;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class
)
@AutoConfigureMockMvc
@Slf4j
public class RestmvcBlogsApplicationTests {
//    @LocalServerPort
//    int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private PostRepository postRepository;
    @MockBean
    private UserRepository userRepository;

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

    @Test
    void givePosts_whenPostPost_thenStatus201andLocationHeader() throws Exception {
        given(postRepository.save(any(Post.class))).willReturn(newPost);
        given(userRepository.findByUsername(any(String.class))).willReturn(Optional.of(users.get(0)));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
                    .with(user("admin").password("admin123").roles("ADMIN"))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(mapper.writeValueAsString(modelMapper.map(newPost, PostDto.class))))
                    .content(mapper.writeValueAsString(Post.class)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("location",
                        containsString("http://localhost/api/posts/" + newPost.getId())))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));

//        then(postRepository).should(times(1)).findAll();
    }


    private static List<User> users = List.of(
            new User("Default", "Admin", "admin", "admin123", "admin123@abv.bg",
                    ADMIN),
            new User("Default", "Author", "author", "author123", "author@abv.bg",
                   AUTHOR)
    );


    private static final Post newPost =
            new Post(1L, "New Title", "New content ...", users.get(1));

    private static final List<Post> mockPosts = List.of(
            new Post(1L, "New in Spring", "Spring 5 is here ...", users.get(0)),
            new Post(2L, "DI in Spring", "Many ways to do it ...", users.get(1)),
            new Post(3L, "Reactive Web Programming", "Webflux uses Reactor ...", users.get(1)),
            new Post(4L, "JPA Made Easy", "Spring Data DSL automates JPQL creation ...", users.get(1))
    );
}
