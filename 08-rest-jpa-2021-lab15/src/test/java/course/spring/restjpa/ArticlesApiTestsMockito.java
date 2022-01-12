package course.spring.restjpa;

import course.spring.restjpa.dto.ArticleRepository;
import course.spring.restjpa.dto.UserRepository;
import course.spring.restjpa.entity.Article;
import course.spring.restjpa.entity.User;
import course.spring.restjpa.service.ArticleService;
import course.spring.restjpa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
//@ContextConfiguration(locations = "classpath:application-test.yaml")
//@TestPropertySource(inheritLocations = false, inheritProperties = false, locations = "classpath:application.yaml")
@Slf4j
@ActiveProfiles("test")
public class ArticlesApiTestsMockito {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleRepository mockArticleRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void givenArticles_whenGetArticles_thenStatus200JsonArray() throws Exception {
        given(mockArticleRepository.findAll()).willReturn(mockArticles);

        mockMvc.perform(get("/api/articles")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andDo(result -> log.info(result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.length()").value(mockArticles.size()))
                .andExpect(jsonPath("$[0].title").value(mockArticles.get(0).getTitle()))
                .andExpect(jsonPath("$[1].title").value(mockArticles.get(1).getTitle()))
                .andExpect(jsonPath("$[2].title").value(mockArticles.get(2).getTitle()));

        then(mockArticleRepository).should(times(1)).findAll();
        then(mockArticleRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void givenUser_whenGetUsersAuthenticated_thenStatus200JsonArray() throws Exception {
        given(userRepository.findAll()).willReturn(mockUsers);

        mockMvc.perform(get("/api/users")
                        .accept(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andDo(result -> log.info(result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.length()").value(mockUsers.size()))
                .andExpect(jsonPath("$[0].username").value(mockUsers.get(0).getUsername()))
                .andExpect(jsonPath("$[1].username").value(mockUsers.get(1).getUsername()));

        then(userRepository).should(times(1)).findAll();
        then(userRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void givenUser_whenGetUsersNotAuthenticated_thenStatus401() throws Exception {
        given(userRepository.findAll()).willReturn(mockUsers);

        mockMvc.perform(get("/api/users")
                        .accept(APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(""));

        then(userRepository).shouldHaveNoMoreInteractions();
    }

    private final List<Article> mockArticles = List.of(
            new Article("New in Spring", "Non-blocking reactive WebFlux web services ...", List.of("spring", "java", "webflux")),
            new Article("Spring Data Mongo DB - What's New?",
                    "References between Mongo DB collections supported by Spring Ddata ...", List.of("spring", "data", "mongo")),
            new Article("Spring Security - is It Easy?", "Even easier with Kotlin DSL ...", List.of("spring", "kotlin", "security"))
    );
    private final List<User> mockUsers = List.of(
            new User("admin", "Admin123&", "Default", "Admin", "AUTHOR, ADMIN"),
            new User("author", "Author123&", "Default", "Author")
    );

}
