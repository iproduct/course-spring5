package org.iproduct.spring.restmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.config.TestRootConfig;
import org.iproduct.spring.restmvc.config.SpringSecurityConfig;
import org.iproduct.spring.restmvc.config.TestWebConfig;
import org.iproduct.spring.restmvc.dao.ArticleRepository;
import org.iproduct.spring.restmvc.model.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringJUnitWebConfig(classes = {TestRootConfig.class, TestWebConfig.class, SpringSecurityConfig.class})
//@ContextHierarchy({
//        @ContextConfiguration(classes = SpringRootConfig.class),
//        @ContextConfiguration(classes = SpringWebConfig.class)
//})
//@WebAppConfiguration
//@ContextConfiguration(classes = SpringWebConfig.class)
@AutoConfigureJsonTesters
@Slf4j
public class ArticlesAPITests {

    private static final List<Article> mockArticles = Arrays.asList(
            new Article("Welcome to Spring 5", "Spring 5 is great beacuse ...", "1111111111111111111111"),
            new Article("Dependency Injection", "Should I use DI or lookup ...", "1111111111111111111111"),
            new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans.", "1111111111111111111111")
    );

    private static final Article newArticle =
            new Article("222222222222222222222222","New Title", "New content ...", "1111111111111111111111",
            LocalDateTime.now(), LocalDateTime.now());


    //    @TestConfiguration
//    static class ArticlesServiceImplTestContextConfiguration {
//
//        @Bean
//        public ArticleService employeeService() {
//            return new ArticleServiceImpl();
//        }
//    }

    @Autowired
    private ObjectMapper mapper;

//    @Autowired
//    private ArticleService articleService;

    @MockBean
    private ArticleRepository articleRepository;


    private MockMvc mockMvc;

    private String authToken;


    @BeforeEach
    void setup(WebApplicationContext wac) throws Exception {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                    .apply(springSecurity())
                    .build();

//        MvcResult result = mockMvc.perform(post("/login")
//                .accept("*/*")
//                .header("Host", "localhost:9000")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .content("username=admin&password=admin"))
//                .andDo(print())
//                .andReturn();
//        authToken = result.getResponse().getCookie("JSESSIONID").getValue();
//        log.info(">>> AUTH Token: {}", authToken);
    }

    @Test
    void givenArticles_whenGetArticles_thenStatus200andJsonArray() throws Exception {

        given(articleRepository.findAll()).willReturn(mockArticles);

        mockMvc.perform(get("/api/articles")
                .with(user("admin").password("admin").roles("ADMIN"))
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(result -> log.info(result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.content.length()").value(3))
//                .andExpect(jsonPath("$.length()").value(greaterThan(2)))
                .andExpect(jsonPath("$.content[0].title").value("Welcome to Spring 5"))
                .andExpect(jsonPath("$.content[1].title").value("Dependency Injection"))
                .andExpect(jsonPath("$.content[2].title").value("Spring Beans and Wireing"));

        then(articleRepository).should(times(1)).findAll();
    }

    @Test
    void givenArticles_whenPostArticle_thenStatus201andLocationHeader() throws Exception {

        given(articleRepository.insert(any(Article.class))).willReturn(newArticle);

        mockMvc.perform(post("/api/articles")
                    .with(user("admin").password("admin").roles("ADMIN"))
//                    .with(httpBasic("admin","admin"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(newArticle))
                    .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(header().string("location",
                        containsString("http://localhost/api/articles/" + newArticle.getId())));

        then(articleRepository).should(times(1)).insert(any(Article.class));
        verifyNoMoreInteractions(articleRepository);
    }

}

