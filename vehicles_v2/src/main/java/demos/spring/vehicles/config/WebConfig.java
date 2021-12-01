package demos.spring.vehicles.config;

import demos.spring.vehicles.util.StringToEnumConverterFactory;
import demos.spring.vehicles.util.StringToModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private StringToModelConverter stringToModelConverter;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/offers");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToModelConverter);
        registry.addConverterFactory(new StringToEnumConverterFactory());
    }
}
