package org.iproduct.spring.restmvc.web.resource;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.exception.EntityNotFoundException;
import org.iproduct.spring.restmvc.model.Article;
import org.iproduct.spring.restmvc.model.User;
import org.iproduct.spring.restmvc.service.UserService;
import org.iproduct.spring.restmvc.web.ArticleController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ArticleResourceAssembler extends EmbeddableResourceAssemblerSupport<Article, ArticleResource, ArticleController> {

    @Autowired
    private UserResourceAssembler userResourceAssembler;

    @Autowired
    private UserService userService;

    @Autowired
    public ArticleResourceAssembler(final EntityLinks entityLinks, final RelProvider relProvider) {
        super(entityLinks, relProvider, ArticleController.class, ArticleResource.class);
    }

    @Override
    public Link linkToSingleResource(Article article) {
        return entityLinks.linkToSingleResource(ArticleResource.class, article);
    }

    @Override
    public ArticleResource toResource(Article article) {
        final ArticleResource resource = createResourceWithId(article, article);

        try {
            User author = userService.getUserById(article.getAuthorId());
            // Add link to author resource
            resource.add(userResourceAssembler
                    .linkToSingleResource(author)
                    .withRel("author"));
        } catch(EntityNotFoundException ex) {
            log.error("Error finding article author: {}", ex.getMessage());
        }

//        List<User> authors = new ArrayList<>();
//        authors.add(author);

//        resource.setEmbeddeds(new Resources(userResourceAssembler.toEmbeddable(authors)));


        return resource;
    }
    @Override
    protected ArticleResource instantiateResource(Article entity) {
        return new ArticleResource(entity.getId(), entity.getTitle(), entity.getContent(),
                entity.getAuthorId(), entity.getCreated(), entity.getUpdated());
    }
}
