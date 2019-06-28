package org.iproduct.spring.restmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@EnableEntityLinks
@Configuration
@ComponentScan("org.iproduct.spring.restmvc.web")
@Import({ SwaggerConfig.class, RestConfiguration.class  })
public class TestWebConfig implements WebMvcConfigurer{

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }

//    @Bean
//    public ObjectMapper jacksonObjectMapper() {
//        return new CustomObjectMapper();
//    }
//
//    @Bean
//    public SerializationConfig serializationConfig() {
//        return jacksonObjectMapper().getSerializationConfig();
//    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**")
//                .addResourceLocations("/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("/webjars/");
//        registry.addResourceHandler("/uploads/**")
//                .addResourceLocations("file:tmp/");
//    }

//    @Bean
//    public InternalResourceViewResolver viewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setViewClass(JstlView.class);
//        resolver.setPrefix("/WEB-INF/views/");
//        resolver.setSuffix(".jsp");
//        return resolver;
//    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/new-article").setViewName("articleForm");
//        registry.addViewController("/home").setViewName("home");
//    }

//    @Bean
//    public ThemeSource themeSource() {
//        ResourceBundleThemeSource themeSource = new ResourceBundleThemeSource();
//        themeSource.setBasenamePrefix("theme/");
//        return themeSource;
//    }
//
//    @Bean
//    public ThemeResolver themeResolver() {
//        CookieThemeResolver resolver = new CookieThemeResolver();
//        resolver.setDefaultThemeName("pulse");
//        return resolver;
//    }

//    @Bean("messageSource")
//    public MessageSource messageSource() {
//        ReloadableResourceBundleMessageSource messageSource=new ReloadableResourceBundleMessageSource();
//        messageSource.setBasename("classpath:messages");
//        messageSource.setDefaultEncoding("UTF-8");
//        messageSource.setUseCodeAsDefaultMessage(true);
//        return messageSource;
//    }
//
//    @Bean
//    public LocaleResolver localeResolver() {
//        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
//        localeResolver.setDefaultLocale(new Locale("bg", "BG"));
//        return localeResolver;
//    }
//
//    @Bean(name = "multipartResolver")
//    public StandardServletMultipartResolver multipartResolver() {
//        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
////        multipartResolver.setMaxUploadSize(100000);
//        return multipartResolver;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        ThemeChangeInterceptor themeChangeInterceptor = new ThemeChangeInterceptor();
//        themeChangeInterceptor.setParamName("theme");
//        registry.addInterceptor(themeChangeInterceptor);
//
//        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//        localeChangeInterceptor.setParamName("lang");
//        registry.addInterceptor(localeChangeInterceptor);
//    }

}
