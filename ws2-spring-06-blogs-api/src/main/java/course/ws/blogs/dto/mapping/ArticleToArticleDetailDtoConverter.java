package course.ws.blogs.dto.mapping;

import com.github.jmnarloch.spring.boot.modelmapper.ConverterConfigurerSupport;
import com.github.jmnarloch.spring.boot.modelmapper.PropertyMapConfigurerSupport;
import course.ws.blogs.dto.ArticleDetailDto;
import course.ws.blogs.entity.Article;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ArticleToArticleDetailDtoConverter extends ConverterConfigurerSupport<Article, ArticleDetailDto> {

    @Override
    protected Converter<Article, ArticleDetailDto> converter() {
        return new AbstractConverter<Article, ArticleDetailDto>() {
            @Override
            public ArticleDetailDto convert(Article source) {
                ArticleDetailDto result = new ArticleDetailDto();
                BeanUtils.copyProperties(source, result);
                result.setAuthor(
                        source.getAuthor().getFirstName() + " " +  source.getAuthor().getLastName()
                );
                return result;
            }
        };
    }
}
