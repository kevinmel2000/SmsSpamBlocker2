package com.anjar.smsspamblocker.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;

import com.anjar.smsspamblocker.model.Content;
import com.anjar.smsspamblocker.util.DbHelper;


public class DaoContent { 
	private String mTableName = "content";
	private String[] mColumns = new String[] {"id", "content", "type", "is_enable"};

	public static String DML = "CREATE TABLE IF NOT EXISTS 'content' ('id' INTEGER PRIMARY KEY  NOT NULL, 'content' TEXT, 'type' INTEGER, 'is_enable' BOOL)";


	public Content insert(Content content) {
		if(content == null) return content;

		ContentValues values = new ContentValues();
		values = this.objectToContentValue(content);

		Long dbId = DbHelper.getDatabase().insert(this.mTableName, null, values);
		if(dbId < 0) return content;

		content.setId(Integer.valueOf(dbId.toString()));

		return content;
	}

	public Boolean update(Content content, Integer id) {
		if(content == null || id == null) return false;

		ContentValues values = new ContentValues();
		values = this.objectToContentValue(content);

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

	public Content getById(Integer id) {
		Cursor cr = DbHelper.getDatabase().query(this.mTableName,
			this.mColumns ,
			"id = ?" ,
			new String[] {id.toString()},
			null,
			null,
			null);

		if (cr.getCount() == 0) return null;

		cr.moveToFirst();
		Content result = this.cursorToObject(cr);
		cr.close();

		return result;
	}

	public ArrayList<Content> getAll() {
		ArrayList<Content> result = new ArrayList<Content>();
		Cursor cr = DbHelper.getDatabase().query(this.mTableName,
			this.mColumns ,
			null,
			null,
			null,
			null,
			"id DESC");

		if (cr.getCount() == 0) return result;

		cr.moveToFirst();
		Content model;

		do {
			model = cursorToObject(cr);
			result.add(model);
		} while(cr.moveToNext());
		cr.close();

		return result;
	}

	private Content cursorToObject(Cursor cr) {
		Content result = new Content();

		result.setId(DbHelper.getInteger(cr, "id"));
		result.setContent(DbHelper.getString(cr, "content"));
		result.setType(DbHelper.getInteger(cr, "type"));
		result.setIsEnable(DbHelper.getBoolean(cr, "is_enable"));

		return result;
	}

	private ContentValues objectToContentValue(Content content) {
		ContentValues result = new ContentValues();

		if(content.getContent() != null)
			result.put("content", content.getContent());
		if(content.getType() != null)
			result.put("type", content.getType());
		if(content.getIsEnable() != null)
			result.put("is_enable", content.getIsEnable());

		return result;
	}

}

