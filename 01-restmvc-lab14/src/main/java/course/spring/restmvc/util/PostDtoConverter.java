package course.spring.restmvc.util;

import com.github.jmnarloch.spring.boot.modelmapper.ConverterConfigurerSupport;
import course.spring.restmvc.dto.PostDto;
import course.spring.restmvc.dto.PostUserDto;
import course.spring.restmvc.entity.Category;
import course.spring.restmvc.entity.Post;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PostDtoConverter extends ConverterConfigurerSupport<Post, PostDto> {

    @Override
    protected Converter<Post, PostDto> converter() {
        return new AbstractConverter<Post, PostDto>() {

            @Override
            protected PostDto convert(Post source) {
                PostDto result = new PostDto();
                BeanUtils.copyProperties(source, result);
                PostUserDto author = new PostUserDto();
                BeanUtils.copyProperties(source.getAuthor(), author);
                result.setAuthor(author);
                result.setCategoryTitles(
                        source.getCategories().stream().map(Category::getTitle).collect(Collectors.toSet()));
                return result;
            }
        };
    }
}

