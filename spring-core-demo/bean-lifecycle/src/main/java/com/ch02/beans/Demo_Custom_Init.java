package com.ch02.beans;

public class Demo_Custom_Init {
	private String message;
	private String name;

	public Demo_Custom_Init() {
		// TODO Auto-generated constructor stub
		System.out.println("constructor gets called for initializing data members in Custom init");
		message="welcome!!!";
		name="no name";
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return message+"\t"+name;
	}
	
	public void myInit()
	{
		name=name.toUpperCase();
		System.out.println("myInit() get called");
	}
	
	public void destroy()
	{
		name=null;
		System.out.println("destroy called");
	}
}
