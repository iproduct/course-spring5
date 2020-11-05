package course.spring.restmvc.util;

import com.github.jmnarloch.spring.boot.modelmapper.PropertyMapConfigurerSupport;
import course.spring.restmvc.dto.PostDto;
import course.spring.restmvc.entity.Category;
import course.spring.restmvc.entity.Post;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

//@Component
public class PostDtoMapping extends PropertyMapConfigurerSupport<Post, PostDto> {

    @Override
    public PropertyMap<Post, PostDto> mapping() {

        return new PropertyMap<Post, PostDto>() {
            @Override
            protected void configure() {
//                Set<String> titles = new LinkedHashSet<String>();
//                for (Category c : source.getCategories()) {
//                    titles.add(c.getTitle());
//                }
//                map().setCategoryTitles(titles);
            }
        };
    }
}
