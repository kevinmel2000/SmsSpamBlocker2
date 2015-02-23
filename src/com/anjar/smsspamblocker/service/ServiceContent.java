package com.anjar.smsspamblocker.service;

import java.util.ArrayList;

import com.anjar.smsspamblocker.dao.DaoContent;
import com.anjar.smsspamblocker.model.Content;


public class ServiceContent { 
	private static DaoContent dao = new DaoContent();

	public Content insert(Content content) {
		return dao.insert(content);
	}

	public Boolean delete(Integer id) {
		return dao.delete(id);
	}

	public Content getById(Integer id) {
		return dao.getById(id);
	}

	public ArrayList<Content> getAll() {
		return dao.getAll();
	}

	public Boolean update(Content content, Integer id) {
		return dao.update(content, id);
	}

}

