package com.ch02.beans;

import javax.inject.Named;

@Named
public class Address {
	private String city_name;
	private int build_no;
	private long pin_code;
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public void setBuild_no(int build_no) {
		this.build_no = build_no;
	}
	public void setPin_code(long pin_code) {
		this.pin_code = pin_code;
	}
	
	 @Override
		public String toString() {
			// TODO Auto-generated method stub
			return "\t"+city_name+"\t"+build_no;
		}

	 public Address() {
		// TODO Auto-generated constructor stub
		 city_name="Mumbai";
		 build_no=2;
		 pin_code= 412004l;
	}

}
