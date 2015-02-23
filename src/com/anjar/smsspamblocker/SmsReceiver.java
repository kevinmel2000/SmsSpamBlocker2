package com.anjar.smsspamblocker;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.anjar.smsspamblocker.model.BlockedSms;
import com.anjar.smsspamblocker.service.ServiceBlockedSms;

public class SmsReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		String sender = "";
		String messageBody = "";
		
	    if (bundle != null) {
	        Object[] pdusObject = (Object[]) bundle.get("pdus");
	        SmsMessage[] messages = new SmsMessage[pdusObject.length];

	        for (int x = 0; x < pdusObject.length; x++) {
	            messages[x] = SmsMessage.createFromPdu((byte[]) pdusObject[x]);
	        }

	        for (SmsMessage currentMessage : messages) {
	            sender = currentMessage.getDisplayOriginatingAddress();
	            
	            if(!currentMessage.getDisplayMessageBody().equals(null)) {
	            	messageBody += currentMessage.getDisplayMessageBody();
	            }
	        }
	    }
	    
	    if( Blocker.isBlockedByContent(messageBody) == Boolean.TRUE  || Blocker.isBlockedBySenderNumber(sender) == Boolean.TRUE ) {
	    	abortBroadcast();
	    	
	    	BlockedSms blockedSms = new BlockedSms();
	    	blockedSms.setContent(messageBody);
	    	blockedSms.setIsOpened(Boolean.FALSE);
	    	blockedSms.setNumber(sender);
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	String currentDateandTime = sdf.format(new Date());
	    	blockedSms.setDateTime(currentDateandTime);
	    	
	    	new ServiceBlockedSms().insert(blockedSms);
	    	
	    	if(BlockedSmsFragment.listAdapter != null) {
	    		BlockedSmsFragment.listAdapter.getListBlockedSms().clear();
	    		BlockedSmsFragment.listAdapter.setListBlockedSms(new ServiceBlockedSms().getAll());
	    		BlockedSmsFragment.listAdapter.notifyDataSetChanged();
	    	}
	    }
	}
}
