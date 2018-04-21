import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ch02.contextAware.MyBean;;

public class Test_MyBean {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context=new ClassPathXmlApplicationContext("beans_lifecycle.xml");

		MyBean obj=(MyBean)context.getBean("obj_myBean");
        obj.display();
		
	}

}
