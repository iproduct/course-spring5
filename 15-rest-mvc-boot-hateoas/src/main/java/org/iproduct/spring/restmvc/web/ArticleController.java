package org.iproduct.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.exception.InvalidEntityIdException;
import org.iproduct.spring.restmvc.model.Article;
import org.iproduct.spring.restmvc.service.ArticleService;
import org.iproduct.spring.restmvc.web.resource.ArticleResource;
import org.iproduct.spring.restmvc.web.resource.ArticleResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@ExposesResourceFor(ArticleResource.class)
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService service;

    @Autowired
    private ArticleResourceAssembler assembler;


//    @InitBinder()
//    protected void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
//            @Override
//            public void setAsText(String text) throws IllegalArgumentException{
//                setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//            }
//
//            @Override
//            public String getAsText() throws IllegalArgumentException {
//                return DateTimeFormatter.ofPattern("yyyy-MM-dd").format((LocalDateTime) getValue());
//            }
//        });
//    }

    @GetMapping
    public Resources<List<ArticleResource>> getArticles() { // Resources<Article>
        List<Article> articles = service.getArticles();
        List<ArticleResource> articleResources = assembler.toResources(articles);
        List<Link> links = new ArrayList<>();
        if(articles.size() > 0) {
            links.add(assembler.linkToSingleResource(articles.get(0)).withRel("first"));
            links.add(assembler.linkToSingleResource(articles.get(0)).withRel("last"));
        }
        return new Resources(articleResources, links);

//        return new Resources(
//                service.getArticles().stream().map(article -> new Resource<Article>(
//                        article,
//                        linkTo(methodOn(ArticleController.class)
//                                .getArticleById(article.getId())).withSelfRel(),
//                        linkTo(methodOn(UserController.class)
//                                .getUserById(article.getAuthorId())).withRel("author")
//                )).collect(Collectors.toList()),
//                linkTo(methodOn(ArticleController.class).getArticles()).withSelfRel()
//        );
    }

    @GetMapping("{id}")
    public ArticleResource getArticleById(@PathVariable String id) { // Resource<Article>
        return assembler.toResource(service.getArticleById(id));

//        Article article = service.getArticleById(id);
//        return new Resource<Article>(article,
//                linkTo(ArticleController.class).slash(article.getId()).withSelfRel()
//        );
    }

    @DeleteMapping("{id}")
    public Article deleteArticles(@PathVariable String id) {
        return service.deleteArticle(id);
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
//                                                 UriComponentsBuilder uriComponentsBuilder, HttpServletRequest req) {
        Article created = service.createArticle(article);
        URI location = MvcUriComponentsBuilder.fromMethodName(ArticleController.class, "createArticle", Article.class)
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri() ;
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .pathSegment("{id}").buildAndExpand(created.getId()).toUri() ;
//        URI location = uriComponentsBuilder.path(req.getServletPath())
//                .pathSegment("{id}").buildAndExpand(created.getId()).toUri() ;
        log.info("Article created: {}", location);
        return ResponseEntity.created(location).body(created);
//        return ResponseEntity.status(303).location(location).body(created);
    }

    @PutMapping("{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable String id, @RequestBody Article article) {
        if(!article.getId().equals(id)) throw new InvalidEntityIdException(
                String.format("Article ID=%s from path is different from Entity ID=%s", id, article.getId()));
        Article old = service.getArticleById(article.getId());
        Article updated = service.updateArticle(old.getAuthorId(), article);
        log.info("Article updated: {}", updated);
        return ResponseEntity.ok(updated);
    }

}
