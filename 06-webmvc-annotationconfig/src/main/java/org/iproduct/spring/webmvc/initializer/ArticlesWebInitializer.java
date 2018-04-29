package org.iproduct.spring.webmvc.initializer;

import org.iproduct.spring.webmvc.config.SpringRootConfig;
import org.iproduct.spring.webmvc.config.SpringWebConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class ArticlesWebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletCxt) {

        // Load root application configuration
        AnnotationConfigApplicationContext rootContext = new AnnotationConfigApplicationContext();
        rootContext.register(SpringRootConfig.class);
        rootContext.refresh();

        // Load Spring web application configuration
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.setParent(rootContext);
        webContext.setServletContext(servletCxt);
        webContext.register(SpringWebConfig.class);
        webContext.refresh();

        // Create DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(webContext);

        // Register and map the Servlet
        ServletRegistration.Dynamic registration = servletCxt.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }

}