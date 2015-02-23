package com.anjar.smsspamblocker;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

public class SmsUtils {
	public static void saveToInbox(Context context, String senderNumber, String messageBody) {
		ContentValues values = new ContentValues();
		values.put("address", senderNumber);
		values.put("body", messageBody);
		
		context.getContentResolver().insert(Uri.parse("content://sms/inbox"), values);
	}
}
