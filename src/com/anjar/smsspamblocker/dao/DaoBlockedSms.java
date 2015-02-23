package com.anjar.smsspamblocker.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;

import com.anjar.smsspamblocker.model.BlockedSms;
import com.anjar.smsspamblocker.util.DbHelper;


public class DaoBlockedSms { 
	private String mTableName = "blocked_sms";
	private String[] mColumns = new String[] {"id", "number", "content", "date_time", "blocked_by", "is_opened"};

	public static String DML = "CREATE TABLE IF NOT EXISTS 'blocked_sms' ('id' INTEGER PRIMARY KEY  NOT NULL, 'number' TEXT, 'content' TEXT, 'date_time' TEXT, 'blocked_by' INTEGER, 'is_opened' BOOL)";


	public BlockedSms insert(BlockedSms blockedSms) {
		if(blockedSms == null) return blockedSms;

		ContentValues values = new ContentValues();
		values = this.objectToContentValue(blockedSms);

		Long dbId = DbHelper.getDatabase().insert(this.mTableName, null, values);
		if(dbId < 0) return blockedSms;

		blockedSms.setId(Integer.valueOf(dbId.toString()));
		
		String sqlKeepNData = "delete from " + mTableName + " where id not in (select id from " + mTableName + " order by id desc limit 20)";
		DbHelper.getDatabase().execSQL(sqlKeepNData);
		
		return blockedSms;
	}

	public Boolean update(BlockedSms blockedSms, Integer id) {
		if(blockedSms == null || id == null) return false;

		ContentValues values = new ContentValues();
		values = this.objectToContentValue(blockedSms);

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

	public BlockedSms getById(Integer id) {
		Cursor cr = DbHelper.getDatabase().query(this.mTableName,
			this.mColumns ,
			"id = ?" ,
			new String[] {id.toString()},
			null,
			null,
			null);

		if (cr.getCount() == 0) return null;

		cr.moveToFirst();
		BlockedSms result = this.cursorToObject(cr);
		cr.close();

		return result;
	}

	public ArrayList<BlockedSms> getAll() {
		ArrayList<BlockedSms> result = new ArrayList<BlockedSms>();
		
		Cursor cr = DbHelper.getDatabase().query(this.mTableName,
			this.mColumns ,
			null,
			null,
			null,
			null,
			"id DESC");

		if (cr.getCount() == 0) return result;

		cr.moveToFirst();
		
		BlockedSms model;

		do {
			model = cursorToObject(cr);
			result.add(model);
		} while(cr.moveToNext());
		cr.close();

		return result;
	}

	private BlockedSms cursorToObject(Cursor cr) {
		BlockedSms result = new BlockedSms();

		result.setId(DbHelper.getInteger(cr, "id"));
		result.setNumber(DbHelper.getString(cr, "number"));
		result.setContent(DbHelper.getString(cr, "content"));
		result.setDateTime(DbHelper.getString(cr, "date_time"));
		result.setBlockedBy(DbHelper.getInteger(cr, "blocked_by"));
		result.setIsOpened(DbHelper.getBoolean(cr, "is_opened"));

		return result;
	}

	private ContentValues objectToContentValue(BlockedSms blockedSms) {
		ContentValues result = new ContentValues();

		if(blockedSms.getNumber() != null)
			result.put("number", blockedSms.getNumber());
		if(blockedSms.getContent() != null)
			result.put("content", blockedSms.getContent());
		if(blockedSms.getDateTime() != null)
			result.put("date_time", blockedSms.getDateTime());
		if(blockedSms.getBlockedBy() != null)
			result.put("blocked_by", blockedSms.getBlockedBy());
		if(blockedSms.getIsOpened() != null)
			result.put("is_opened", blockedSms.getIsOpened());

		return result;
	}

}

