package course.spring.springcoredemo;

import course.spring.springcoredemo.domain.ArticlePresenter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class BeanFactoryDemo {
    public static void main(String[] args) {
//        BeanFactory beanFactory = new XmlBeanFactory(
//                new ClassPathResource("app-config.xml"));
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader =
                new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(new ClassPathResource("app-config.xml"));
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(beanPostProcessor);
        beanFactory.getBean("presenter", ArticlePresenter.class)
                .present();

    }
}
