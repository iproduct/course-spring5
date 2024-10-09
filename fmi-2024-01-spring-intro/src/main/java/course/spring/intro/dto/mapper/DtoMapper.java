package course.spring.intro.dto.mapper;

import course.spring.intro.dto.PostCreateDto;
import course.spring.intro.entity.Post;
import org.springframework.beans.BeanUtils;

public class DtoMapper {
    public static Post mapPostCreateDtoToPost(PostCreateDto dto){
        var post = new Post();
        BeanUtils.copyProperties(dto, post);
        return post;
    }
}
