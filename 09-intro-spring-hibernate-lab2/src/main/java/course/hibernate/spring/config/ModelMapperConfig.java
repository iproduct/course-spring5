package course.hibernate.spring.config;

import com.github.jmnarloch.spring.boot.modelmapper.ModelMapperConfigurer;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NamingConventions;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperConfig implements ModelMapperConfigurer {
    public void configure(ModelMapper modelMapper) {
        modelMapper.getConfiguration()
//                .setMatchingStrategy(MatchingStrategies.LOOSE)
                .setSourceNamingConvention(NamingConventions.JAVABEANS_ACCESSOR)
                .setDestinationNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
    }
}
