import org.springframework.context.ApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ch02.beans.Car_JSR;


public class TestCar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context=new ClassPathXmlApplicationContext("beans_new.xml");
		// first request to get the car instance
		Car_JSR car_one=(Car_JSR)context.getBean("car");
        car_one.show();
	}

	
}
