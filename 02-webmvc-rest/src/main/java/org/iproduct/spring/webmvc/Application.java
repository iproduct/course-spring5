package org.iproduct.spring.webmvc;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.Server;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.TomcatHttpHandlerAdapter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@Slf4j
public class Application {

//    @Value("${server.port:8080}")
    private int port = 8080;

    public static void main(String[] args) throws Exception {
        Application app = new Application();
        app.start();
    }

    public void start() throws Exception {
        String tmpDir = createTempDir();
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(tmpDir);
        tomcat.setPort(port);
        tomcat.getHost().setAppBase(tmpDir);
        tomcat.addWebapp("", tmpDir);
        tomcat.start();
        Connector connector = tomcat.getConnector();
        log.info("Server started: {}", connector);
        System.out.println("Press ENTER to exit.");
        System.in.read();
        tomcat.stop();
    }


//    String webAppDirLocation = "src/main/";
//    Tomcat tomcat = new Tomcat();
//
//    //Set Port #
//        tomcat.setPort(8080);
//
//    StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webAppDirLocation).getAbsolutePath());
//
//    AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
//        webContext.setServletContext(ctx.getServletContext());
//        webContext.register(Application .class);
//        webContext.refresh();

//        HttpHandler handler = WebHttpHandlerBuilder.applicationContext(webContext).build();
//        // Tomcat and Jetty (also see notes below)
//        Servlet servlet = new TomcatHttpHandlerAdapter(handler);
//
//        tomcat.start();
//        tomcat.getServer().
//
    // Load root application configuration
//        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
//        webContext.setServletContext(servletCxt);
//        webContext.register(Application.class);
//        webContext.refresh();

//
//        // Load Spring web application configuration
//        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
//        webContext.setParent(rootContext);
//        webContext.setServletContext(servletCxt);
//        webContext.register(SpringWebConfig.class);
//        webContext.refresh();
//
//        // Create DispatcherServlet
//        DispatcherServlet servlet = new DispatcherServlet(webContext);
//
//        // Register and map the Servlet
//        ServletRegistration.Dynamic registration = servletCxt.addServlet("app", servlet);
//        registration.setLoadOnStartup(1);
//        registration.addMapping("/");
//        Tomcat tomcatServer = webContext.getBean(Tomcat.class);
//        tomcatServer.start();
//
//        System.out.println("Press ENTER to exit.");
//        System.in.read();
//}

    // based on AbstractEmbeddedServletContainerFactory
    private String createTempDir() {
        try {
            File tempDir = File.createTempFile("tomcat.", "." + port);
            tempDir.delete();
            tempDir.mkdir();
            tempDir.deleteOnExit();
            return tempDir.getAbsolutePath();
        } catch (IOException ex) {
            throw new RuntimeException(
                    "Unable to create tempDir. java.io.tmpdir is set to " + System.getProperty("java.io.tmpdir"), ex);
        }
    }

//    @Bean
//    public Tomcat embededTomcatServer(ApplicationContext context) throws Exception {
//        HttpHandler handler = WebHttpHandlerBuilder.applicationContext(context).build();
//
//        // Tomcat and Jetty (also see notes below)
//        Servlet servlet = new TomcatHttpHandlerAdapter(handler);
//
//        Tomcat tomcatServer = new Tomcat();
//        tomcatServer.setHostname("localhost");
//        tomcatServer.setPort(this.port);
//        Context rootContext = tomcatServer.addContext("", System.getProperty("java.io.tmpdir"));
//        Tomcat.addServlet(rootContext, "httpHandlerServlet", servlet);
//        rootContext.addServletMappingDecoded("/", "httpHandlerServlet");
////        context.setServletContext(rootContext.getServletContext());
//
//        return tomcatServer;
//    }

}