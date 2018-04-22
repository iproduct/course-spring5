import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ch02.beans.Customer;
import com.ch02.jsr.annot.MyConfiguration;

public class TestConfiguration {
	public static void main(String[] args) {
		ApplicationContext context=new AnnotationConfigApplicationContext(MyConfiguration.class);
		Customer customer=(Customer)context.getBean("myCustomer");
		System.out.println(customer.getCust_id()+"\t"+customer.getCust_name());
	}

}
