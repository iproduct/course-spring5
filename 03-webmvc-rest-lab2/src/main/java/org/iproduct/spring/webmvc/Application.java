package org.iproduct.spring.webmvc;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;

@Slf4j
public class Application {

    private static final int port = 9000;

    public static void main(String[] args) throws Exception{
        String tmpDir = createTempDir();
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(tmpDir);
        tomcat.setPort(port);
        tomcat.getHost().setAppBase(tmpDir);
        tomcat.addWebapp("",tmpDir);
        tomcat.start();
        Connector connector = tomcat.getConnector();
        log.info("Server started: {}", connector);
        System.out.println("Press ENTER to exit ...");
        System.in.read();
        tomcat.stop();
    }

    private static String createTempDir() {
        try {
            File tempDir = File.createTempFile("tomcat.","." + port);
            tempDir.delete();
            tempDir.mkdir();
            tempDir.deleteOnExit();
            return tempDir.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Unable to create temp dir in java.io.tmpdir: "
                    + System.getProperty("java.io.tmpdir"), e);
        }

    }


}
