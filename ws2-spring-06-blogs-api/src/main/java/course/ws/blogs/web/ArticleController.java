package course.ws.blogs.web;

import course.ws.blogs.dto.ArticleCreateDto;
import course.ws.blogs.dto.ArticleDetailDto;
import course.ws.blogs.entity.Article;
import course.ws.blogs.entity.User;
import course.ws.blogs.exception.InvalidEntityDataException;
import course.ws.blogs.exception.UnautorizedException;
import course.ws.blogs.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static course.ws.blogs.util.ErrorHandlingUtils.checkErrors;
import static course.ws.blogs.util.UserUtils.getUser;

@RestController
@RequestMapping("/api/articles")
@Slf4j
public class ArticleController {
    private ArticleService articleService;
    private ModelMapper mapper;

    @Autowired
    public ArticleController(ArticleService articleService, ModelMapper mapper) {
        this.articleService = articleService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<Article>  getAllArticles() {
       return articleService.findAllArticles();
    }

    @GetMapping("/{id:\\d+}")
    public Article  getArticleById(@PathVariable Long id) {
       return articleService.findArticleById(id);
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ArticleDetailDto> addNewArticle(@Valid @RequestBody ArticleCreateDto articleDto, Errors errors,
                                                          Principal principal) {
        checkErrors(errors);
        log.info("Logged User: {}", principal.toString());
        Article article = mapper.map(articleDto, Article.class);
        User user = getUser(principal);
        article.setAuthor(user);
//        Article article1 = new Article();
//        BeanUtils.copyProperties(articleDto, article1);

        Article newArticle = articleService.create(article);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                                .buildAndExpand(newArticle.getId()).toUri())
                .body(mapper.map(newArticle, ArticleDetailDto.class));
    }



    @PutMapping("/{id:\\d+}")
    public Article updateArticle(@Valid @RequestBody Article article, Errors errors, @PathVariable Long id) {
        if(!id.equals(article.getId())){
            throw new InvalidEntityDataException(
                    String.format("ID='%s' in path differs from ID='%s' in message body", id, article.getId()));
        }
        checkErrors(errors);
        return articleService.update(article);
    }

    @DeleteMapping("/{id:\\d+}")
    public Article  deleteArticleById(@PathVariable Long id) {
        return articleService.deleteArticleById(id);
    }

}
