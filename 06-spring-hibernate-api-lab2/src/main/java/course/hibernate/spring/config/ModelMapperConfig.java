package course.hibernate.spring.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spring.SpringIntegration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(ApplicationContext beanFactory) {
        var modelMapper = new ModelMapper();
//        Provider<?> springProvider = SpringIntegration.fromSpring(beanFactory);
//        modelMapper.getConfiguration().setProvider(springProvider);
        modelMapper.getConfiguration()
//                .setMatchingStrategy(MatchingStrategies.LOOSE)
                .setSourceNamingConvention(NamingConventions.JAVABEANS_ACCESSOR)
                .setDestinationNamingConvention(NamingConventions.JAVABEANS_MUTATOR);

        return modelMapper;
    }
}
