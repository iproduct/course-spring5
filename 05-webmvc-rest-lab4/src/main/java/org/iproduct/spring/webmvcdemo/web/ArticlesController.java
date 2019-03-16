package org.iproduct.spring.webmvcdemo.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.webmvcdemo.domain.ArticlesService;
import org.iproduct.spring.webmvcdemo.exceptions.InvalidEntityIdException;
import org.iproduct.spring.webmvcdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystemException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/articles")
@Slf4j
public class ArticlesController {
    private static final String UPLOADS_DIR = "tmp";

    @Autowired
    private ArticlesService articlesService;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping
    public List<Article> getArticles(){
        return articlesService.getArticles();
    }

    @GetMapping("{id}")
    public ResponseEntity<Article> getArticles(@PathVariable("id") String id){
        Article article = articlesService.getArticleById(id);
        return ResponseEntity.ok(article);
    }

    @PostMapping
    public ResponseEntity<Article> addArticle(@Valid @RequestBody Article article, BindingResult errors,
                               HttpServletRequest req, UriComponentsBuilder uriComponentsBuilder){
        log.info("CREATE Article: {}", article);
        Article created = articlesService.add(article);
        URI location = uriComponentsBuilder.path(req.getServletPath())
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping("{id}")
    public Article deleteArticles(@PathVariable String id) {
        return articlesService.delete(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<Article> getArticles(@PathVariable("id") String id,
           @Valid @RequestBody Article article, BindingResult errors){
        if(!id.equals(article.getId())) {
            throw new InvalidEntityIdException(
                    String.format("Article ID=%s from path is different from Entity ID=%s",
                            id, article.getId())
            );
        }
        Article updated = articlesService.update(article);
        log.info("UPDATED Article: {}", updated);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping(path = "{id}", consumes = "application/json-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> patchArticle(@PathVariable String id, @RequestBody String jsonPatchResource) throws JsonPatchException, IOException {
        Article sourceArticle = articlesService.getArticleById(id);
        JsonPatch patch = mapper.readValue(jsonPatchResource, JsonPatch.class);
        JsonNode patchedNode = patch.apply(mapper.convertValue(sourceArticle, JsonNode.class));
        Article patchedArticle = mapper.convertValue(patchedNode, Article.class);
        Article resultArticle =  articlesService.update(patchedArticle);
        log.info("Article patched: {}", resultArticle);
        return ResponseEntity.ok(resultArticle);
    }

    @PatchMapping(path = "{id}", consumes = "application/merge-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> mergePatchArticle(@PathVariable String id, @RequestBody String jsonPatchResource) throws IOException, JsonPatchException {
        Article sourceArticle = articlesService.getArticleById(id);
        JsonMergePatch patch = mapper.readValue(jsonPatchResource, JsonMergePatch.class);
        JsonNode patchedNode = patch.apply(mapper.convertValue(sourceArticle, JsonNode.class));
        Article patchedArticle = mapper.convertValue(patchedNode, Article.class);
        Article resultArticle =  articlesService.update(patchedArticle);
        log.info("Article patched: {}", resultArticle);
        return ResponseEntity.ok(resultArticle);
    }


}
