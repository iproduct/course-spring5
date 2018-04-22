package com.ch02.beans;

import org.springframework.beans.factory.DisposableBean;

public class Demo_DisposableBean implements DisposableBean {

	private String message;
	private String name;

	public Demo_DisposableBean() {
		// TODO Auto-generated constructor stub
		System.out.println("constructor gets called for initializing data members in Disposable Bean");
		message="welcome!!!";
		name="no name";
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return message+"\t"+name;
	}

	
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("destroy from disposable bean get called");
		name=null;

	}

}
