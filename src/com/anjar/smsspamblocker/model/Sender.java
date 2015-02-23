package com.anjar.smsspamblocker.model;




public class Sender { 

	private Integer id;
	private String number;
	private String name;
	private Boolean isEnable;

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return this.id;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getNumber() {
		return this.number;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	public Boolean getIsEnable() {
		return this.isEnable;
	}
}
