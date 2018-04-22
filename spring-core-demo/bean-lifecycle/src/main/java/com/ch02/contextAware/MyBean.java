package com.ch02.contextAware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ch02.beans.Demo_InitializingBean;

public class MyBean implements ApplicationContextAware {

	
	private ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		// TODO Auto-generated method stub
		System.out.println("context set");
		this.context=ctx;

	}
	
	public void display()
	{
    	System.out.println((Demo_InitializingBean)context.getBean("obj_Initializing"));
	}
}
