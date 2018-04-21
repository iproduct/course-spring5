import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ch02.beans.Demo_Custom_Init;
import com.ch02.processor.Demo_BeanPostProcessor;

public class Test_BeanPostProcessor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context=new ClassPathXmlApplicationContext("beans_lifecycle.xml");

		Demo_BeanPostProcessor obj=(Demo_BeanPostProcessor)context.getBean("beanPostProcessor");
        System.out.println(obj);
    
	}
}
