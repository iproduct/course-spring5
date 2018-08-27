package org.iproduct.spring.webmvc.initializer;

import org.iproduct.spring.webmvc.config.SpringRootConfig;
import org.iproduct.spring.webmvc.config.SpringWebConfig;
import org.iproduct.spring.webmvc.config.WebSecurityConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;


//public class ArticlesWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
//
//    private static final String LOCATION = "c:/temp/"; // Temporary location where files will be stored
//
//    private static final long MAX_FILE_SIZE = 5242880; // 5MB : Max file size.
//    // Beyond that size spring will throw exception.
//    private static final long MAX_REQUEST_SIZE = 20971520; // 20MB : Total request size containing Multi part.
//
//    private static final int FILE_SIZE_THRESHOLD = 0; // Size threshold after which files will be written to disk
//
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class<?>[] { WebSecurityConfig.class, SpringRootConfig.class,  };
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class<?>[] { SpringWebConfig.class };
//    }
//
//    @Override
//    protected String[] getServletMappings() {
//        return new String[] { "/" };
//    }
//
//    @Override
//    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
//        registration.setMultipartConfig(getMultipartConfigElement());
//    }
//
//    private MultipartConfigElement getMultipartConfigElement() {
//        MultipartConfigElement multipartConfigElement = new MultipartConfigElement( LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
//        return multipartConfigElement;
//    }
//
//}

public class ArticlesWebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletCxt) {

        // Load root application configuration
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringRootConfig.class);
//        rootContext.refresh();
        rootContext.registerShutdownHook();

        // Manage the lifecycle of the root application context
        servletCxt.addListener(new ContextLoaderListener(rootContext));

        // Load Spring web application configuration
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
//        webContext.setParent(rootContext);
//        webContext.setServletContext(servletCxt);
        webContext.register(SpringWebConfig.class);
//        webContext.refresh();

        // Create DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(webContext);

        // Register and map the Servlet
        ServletRegistration.Dynamic registration = servletCxt.addServlet("dispatcher", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");

    }

}