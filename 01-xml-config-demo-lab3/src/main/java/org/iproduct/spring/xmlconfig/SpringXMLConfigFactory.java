package org.iproduct.spring.xmlconfig;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

public class SpringXMLConfigFactory {
    public static void main(String[] args) {
//        BeanFactory beanFactory=new XmlBeanFactory(new ClassPathResource("app-context.xml"));
//        BeanFactory beanFactory1=new XmlBeanFactory(new FileSystemResource("src/main/resources/app-context.xml"));
        DefaultListableBeanFactory beanFactory= new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions(new ClassPathResource("app-context.xml"));
////        beanDefinitionReader.loadBeanDefinitions("spring/app-context.xml");
////
//        DefaultListableBeanFactory beanFactory1= new DefaultListableBeanFactory();
//        XmlBeanDefinitionReader beanDefinitionReader1 = new XmlBeanDefinitionReader(beanFactory1);
//        beanDefinitionReader1.loadBeanDefinitions(
//                new FileSystemResource("xmlconfig/src/main/resources/spring/app-context.xml"));
//
        AutowiredAnnotationBeanPostProcessor postProcessor = new AutowiredAnnotationBeanPostProcessor();
        postProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(postProcessor);
//
        System.out.println("beanfactory created successfully");
        ArticlePresenter presenter = beanFactory.getBean("presenter", ArticlePresenter.class);
        presenter.present();
    }
}
