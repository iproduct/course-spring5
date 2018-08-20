package org.iproduct.spring.webmvc.init;

import org.iproduct.spring.webmvc.config.SpringRootConfig;
import org.iproduct.spring.webmvc.config.SpringWebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class AricleWebInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //Create root context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringRootConfig.class);
        rootContext.refresh();

        //Create web context
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.setParent(rootContext);
        webContext.setServletContext(servletContext);
        webContext.register(SpringWebConfig.class);
        webContext.refresh();

        //Create and map DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(webContext);

        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");

    }
}
