package course.ws.blogs.dto.mapping;

import course.ws.blogs.dto.ArticleCreateDto;
import course.ws.blogs.dto.ArticleDetailDto;
import course.ws.blogs.dto.ArticleUpdateDto;
import course.ws.blogs.entity.Article;
import org.springframework.beans.BeanUtils;

public class ArticleDtoMappers {
    public static Article mapArticleCreateDtoToArticle(ArticleCreateDto source) {
        Article result = new Article();
        BeanUtils.copyProperties(source, result);
        return result;
    }
    public static Article mapArticleUpdateDtoToArticle(ArticleUpdateDto source) {
        Article result = new Article();
        BeanUtils.copyProperties(source, result);
        return result;
    }
    public static ArticleDetailDto mapArticleToArticleDetailDto(Article source) {
        ArticleDetailDto result = new ArticleDetailDto();
        BeanUtils.copyProperties(source, result);
        result.setAuthor(source.getAuthor() != null ?
                source.getAuthor().getFirstName() + " " +  source.getAuthor().getLastName() :
                "No author"
        );
        return result;
    }
}
