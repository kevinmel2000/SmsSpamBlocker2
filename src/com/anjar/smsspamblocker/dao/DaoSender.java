package com.anjar.smsspamblocker.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;

import com.anjar.smsspamblocker.model.Sender;
import com.anjar.smsspamblocker.util.DbHelper;


public class DaoSender { 
	private String mTableName = "sender";
	private String[] mColumns = new String[] {"id", "number", "name", "is_enable"};

	public static String DML = "CREATE TABLE IF NOT EXISTS 'sender' ('id' INTEGER PRIMARY KEY  NOT NULL, 'number' TEXT, 'name' TEXT, 'is_enable' BOOL)";


	public Sender insert(Sender sender) {
		if(sender == null) return sender;

		ContentValues values = new ContentValues();
		values = this.objectToContentValue(sender);

		Long dbId = DbHelper.getDatabase().insert(this.mTableName, null, values);
		if(dbId < 0) return sender;

		sender.setId(Integer.valueOf(dbId.toString()));

		return sender;
	}

	public Boolean update(Sender sender, Integer id) {
		if(sender == null || id == null) return false;

		ContentValues values = new ContentValues();
		values = this.objectToContentValue(sender);

		int rowUpdated = DbHelper.getDatabase().update(this.mTableName,
			values,
			"id = ?",
			new String[] {id.toString()});

		if (rowUpdated < 0) return false;

		return true;
	}

	public Boolean delete(Integer id) {
		int rowDeleted = DbHelper.getDatabase().delete(this.mTableName,
			"id = ?",
			new String[] {id.toString()});

		if (rowDeleted < 0) return false;
		return true;
	}

	public Sender getById(Integer id) {
		Cursor cr = DbHelper.getDatabase().query(this.mTableName,
			this.mColumns ,
			"id = ?" ,
			new String[] {id.toString()},
			null,
			null,
			null);

		if (cr.getCount() == 0) return null;

		cr.moveToFirst();
		Sender result = this.cursorToObject(cr);
		cr.close();

		return result;
	}

	public ArrayList<Sender> getAll() {
		ArrayList<Sender> result = new ArrayList<Sender>();
		Cursor cr = DbHelper.getDatabase().query(this.mTableName,
			this.mColumns ,
			null,
			null,
			null,
			null,
			"id DESC");

		if (cr.getCount() == 0) return result;
		cr.moveToFirst();
		
		Sender model;

		do {
			model = cursorToObject(cr);
			result.add(model);
		} while(cr.moveToNext());
		cr.close();

		return result;
	}

	private Sender cursorToObject(Cursor cr) {
		Sender result = new Sender();

		result.setId(DbHelper.getInteger(cr, "id"));
		result.setNumber(DbHelper.getString(cr, "number"));
		result.setName(DbHelper.getString(cr, "name"));
		result.setIsEnable(DbHelper.getBoolean(cr, "is_enable"));

		return result;
	}

	private ContentValues objectToContentValue(Sender sender) {
		ContentValues result = new ContentValues();

		if(sender.getNumber() != null)
			result.put("number", sender.getNumber());
		if(sender.getName() != null)
			result.put("name", sender.getName());
		if(sender.getIsEnable() != null)
			result.put("is_enable", sender.getIsEnable());

		return result;
	}

}

