package course.ws.blogs.dto.mapping;

import com.github.jmnarloch.spring.boot.modelmapper.PropertyMapConfigurerSupport;
import course.ws.blogs.dto.ArticleDetailDto;
import course.ws.blogs.entity.Article;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

//@Component
public class ArticleToArticleDetailDtoMapping extends PropertyMapConfigurerSupport<Article, ArticleDetailDto> {

    @Override
    public PropertyMap<Article, ArticleDetailDto> mapping() {
        return new PropertyMap<Article, ArticleDetailDto>() {
            @Override
            protected void configure() {
                map().setAuthor(
                        source.getAuthor().getFirstName() + " " +  source.getAuthor().getLastName()
                );
            }
        };
    }
}
