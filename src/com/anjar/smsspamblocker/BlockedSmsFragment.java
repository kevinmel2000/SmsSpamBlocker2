package com.anjar.smsspamblocker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

import com.anjar.smsspamblocker.model.BlockedSms;
import com.anjar.smsspamblocker.service.ServiceBlockedSms;

public class BlockedSmsFragment extends Fragment{
	private ListView listBlocked;
	private Activity activity;
	private View rootView;
	public static BlockedSmsListAdapter<BlockedSms> listAdapter;
	
	public static final int POPUP_CODE_EDIT = 305;
	public static final int POPUP_CODE_DELETE = 306;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_blocked_sms, container, false);

		initWidget();
		
		return rootView;
	}
	
	private void initWidget() {
		listBlocked = (ListView) rootView.findViewById(R.id.blockedSms_listView_blocked);
		
		listAdapter = new BlockedSmsListAdapter<BlockedSms>(activity, new ServiceBlockedSms().getAll());
		listBlocked.setAdapter(listAdapter);
		registerForContextMenu(listBlocked);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		int position = info.position;
		
		menu.setHeaderTitle(((BlockedSms) listBlocked.getItemAtPosition(position)).getNumber());
		
		menu.add(0, POPUP_CODE_DELETE, 1, "Delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int position = info.position;

		switch (item.getItemId()) {
			case POPUP_CODE_DELETE:
				deleteDialog((BlockedSms) listBlocked.getItemAtPosition(position));
				break;
		}

		return super.onContextItemSelected(item);
	}
	
	public void deleteDialog(final BlockedSms blockedSms) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		
		builder.setTitle("Delete " + blockedSms.getNumber() + "?");
		builder.setMessage(blockedSms.getContent());
		
		builder.setPositiveButton("Delete", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(new ServiceBlockedSms().delete(blockedSms.getId()) == Boolean.TRUE) {
					listAdapter.remove(blockedSms);
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
}
