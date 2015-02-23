package com.anjar.smsspamblocker;

import java.util.List;

import android.annotation.SuppressLint;

import com.anjar.smsspamblocker.model.Content;
import com.anjar.smsspamblocker.model.Sender;
import com.anjar.smsspamblocker.service.ServiceContent;
import com.anjar.smsspamblocker.service.ServiceSender;

public class Blocker {
	public static boolean isBlockedBySenderNumber(String sender) {
		if(sender == null) throw new IllegalArgumentException(); 
		
		boolean result = Boolean.FALSE;
		
		List<Sender> listSender = new ServiceSender().getAll();
		for(Sender s : listSender) {
			if(s.getIsEnable() == Boolean.FALSE) continue;
			
			if(s.getNumber().equals(sender)) {
				result = Boolean.TRUE;
				return result;
			}
		}
		
		return result;
	}
	
	@SuppressLint("DefaultLocale")
	public static boolean isBlockedByContent(String messageBody) {
		if(messageBody == null) return false;
		
		boolean result = Boolean.FALSE;
		
		if(messageBody.equals("")) return result;
				
		List<Content> listContent = new ServiceContent().getAll();
		for(Content c : listContent) {
			if(c.getIsEnable() == Boolean.FALSE) continue;
			
			if(c.getType() == Content.CONTENT_POSITION_START) {
				if(messageBody.toLowerCase().matches("^" + c.getContent().toLowerCase() + ".*")){
					result = Boolean.TRUE;
					return result;
				}
			}
			else if(c.getType() == Content.CONTENT_POSITION_CENTER) {
				if(messageBody.toLowerCase().matches("^" + c.getContent().toLowerCase() + ".*")){
					result = Boolean.TRUE;
					return result;
				}
				
				if(messageBody.toLowerCase().matches(".* " + c.getContent().toLowerCase() + " .*")){
					result = Boolean.TRUE;
					return result;
				}
				
				if(messageBody.toLowerCase().matches(".*" + c.getContent().toLowerCase() + "$")){
					result = Boolean.TRUE;
					return result;
				}
			}
			else {
				if(messageBody.toLowerCase().matches(".*" + c.getContent().toLowerCase() + "$")){
					result = Boolean.TRUE;
					return result;
				}
			}
		}

		return result;
	}
}
