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
public class MapperConfigurer implements ModelMapperConfigurer {

    public void configure(ModelMapper modelMapper) {
        modelMapper.getConfiguration()
//                .setMatchingStrategy(MatchingStrategies.LOOSE)
                .setSourceNamingConvention(NamingConventions.JAVABEANS_ACCESSOR)
                .setDestinationNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
    }
}
