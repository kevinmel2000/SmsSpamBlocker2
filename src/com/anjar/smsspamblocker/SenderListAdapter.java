package com.anjar.smsspamblocker;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.anjar.smsspamblocker.model.Sender;
import com.anjar.smsspamblocker.service.ServiceSender;


public class SenderListAdapter<T> extends ArrayAdapter<Sender> {
	private Activity context;
	private ArrayList<Sender> listSender;

	public SenderListAdapter(Activity context, ArrayList<Sender> listSender) {
		super(context, R.layout.inflated_row_list1, listSender);

		this.context = context;
		this.listSender = new ArrayList<Sender>();

		if(listSender != null) {
			this.listSender = listSender;
		}
	}

	public ArrayList<Sender> getListSender() {
		return listSender;
	}

	public void setListSender(ArrayList<Sender> listSender) {
		this.listSender= listSender;
	}

	@Override
	public int getCount() {
		return listSender.size();
	}

	@Override
	public Sender getItem(int position) {
		return listSender.get(position);
	}

	@Override
	public long getItemId(int position) {
		return super.getItemId(position);
	}

	@Override
	public void remove(Sender sender) {
		listSender.remove(sender);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = View.inflate(context, R.layout.inflated_row_list1, null);
		}

		TextView textView1 = (TextView) convertView.findViewById(R.id.inflated_textView_text1);
		TextView textView2 = (TextView) convertView.findViewById(R.id.inflated_textView_text2);
		CheckBox checBoxIsEnable = (CheckBox) convertView.findViewById(R.id.inflated_chexbox_isEnable);
		
		Sender sender = listSender.get(position);

		textView1.setText(sender.getNumber());
		textView2.setVisibility(View.GONE);
		checBoxIsEnable.setTag(sender);
		checBoxIsEnable.setOnCheckedChangeListener(listener);
		checBoxIsEnable.setChecked(sender.getIsEnable());
		
		return convertView;
	}
	
	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {
	     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {   
	    	 Sender sender = listSender.get(listSender.indexOf(buttonView.getTag()));
	    	 sender.setIsEnable(isChecked);
	    	 
	    	 new ServiceSender().update(sender, sender.getId());
	     }
	};

}

