package course.ws.blogs.web;

import course.ws.blogs.dto.ArticleCreateDto;
import course.ws.blogs.dto.ArticleDetailDto;
import course.ws.blogs.dto.ArticleUpdateDto;
import course.ws.blogs.dto.mapping.ArticleMapper;
import course.ws.blogs.entity.Article;
import course.ws.blogs.entity.User;
import course.ws.blogs.exception.InvalidEntityDataException;
import course.ws.blogs.exception.UnauthorizedException;
import course.ws.blogs.service.ArticleService;
import course.ws.blogs.util.UserUtil;
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

import static course.ws.blogs.dto.mapping.ArticleMapper.*;
import static course.ws.blogs.util.UserUtil.getUser;
import static course.ws.blogs.util.ValidationErrorUtil.handleValidationErrors;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<ArticleDetailDto> getAllArticles() {
        return articleService.getAll().stream().map(ArticleMapper::mapArticleToArticleDetailDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:[\\d]+}")
    public ArticleDetailDto getArticle(@PathVariable("id") Long id) {
        return mapArticleToArticleDetailDto(articleService.getById(id));
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ArticleDetailDto> addArticle(@Valid @RequestBody ArticleCreateDto articleDto, Errors errors,
                                                       Principal authentication) {
        handleValidationErrors(errors);
        Article article = mapArticleCreateDtoToAtricle(articleDto);
        User user = getUser(authentication);
        article.setAuthor(user);
        Article created = articleService.create(article);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(created.getId()).toUri()
        ).body(mapArticleToArticleDetailDto(created));
    }

    @PutMapping("/{id}")
    public ArticleDetailDto updateArticle(@Valid @RequestBody ArticleUpdateDto articleDto, Errors errors, @PathVariable("id") Long id) {
        if (!id.equals(articleDto.getId())) {
            throw new InvalidEntityDataException(
                    String.format("ID in URI: '%d' id different from ID in body: '%d'", id, articleDto.getId()));
        }
        handleValidationErrors(errors);
        Article article = mapArticleUpdateDtoToAtricle(articleDto);
        return mapArticleToArticleDetailDto(articleService.update(article));
    }

    @DeleteMapping("/{id:[\\d]+}")
    public Article deleteArticle(@PathVariable("id") Long id) {
        return articleService.deleteById(id);
    }

}
