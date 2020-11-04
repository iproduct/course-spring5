package course.spring.restmvc.util;

import course.spring.restmvc.dto.PostDTO;
import course.spring.restmvc.dto.PostUserDTO;
import course.spring.restmvc.entity.Category;
import course.spring.restmvc.entity.Post;
import course.spring.restmvc.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class DtoUtil {
    private static final ModelMapper modelMapper = new ModelMapper();
    static {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        log.info(modelMapper.getTypeMaps().toString());
        modelMapper.typeMap(Post.class, PostDTO.class, "postToDtoMap")
                .addMapping(src -> {
                    Set<String> titles = new LinkedHashSet<String>();
                    for (Category c : src.getCategories()) {
                        titles.add(c.getTitle());
                    }
                    return titles;
                }, PostDTO::setCategoryTitles);
    }

//    public static PostDTO getPostDTO(Post created) {
//        PostDTO postDto = new PostDTO();
//        BeanUtils.copyProperties(created, postDto);
//        PostUserDTO author = new PostUserDTO();
//        BeanUtils.copyProperties(created.getAuthor(), author);
//        postDto.setAuthor(author);
//        postDto.setCategoryTitles(
//                created.getCategories().stream().map(Category::getTitle).collect(Collectors.toSet()));
//        return postDto;
//    }

    public static PostDTO getPostDTO(Post created) {
        PostDTO postDto = modelMapper.map(created, PostDTO.class);
        return postDto;
    }

}
