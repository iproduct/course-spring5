package course.ws.blogs.web;

import course.ws.blogs.dto.ErrorResponse;
import course.ws.blogs.entity.Article;
import course.ws.blogs.exception.EntityNotFoundException;
import course.ws.blogs.exception.InvalidEntityDataException;
import course.ws.blogs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article>  getAllArticles() {
       return articleService.findAllArticles();
    }

    @GetMapping("/{id}")
    public Article  getArticleById(@PathVariable Long id) {
       return articleService.findArticleById(id);
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Article> addNewArticle(@RequestBody Article article) {
        Article newArticle = articleService.create(article);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(newArticle.getId()).toUri())
                .body(newArticle);
    }

    @PutMapping("/{id}")
    public Article updateArticle(@RequestBody Article article, @PathVariable Long id) {
        if(!id.equals(article.getId())){
            throw new InvalidEntityDataException(
                    String.format("ID='%s' in path differs from ID='%s' in message body", id, article.getId()));
        }
        return articleService.update(article);
    }

    @DeleteMapping("/{id}")
    public Article  deleteArticleById(@PathVariable Long id) {
        return articleService.deleteArticleById(id);
    }

}
