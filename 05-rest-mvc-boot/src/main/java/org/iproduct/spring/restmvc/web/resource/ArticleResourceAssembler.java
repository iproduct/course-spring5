package org.iproduct.spring.restmvc.web.resource;

import org.iproduct.spring.restmvc.model.Article;
import org.iproduct.spring.restmvc.model.User;
import org.iproduct.spring.restmvc.service.UserService;
import org.iproduct.spring.restmvc.web.ArticleController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
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

        // Add link to author resource
        resource.add(userResourceAssembler.linkToSingleResource(userService.getUserById(article.getAuthorId()))
                .withRel("author"));

        return resource;
    }
    @Override
    protected ArticleResource instantiateResource(Article entity) {
        return new ArticleResource(entity.getId(), entity.getTitle(), entity.getContent(),
                entity.getAuthorId(), entity.getCreated(), entity.getUpdated());
    }
}
