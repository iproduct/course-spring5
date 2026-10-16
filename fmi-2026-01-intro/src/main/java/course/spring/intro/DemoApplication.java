package course.spring.intro;

import course.spring.intro.service.ArticleService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		BeanDefinition beanDefinition = new RootBeanDefinition(ArticleService.class);
	}

}
