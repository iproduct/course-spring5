package com.ch02.jsr.annot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.ch02.beans.Customer;

@Configuration
@ComponentScan(basePackages="com.ch02.beans")
public class MyConfiguration {
	
	@Bean(name="myCustomer")
	public Customer myCustomer()
	{
		Customer customer=new Customer();
		customer.setCust_name("name by config");
		return customer;
	}

}
