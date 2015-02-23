package com.anjar.smsspamblocker.model;


public class BlockedSms { 

	private Integer id;
	private String number;
	private String content;
	private String dateTime;
	private Integer blockedBy;
	private Boolean isOpened;

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
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return this.content;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getDateTime() {
		return this.dateTime;
	}
	public void setBlockedBy(Integer blockedBy) {
		this.blockedBy = blockedBy;
	}
	public Integer getBlockedBy() {
		return this.blockedBy;
	}
	public void setIsOpened(Boolean isOpened) {
		this.isOpened = isOpened;
	}
	public Boolean getIsOpened() {
		return this.isOpened;
	}
}
