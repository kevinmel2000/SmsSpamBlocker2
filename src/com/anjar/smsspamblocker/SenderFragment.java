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

import com.anjar.smsspamblocker.model.Sender;
import com.anjar.smsspamblocker.service.ServiceSender;

public class SenderFragment extends Fragment{
	private ListView listSender;
	private Button buttonAdd;
	private Activity activity;
	private View rootView;
	private SenderListAdapter<Sender> listAdapter;
	
	public static final int POPUP_CODE_EDIT = 303;
	public static final int POPUP_CODE_DELETE = 304;
	
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
		rootView = inflater.inflate(R.layout.fragment_sender, container, false);

		initWidget();
		
		return rootView;
	}

	private void initWidget() {
		listSender = (ListView) rootView.findViewById(R.id.sender_listView_sender);
		
		listAdapter = new SenderListAdapter<Sender>(activity, new ServiceSender().getAll());
		listSender.setAdapter(listAdapter);
		registerForContextMenu(listSender);
		
		listSender.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CheckBox isEnable = (CheckBox) view.findViewById(R.id.inflated_chexbox_isEnable);
				if(isEnable.isChecked()) {
					((Sender)listSender.getItemAtPosition(position)).setIsEnable(Boolean.FALSE);
				}
				else {
					((Sender)listSender.getItemAtPosition(position)).setIsEnable(Boolean.TRUE);
				}
				
				listAdapter.notifyDataSetChanged();
			}
		});
		
		buttonAdd = (Button) rootView.findViewById(R.id.sender_button_addSpamSender);
		buttonAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity, SenderFormActivity.class);
				startActivityForResult(intent, REQUEST_CODE_EDIT);
			}
		});
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		int position = info.position;
		
		menu.setHeaderTitle(((Sender) listSender.getItemAtPosition(position)).getNumber());
		
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
				deleteDialog((Sender) listSender.getItemAtPosition(position));
				break;
		}

		return super.onContextItemSelected(item);
	}
	
	
	public void deleteDialog(final Sender sender) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("Delete " + sender.getNumber() + "?");
		
		builder.setPositiveButton("Delete", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(new ServiceSender().delete(sender.getId()) == Boolean.TRUE) {
					listAdapter.remove(sender);
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
		Intent intent = new Intent(activity, SenderFormActivity.class);
		intent.putExtra("requestCode", REQUEST_CODE_EDIT);
		intent.putExtra("id", listAdapter.getItem(position).getId());
		startActivityForResult(intent, REQUEST_CODE_EDIT);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_REQUEST_REFRESH) {
			listAdapter.setListSender(new ServiceSender().getAll());
			listAdapter.notifyDataSetChanged();
		}
	}
}
