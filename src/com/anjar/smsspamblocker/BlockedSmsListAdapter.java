package com.anjar.smsspamblocker;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.anjar.smsspamblocker.model.BlockedSms;


public class BlockedSmsListAdapter<T> extends ArrayAdapter<BlockedSms> {
	private Activity context;
	private ArrayList<BlockedSms> listBlockedSms;

	public BlockedSmsListAdapter(Activity context, ArrayList<BlockedSms> listBlockedSms) {
		super(context, R.layout.inflated_row_blocked_sms, listBlockedSms);

		this.context = context;
		this.listBlockedSms = new ArrayList<BlockedSms>();

		if(listBlockedSms != null) {
			this.listBlockedSms = listBlockedSms;
		}
	}

	public ArrayList<BlockedSms> getListBlockedSms() {
		return listBlockedSms;
	}

	public void setListBlockedSms(ArrayList<BlockedSms> listBlockedSms) {
		this.listBlockedSms= listBlockedSms;
	}

	@Override
	public int getCount() {
		return listBlockedSms.size();
	}

	@Override
	public BlockedSms getItem(int position) {
		return listBlockedSms.get(position);
	}

	@Override
	public long getItemId(int position) {
		return super.getItemId(position);
	}

	@Override
	public void remove(BlockedSms blockedSms) {
		listBlockedSms.remove(blockedSms);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = View.inflate(context, R.layout.inflated_row_blocked_sms, null);
		}

		TextView textView1 = (TextView) convertView.findViewById(R.id.inflated_textView_text1);
		TextView textView2 = (TextView) convertView.findViewById(R.id.inflated_textView_text2);
		TextView textView3 = (TextView) convertView.findViewById(R.id.inflated_textView_text3);
		TextView textView4 = (TextView) convertView.findViewById(R.id.inflated_textView_text4);
		
		BlockedSms blockedSms = listBlockedSms.get(position);
		
		if(blockedSms.getNumber() != null)
			textView1.setText(blockedSms.getNumber());
		
		textView2.setVisibility(View.GONE);
		
		if(blockedSms.getContent() != null)
			textView3.setText("Message:\n" + blockedSms.getContent());
		
		if(blockedSms.getDateTime() != null)
			textView4.setText(blockedSms.getDateTime());

		return convertView;
	}

}

