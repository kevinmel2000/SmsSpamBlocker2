package com.anjar.smsspamblocker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.anjar.smsspamblocker.model.Content;
import com.anjar.smsspamblocker.service.ServiceContent;

public class ContentFragment extends Fragment{
	private ListView listContent;
	private Button buttonAdd;
	private Activity activity;
	private View rootView;
	private ContentListAdapter<Content> listAdapter;
	
	public static final int POPUP_CODE_EDIT = 301;
	public static final int POPUP_CODE_DELETE = 302;

	public static final int REQUEST_CODE_SAVE = 101;
	public static final int REQUEST_CODE_EDIT = 102;
	
	public static final int RESULT_REQUEST_REFRESH = 201;;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_content, container, false);

		initWidget();
		
		return rootView;
	}

	private void initWidget() {
		listContent = (ListView) rootView.findViewById(R.id.content_listView_content);
		
		listAdapter = new ContentListAdapter<Content>(activity, new ServiceContent().getAll());
		listContent.setAdapter(listAdapter);
		registerForContextMenu(listContent);
		
		listContent.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CheckBox isEnable = (CheckBox) view.findViewById(R.id.inflated_chexbox_isEnable);
				if(isEnable.isChecked()) {
					((Content)listContent.getItemAtPosition(position)).setIsEnable(Boolean.FALSE);
				}
				else {
					((Content)listContent.getItemAtPosition(position)).setIsEnable(Boolean.TRUE);
				}
				
				listAdapter.notifyDataSetChanged();
			}
		});
		
		buttonAdd = (Button) rootView.findViewById(R.id.content_button_addSpamContent);
		buttonAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity, ContentFormActivity.class);
				startActivityForResult(intent, REQUEST_CODE_SAVE);
			}
		});
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		int position = info.position;
		
		menu.setHeaderTitle(((Content) listContent.getItemAtPosition(position)).getContent());
		
		menu.add(0, POPUP_CODE_EDIT, 0, "Edit");
		menu.add(0, POPUP_CODE_DELETE, 1, "Delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int position = info.position;

		switch (item.getItemId()) {
			case POPUP_CODE_EDIT:
				edit(position);
				break;
			case POPUP_CODE_DELETE:
				deleteDialog((Content) listContent.getItemAtPosition(position));
				break;
		}

		return super.onContextItemSelected(item);
	}
	
	public void deleteDialog(final Content content) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("Delete " + content.getContent() + "?");
		
		builder.setPositiveButton("Delete", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(new ServiceContent().delete(content.getId()) == Boolean.TRUE) {
					listAdapter.remove(content);
					listAdapter.notifyDataSetChanged();
				}
			}
		});
		
		builder.setNegativeButton("Cancel", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		
		builder.show();
	}
	
	public void edit(int position) {
		Intent intent = new Intent(activity, ContentFormActivity.class);
		intent.putExtra("requestCode", REQUEST_CODE_EDIT);
		intent.putExtra("id", listAdapter.getItem(position).getId());
		startActivityForResult(intent, REQUEST_CODE_EDIT);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_REQUEST_REFRESH) {
			listAdapter.setListContent(new ServiceContent().getAll());
			listAdapter.notifyDataSetChanged();
		}
	}
	
}
