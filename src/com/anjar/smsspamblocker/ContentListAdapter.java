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

import com.anjar.smsspamblocker.model.Content;
import com.anjar.smsspamblocker.service.ServiceContent;


public class ContentListAdapter<T> extends ArrayAdapter<Content> {
	private Activity context;
	private ArrayList<Content> listContent;

	public ContentListAdapter(Activity context, ArrayList<Content> listContent) {
		super(context, R.layout.inflated_row_list1, listContent);

		this.context = context;
		this.listContent = new ArrayList<Content>();

		if(listContent != null) {
			this.listContent = listContent;
		}
	}

	public ArrayList<Content> getListContent() {
		return listContent;
	}

	public void setListContent(ArrayList<Content> listContent) {
		this.listContent= listContent;
	}

	@Override
	public int getCount() {
		return listContent.size();
	}

	@Override
	public Content getItem(int position) {
		return listContent.get(position);
	}

	@Override
	public long getItemId(int position) {
		return super.getItemId(position);
	}

	@Override
	public void remove(Content content) {
		listContent.remove(content);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = View.inflate(context, R.layout.inflated_row_list1, null);
		}

		TextView textView1 = (TextView) convertView.findViewById(R.id.inflated_textView_text1);
		TextView textView2 = (TextView) convertView.findViewById(R.id.inflated_textView_text2);
		CheckBox checBoxIsEnable = (CheckBox) convertView.findViewById(R.id.inflated_chexbox_isEnable);
		
		Content content = listContent.get(position);

		textView1.setText(content.getContent());
		
		if(content.getType() == Content.CONTENT_POSITION_START) {
			textView2.setText("Begin with");
		}
		else if(content.getType() == Content.CONTENT_POSITION_END) {
			textView2.setText("End with");
		}
		else {
			textView2.setText("Anywhere");
		}
		
		checBoxIsEnable.setTag(content);
		checBoxIsEnable.setOnCheckedChangeListener(listener);
		checBoxIsEnable.setChecked(content.getIsEnable());
		
		return convertView;
	}
	
	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {
	     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {   
	    	 Content content = listContent.get(listContent.indexOf(buttonView.getTag()));
	    	 content.setIsEnable(isChecked);
	    	 
	    	 new ServiceContent().update(content, content.getId());
	     }
	};

}

