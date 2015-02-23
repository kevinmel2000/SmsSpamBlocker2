package com.anjar.smsspamblocker.model;

public class Content { 
	public static final int CONTENT_POSITION_START = 0;
	public static final int CONTENT_POSITION_CENTER = 1;
	public static final int CONTENT_POSITION_END = 2;
	
	private Integer id;
	private String content;
	private Integer type;
	private Boolean isEnable;

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return this.id;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return this.content;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getType() {
		return this.type;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	public Boolean getIsEnable() {
		return this.isEnable;
	}
}
