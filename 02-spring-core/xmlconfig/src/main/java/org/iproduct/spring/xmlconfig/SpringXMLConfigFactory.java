package org.iproduct.spring.xmlconfig;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

public class SpringXMLConfigFactory {
    public static void main(String[] args) {
        BeanFactory beanFactory=new XmlBeanFactory(new ClassPathResource("spring/app-context.xml"));
//        BeanFactory beanFactory1=new XmlBeanFactory(new FileSystemResource("xmlconfig/src/main/resources/spring/app-context.xml"));
//        DefaultListableBeanFactory beanFactory= new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
//        beanDefinitionReader.loadBeanDefinitions(new ClassPathResource("spring/app-context.xml"));
        beanDefinitionReader.loadBeanDefinitions("spring/app-context.xml");

        DefaultListableBeanFactory beanFactory1= new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader1 = new XmlBeanDefinitionReader(beanFactory1);
        beanDefinitionReader1.loadBeanDefinitions(
                new FileSystemResource("xmlconfig/src/main/resources/spring/app-context.xml"));

        AutowiredAnnotationBeanPostProcessor postProcessor = new AutowiredAnnotationBeanPostProcessor();
        postProcessor.setBeanFactory(beanFactory1);
        beanFactory1.addBeanPostProcessor(postProcessor);

        System.out.println("beanfactory created successfully");
        ArticlePresenter presenter = beanFactory1.getBean("presenter", ArticlePresenter.class);
        presenter.present();
    }
}
