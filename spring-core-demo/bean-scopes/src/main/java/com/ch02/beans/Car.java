package com.ch02.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



@Component("car")
@Scope("singleton")
public class Car {
	private String chesis_number, color, fuel_type;
	private long price;
	private double average;

	public void setChesis_number(String chesis_number) {
		this.chesis_number = chesis_number;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setFuel_type(String fuel_type) {
		this.fuel_type = fuel_type;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public void show() {
		System.out.println("showing car " + chesis_number + " having color:-"
				+ color + " and price:-" + price);
	}

	public Car(String chesis_number,String color,double average, long price, String fuel_type) {
		// TODO Auto-generated constructor stub
		this.chesis_number = chesis_number;
		this.average = average;
		this.price = price;
		this.color=color;
		this.fuel_type=fuel_type;
	}


	 public Car() {
			// TODO Auto-generated constructor stub
	    	chesis_number="eng00";
	    	color="white";
	    	fuel_type="diesel";
	    	price=570000l;
	    	average=12d;
		}
}
