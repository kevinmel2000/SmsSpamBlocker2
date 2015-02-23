package com.anjar.smsspamblocker.service;

import java.util.ArrayList;

import com.anjar.smsspamblocker.dao.DaoSender;
import com.anjar.smsspamblocker.model.Sender;


public class ServiceSender { 
	private static DaoSender dao = new DaoSender();

	public Sender insert(Sender sender) {
		return dao.insert(sender);
	}

	public Boolean delete(Integer id) {
		return dao.delete(id);
	}

	public Sender getById(Integer id) {
		return dao.getById(id);
	}

	public ArrayList<Sender> getAll() {
		return dao.getAll();
	}

	public Boolean update(Sender sender, Integer id) {
		return dao.update(sender, id);
	}

}

