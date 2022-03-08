package course.ws.blogs.dto.mapping;

import course.ws.blogs.dto.ArticleCreateDto;
import course.ws.blogs.dto.ArticleDetailDto;
import course.ws.blogs.dto.ArticleUpdateDto;
import course.ws.blogs.entity.Article;
import org.springframework.beans.BeanUtils;

public class ArticleMapper {
    public static Article mapArticleCreateDtoToAtricle(ArticleCreateDto createDto) {
        Article article = new Article();
        BeanUtils.copyProperties(createDto, article);
        return article;
    }
    public static Article mapArticleUpdateDtoToAtricle(ArticleUpdateDto updateDto) {
        Article article = new Article();
        BeanUtils.copyProperties(updateDto, article);
        return article;
    }
    public static ArticleDetailDto mapArticleToArticleDetailDto(Article article) {
        ArticleDetailDto articleDto = new ArticleDetailDto();
        BeanUtils.copyProperties(article, articleDto);
        articleDto.setAuthor(article.getAuthor().getFirstName()
                + " " + article.getAuthor().getLastName());
        return articleDto;
    }
}
