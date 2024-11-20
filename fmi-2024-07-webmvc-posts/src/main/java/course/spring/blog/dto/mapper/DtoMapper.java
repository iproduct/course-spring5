package course.spring.blog.dto.mapper;

import course.spring.blog.dto.PostCreateDto;
import course.spring.blog.entity.Post;
import org.springframework.beans.BeanUtils;

public class DtoMapper {
    public static Post mapPostCreateDtoToPost(PostCreateDto dto){
        var post = new Post();
        BeanUtils.copyProperties(dto, post);
        return post;
    }
}
