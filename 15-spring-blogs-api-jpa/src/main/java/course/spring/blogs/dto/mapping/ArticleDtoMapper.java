package course.spring.blogs.dto.mapping;

import course.spring.blogs.dto.ArticleCreateDto;
import course.spring.blogs.dto.ArticleDetailDto;
import course.spring.blogs.dto.ArticleUpdateDto;
import course.spring.blogs.entity.Article;
import org.springframework.beans.BeanUtils;

public class ArticleDtoMapper {
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
        var author = source.getAuthor();
        result.setAuthor(author != null ?
                author.getFirstName() + ' ' + author.getLastName()
                : "No author"
        );
        return result;
    }
}
