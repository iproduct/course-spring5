package org.iproduct.spring.restmvc.web.resource;

import org.iproduct.spring.restmvc.model.Article;
import org.iproduct.spring.restmvc.model.User;
import org.iproduct.spring.restmvc.service.ArticleService;
import org.iproduct.spring.restmvc.web.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.awt.print.Book;

@Component
public class UserResourceAssembler extends EmbeddableResourceAssemblerSupport<User, UserResource, UserController> {

    @Autowired
    private ArticleResourceAssembler articleResourceAssembler;

    @Autowired
    ArticleService articleService;

    @Autowired
    public UserResourceAssembler(final EntityLinks entityLinks, final RelProvider relProvider) {
        super(entityLinks, relProvider, UserController.class, UserResource.class);
    }

    @Override
    public Link linkToSingleResource(User user) {
        return entityLinks.linkToSingleResource(UserResource.class, user);
    }


    @Override
    public UserResource toResource(User user) {
        final UserResource resource = createResourceWithId(user, user);

        // Add (multiple) links to authored books
        for(Article article : articleService.getArticlesByAuthorId(user.getId())) {
            resource.add( articleResourceAssembler.linkToSingleResource(article).withRel("authored-articles") );
        }

        return resource;
    }

    @Override
    protected UserResource instantiateResource(User entity) {
        return new UserResource(entity.getId(), entity.getUsername(), entity.getFname(),
                entity.getLname(), entity.getRoles(), entity.isActive(), entity.getCreated(), entity.getUpdated());
    }
}
