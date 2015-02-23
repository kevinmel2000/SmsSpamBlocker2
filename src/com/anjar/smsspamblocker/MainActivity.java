package com.anjar.smsspamblocker;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.anjar.smsspamblocker.dao.DaoBlockedSms;
import com.anjar.smsspamblocker.dao.DaoContent;
import com.anjar.smsspamblocker.dao.DaoSender;
import com.anjar.smsspamblocker.util.DbHelper;

@SuppressLint("DefaultLocale")
public class MainActivity extends FragmentActivity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ViewPager viewPager = (ViewPager) findViewById(R.id.main_pager);
		viewPager.setAdapter(new MainFragmentPagerAdapter(this));

		initDb();
	}
	
	private void initDb() {
		DbHelper dbHelper = new DbHelper(MainActivity.this, DbHelper.DB_NAME, null, 1);

		try {
			dbHelper.copyDatabase(MainActivity.this, false);
		} catch (IOException e) {
			e.printStackTrace();
		}

		DbHelper.getDatabase().execSQL(DaoContent.DML);
		DbHelper.getDatabase().execSQL(DaoBlockedSms.DML);
		DbHelper.getDatabase().execSQL(DaoSender.DML);
	}
}
