import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ch02.beans.Demo_Custom_Init;

public class Test_Demo_Custom_Init {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context=new ClassPathXmlApplicationContext("beans_lifecycle.xml");

		Demo_Custom_Init obj=(Demo_Custom_Init)context.getBean("obj");
        System.out.println(obj);
    
        ((AbstractApplicationContext)context).registerShutdownHook();
	}

}
