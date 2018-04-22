package com.ch02.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Car_JSR {
	private String chassis_number, color, fuel_type;
    private long price;
    private double average;

    public String getChassis_number() {
		return chassis_number;
	}

	public void setChassis_number(String chassis_number) {
		this.chassis_number = chassis_number;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFuel_type() {
		return fuel_type;
	}

	public void setFuel_type(String fuel_type) {
		this.fuel_type = fuel_type;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public void show()
    {
    	System.out.println("showing car "+chassis_number+" having color:-"+color+"and price:-"+price);
    }
    
    @PostConstruct
    public void init_car()
    {
    	System.out.println("initializing the car");
    	price=(long)(price+(price*0.10));
    }
    
    @PreDestroy
    public void destroy_car()
    {
    	System.out.println("demolishing the car");
    }
}
