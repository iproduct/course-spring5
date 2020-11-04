package course.spring.restmvc.util;

import course.spring.restmvc.dto.PostDTO;
import course.spring.restmvc.dto.PostUserDTO;
import course.spring.restmvc.entity.Category;
import course.spring.restmvc.entity.Post;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

public class DtoUtil {

    public static PostDTO getPostDTO(Post created) {
        PostDTO postDto = new PostDTO();
        BeanUtils.copyProperties(created, postDto);
        PostUserDTO author = new PostUserDTO();
        BeanUtils.copyProperties(created.getAuthor(), author);
        postDto.setAuthor(author);
        postDto.setCategoryTitles(
                created.getCategories().stream().map(Category::getTitle).collect(Collectors.toSet()));
        return postDto;
    }
}
