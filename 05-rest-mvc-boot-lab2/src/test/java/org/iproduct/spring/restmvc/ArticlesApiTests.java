package org.iproduct.spring.restmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.config.SpringRootConfig;
import org.iproduct.spring.restmvc.config.SpringSecurityConfig;
import org.iproduct.spring.restmvc.config.SpringWebConfig;
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
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig({SpringRootConfig.class, SpringWebConfig.class, SpringSecurityConfig.class})
@AutoConfigureJsonTesters
@Slf4j
public class ArticlesApiTests {
    private MockMvc mockMvc;

    private static final List<Article> mockArticles = Arrays.asList(
            new Article("Welcome to Spring 5", "Spring 5 is great beacuse ..."),
            new Article("Dependency Injection", "Should I use DI or lookup ..."),
            new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans.")
    );

    private static final Article newArticle =
            new Article("222222222222222222222222","New Title", "New content ...", LocalDateTime.now());

    @MockBean
    private ArticleRepository articleRepository;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Autowired
    ObjectMapper mapper;

    @Test
    public void givenArticles_whenGetArticles_thenStatus200andJsonArray() throws Exception {
        given(articleRepository.findAll()).willReturn(mockArticles);

        mockMvc.perform(get("/api/articles").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].title").value("Welcome to Spring 5"))
                .andExpect(jsonPath("$[1].title").value("Dependency Injection"))
                .andExpect(jsonPath("$[2].title").value("Spring Beans and Wireing"));

        then(articleRepository).should(times(1)).findAll();
    }

    @Test
    public void givenArticles_whenPostArticleWithoutAuthorization_thenStatus401Unauthorized() throws Exception {
        given(articleRepository.insert(any(Article.class))).willReturn(newArticle);

        mockMvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newArticle)))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        verifyZeroInteractions(articleRepository);
    }

    @Test
    public void givenArticles_whenPostArticle_thenStatus201andLocationHeader() throws Exception {
        given(articleRepository.insert(any(Article.class))).willReturn(newArticle);

        mockMvc.perform(post("/api/articles")
                .with(httpBasic("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newArticle)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/api/articles/" + newArticle.getId()));

        then(articleRepository).should(times(1)).insert(any(Article.class));
    }


}
