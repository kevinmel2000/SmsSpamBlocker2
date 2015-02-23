package com.anjar.smsspamblocker.service;

import java.util.ArrayList;

import com.anjar.smsspamblocker.dao.DaoBlockedSms;
import com.anjar.smsspamblocker.model.BlockedSms;


public class ServiceBlockedSms { 
	private static DaoBlockedSms dao = new DaoBlockedSms();

	public BlockedSms insert(BlockedSms blockedSms) {
		return dao.insert(blockedSms);
	}

	public Boolean delete(Integer id) {
		return dao.delete(id);
	}

	public BlockedSms getById(Integer id) {
		return dao.getById(id);
	}

	public ArrayList<BlockedSms> getAll() {
		return dao.getAll();
	}

	public Boolean update(BlockedSms blockedSms, Integer id) {
		return dao.update(blockedSms, id);
	}

}

