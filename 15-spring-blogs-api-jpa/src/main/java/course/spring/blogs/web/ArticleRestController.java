package course.spring.blogs.web;

import course.spring.blogs.dto.ArticleCreateDto;
import course.spring.blogs.dto.ArticleDetailDto;
import course.spring.blogs.dto.ArticleUpdateDto;
import course.spring.blogs.dto.mapping.ArticleDtoMapper;
import course.spring.blogs.entity.Article;
import course.spring.blogs.entity.User;
import course.spring.blogs.exception.InvalidEntityDataException;
import course.spring.blogs.service.ArticleService;
import course.spring.blogs.utils.ErrorHandlingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static course.spring.blogs.dto.mapping.ArticleDtoMapper.*;


@RestController
@RequestMapping("/api/articles")
@Slf4j
public class ArticleRestController {
    @Autowired
    private ArticleService articleService;

    @GetMapping
    public List<ArticleDetailDto> getAllArticles() {
        return articleService.getAllArticles().stream()
                .map(ArticleDtoMapper::mapArticleToArticleDetailDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:\\d+}")
    public ArticleDetailDto getArticleById(@PathVariable("id") Long id) {
        return mapArticleToArticleDetailDto(articleService.getArticleById(id));
    }

    @PostMapping
    public ResponseEntity<ArticleDetailDto> addNewArticle(@Valid @RequestBody ArticleCreateDto articleDto, Errors errors,
                  Principal auth) {
        ErrorHandlingUtils.handleValidationErrors(errors);
        log.info("Principal: {}", auth);

        var created = articleService.create(mapArticleCreateDtoToArticle(articleDto));
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(created.getId()).toUri()
        ).body(mapArticleToArticleDetailDto(created));
    }

    @PutMapping("/{id}")
    public ArticleDetailDto updateAticle(@PathVariable("id") Long id, @Valid @RequestBody ArticleUpdateDto articleDto, Errors errors) {
        ErrorHandlingUtils.handleValidationErrors(errors);

        if(!id.equals(articleDto.id())) {
            throw new InvalidEntityDataException(
                    String.format("ID in URL='%d' is different from ID in message body = '%d'", id, articleDto.id()));
        }
        return mapArticleToArticleDetailDto(articleService.update(mapArticleUpdateDtoToArticle(articleDto)));
    }

    @DeleteMapping("/{id}")
    public Article deleteArticleById(@PathVariable("id") Long id) {
        return articleService.deleteArticleById(id);
    }

    @GetMapping(value = "/count", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getArticlsCount() {
        return Long.toString(articleService.getArticlesCount());
    }



}
