package course.spring.restmvc.config;

import com.github.jmnarloch.spring.boot.modelmapper.ModelMapperConfigurer;
import course.spring.restmvc.dto.PostDto;
import course.spring.restmvc.entity.Category;
import course.spring.restmvc.entity.Post;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class CustomConfigurer implements ModelMapperConfigurer {

    public void configure(ModelMapper modelMapper) {
        modelMapper.getConfiguration()
                .setSourceNamingConvention(NamingConventions.JAVABEANS_ACCESSOR)
                .setDestinationNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
//        modelMapper.getConfiguration()
////                .setFieldMatchingEnabled(true)
////                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
//                .setMatchingStrategy(MatchingStrategies.STANDARD);
//        modelMapper.createTypeMap(Post.class, PostDto.class, "postToPostDtoMapping");
//        modelMapper.addMappings(new PropertyMap<Post, PostDto>() {
//            @Override
//            protected void configure() {
//                Set<String> titles = new LinkedHashSet<String>();
//                for (Category c : source.getCategories()) {
//                    titles.add(c.getTitle());
//                }
//                map().setCategoryTitles(titles);
//
//            }
//        });
//                mapper -> mapper.map(src -> {
//                    Set<String> titles = new LinkedHashSet<String>();
//                    for (Category c : src.getCategories()) {
//                        titles.add(c.getTitle());
//                    }
//                    return titles;
//                }, PostDto::setCategoryTitles));
//                .addMapping(src -> {
//            Set<String> titles = new LinkedHashSet<String>();
//            for (Category c : src.getCategories()) {
//                titles.add(c.getTitle());
//            }
//            return titles;
//        }, PostDto::setCategoryTitles);
//        log.info(modelMapper.getTypeMaps().toString());
    }
}
