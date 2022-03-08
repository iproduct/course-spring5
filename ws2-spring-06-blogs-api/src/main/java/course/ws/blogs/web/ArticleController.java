package course.ws.blogs.web;

import course.ws.blogs.dto.ArticleCreateDto;
import course.ws.blogs.dto.ArticleDetailDto;
import course.ws.blogs.dto.ArticleUpdateDto;
import course.ws.blogs.dto.mapping.ArticleDtoMappers;
import course.ws.blogs.entity.Article;
import course.ws.blogs.entity.User;
import course.ws.blogs.exception.InvalidEntityDataException;
import course.ws.blogs.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static course.ws.blogs.dto.mapping.ArticleDtoMappers.*;
import static course.ws.blogs.util.ErrorHandlingUtils.checkErrors;
import static course.ws.blogs.util.UserUtils.getUser;

@RestController
@RequestMapping("/api/articles")
@Slf4j
public class ArticleController {
    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<ArticleDetailDto>  getAllArticles() {
       return articleService.findAllArticles().stream()
               .map(ArticleDtoMappers::mapArticleToArticleDetailDto)
               .collect(Collectors.toList());
    }

    @GetMapping("/{id:\\d+}")
    public ArticleDetailDto  getArticleById(@PathVariable Long id) {
       return mapArticleToArticleDetailDto(articleService.findArticleById(id));
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ArticleDetailDto> addNewArticle(@Valid @RequestBody ArticleCreateDto articleDto, Errors errors,
                                                          Principal principal) {
        checkErrors(errors);
        Article article = mapArticleCreateDtoToArticle(articleDto);
        User user = getUser(principal);
        article.setAuthor(user);
        Article newArticle = articleService.create(article);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                                .buildAndExpand(newArticle.getId()).toUri())
                .body(mapArticleToArticleDetailDto(newArticle));
    }



    @PutMapping("/{id:\\d+}")
    public ArticleDetailDto updateArticle(@Valid @RequestBody ArticleUpdateDto articleDto, Errors errors, @PathVariable Long id) {
        if(!id.equals(articleDto.getId())){
            throw new InvalidEntityDataException(
                    String.format("ID='%s' in path differs from ID='%s' in message body", id, articleDto.getId()));
        }
        checkErrors(errors);
        return mapArticleToArticleDetailDto(
                articleService.update(mapArticleUpdateDtoToArticle(articleDto))
        );
    }

    @DeleteMapping("/{id:\\d+}")
    public Article  deleteArticleById(@PathVariable Long id) {
        return articleService.deleteArticleById(id);
    }

}
