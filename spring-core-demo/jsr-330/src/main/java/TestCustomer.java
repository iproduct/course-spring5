import org.springframework.context.ApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ch02.beans.Customer;

public class TestCustomer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext("customer.xml");
		Customer customer = (Customer) context.getBean("customer");
		System.out.println(customer.getCust_name() + "\t" + customer.getCust_id() + 
				customer.getCust_address());

	}

}
