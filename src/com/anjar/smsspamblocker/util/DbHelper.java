package com.anjar.smsspamblocker.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	public static final String DB_PATH = "/data/data/com.anjar.smsspamblocker/databases/";
	public static final String DB_NAME = "db.sqlite";

	private static SQLiteDatabase dbSqlite;
	
	public DbHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) { }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

	public static void closeDatabase() {
		dbSqlite.close();
		dbSqlite = null;
	}

	public static SQLiteDatabase getDatabase() {
		if (dbSqlite == null) {
			if (checkDatabase()) {
				dbSqlite = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null,
						SQLiteDatabase.OPEN_READWRITE);
			}
		}

		return dbSqlite;
	}

	public static Boolean checkDatabase() {
		File dbFile = new File(DB_PATH + DB_NAME);
		return dbFile.exists();
	}

	public void copyDatabase(Context context, Boolean forceCopy) throws IOException {
		if(forceCopy == true) {
			copyDatabase(context);
		}
		else {
			if (checkDatabase() == false) {
				copyDatabase(context);
			}
		}
	}

	private void copyDatabase(Context context) throws IOException {
		this.getWritableDatabase();

		String myPath = DB_PATH + DB_NAME;

		OutputStream os = new FileOutputStream(myPath);

		InputStream is;
		byte[] buffer = new byte[1024];
		int length;
		is = context.getAssets().open(DB_NAME);
		while ((length = is.read(buffer)) > 0) {
			os.write(buffer, 0, length);
		}

		is.close();

		os.flush();
		os.close();
	}
	
	
	public static String getString(Cursor cursor, String columnName) {
		return cursor.getString(cursor.getColumnIndex(columnName));
	}

	public static Integer getInteger(Cursor cursor, String columnName) {
		return cursor.getInt(cursor.getColumnIndex(columnName));
	}

	public static Boolean getBoolean(Cursor cursor, String columnName) {
		Boolean result = false;
		String dbResult = getString(cursor, columnName);
		
		if (dbResult.equals("1")) { 
			result =  true;
		}

		return result;
	}
}
