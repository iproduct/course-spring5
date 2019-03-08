package patchdemo.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.mongodb.util.JSONParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import patchdemo.exception.InvalidEntityIdException;
import patchdemo.model.Article;
import patchdemo.service.ArticlesService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
@Slf4j
public class ArticleController {

    @Autowired
    ArticlesService service;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping
    public List<Article> getArticles() {
        return service.getAllArticles();
    }

    @GetMapping("{id}")
    public Article getArticleById(@PathVariable String id) {
        return service.getArticleById(id);
    }

    @DeleteMapping("{id}")
    public Article deleteArticles(@PathVariable String id) {
        return service.deleteArticle(id);
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article, UriComponentsBuilder uriComponentsBuilder,
                                              HttpServletRequest req) {
        Article created = service.createArticle(article);
        log.info(">>> Request URI {}", req.getServletPath());
        URI location = uriComponentsBuilder.path(req.getServletPath())
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri() ;
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable String id, @RequestBody Article article) {
        if(!article.getId().equals(id)) throw new InvalidEntityIdException(
                String.format("Article ID=%s from path is different from Entity ID=%s", id, article.getId()));
        Article updated = service.updateArticle(article);
        log.info("Article updated: {}", updated);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping(path = "{id}", consumes = "application/json-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> patchArticle(@PathVariable String id, @RequestBody String jsonPatchResource) throws JsonPatchException, IOException {
        Article sourceArticle = service.getArticleById(id);
        JsonPatch patch = mapper.readValue(jsonPatchResource, JsonPatch.class);
        JsonNode patchedNode = patch.apply(mapper.convertValue(sourceArticle, JsonNode.class));
        Article patchedArticle = mapper.convertValue(patchedNode, Article.class);
        Article resultArticle =  service.updateArticle(patchedArticle);
        log.info("Article patched: {}", resultArticle);
        return ResponseEntity.ok(resultArticle);
    }
    @PatchMapping(path = "{id}", consumes = "application/merge-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> mergePatchArticle(@PathVariable String id, @RequestBody String jsonPatchResource) throws IOException, JsonPatchException {
        Article sourceArticle = service.getArticleById(id);
        JsonMergePatch patch = mapper.readValue(jsonPatchResource, JsonMergePatch.class);
        JsonNode patchedNode = patch.apply(mapper.convertValue(sourceArticle, JsonNode.class));
        Article patchedArticle = mapper.convertValue(patchedNode, Article.class);
        Article resultArticle =  service.updateArticle(patchedArticle);
        log.info("Article patched: {}", resultArticle);
        return ResponseEntity.ok(resultArticle);
    }
}
