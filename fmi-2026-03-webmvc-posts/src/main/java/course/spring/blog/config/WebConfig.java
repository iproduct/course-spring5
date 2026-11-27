package course.spring.blog.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:tmp/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/posts");
        registry.addViewController("/error").setViewName("");
    }

    @Bean
    HandlerExceptionResolver getExceptionResolver() {
        return new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    Object object,
                    Exception ex) {
                ModelAndView modelAndView = new ModelAndView("errors");
                if (ex instanceof MaxUploadSizeExceededException) {
                    log.error("Max upload size exceeded error:", ex);
                    modelAndView.getModel().put("message", ex.getMessage());
                }
                return modelAndView;
            }
        };
    }

    //Tomcat large file upload connection reset
    //http://www.mkyong.com/spring/spring-file-upload-and-connection-reset-issue/
    // Set maxPostSize of embedded tomcat server to 11 megabytes (default is 2 MB, not large enough to support file uploads > 1.5 MB)
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> getTomcatCustomizer() {
        return new WebServerFactoryCustomizer<TomcatServletWebServerFactory>() {
            @Override
            public void customize(TomcatServletWebServerFactory factory) {
                factory.addConnectorCustomizers(
                        (connector) -> {
                            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                                //-1 means unlimited
                                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(11534336); // 11 MB
                            }
                            connector.setMaxPostSize(11534336); // 11 MB
                        }
                );
            }
        };
    }

}
