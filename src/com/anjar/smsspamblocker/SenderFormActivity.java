package com.anjar.smsspamblocker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anjar.smsspamblocker.model.Sender;
import com.anjar.smsspamblocker.service.ServiceSender;

public class SenderFormActivity extends Activity {
	private EditText editTextNumber;
	private TextView textViewErrorMessage;
	
	private Button buttonSave;
	
	private int requestCode = SenderFragment.REQUEST_CODE_SAVE;
	private int id = -1;
	
	private Sender sender;
	private ServiceSender serviceSender;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sender_form_activity);
		
		try {
			Intent intent = getIntent();
			id = intent.getExtras().getInt("id");
			
			requestCode = intent.getExtras().getInt("requestCode");
		}
		catch(Exception e) {
			//e.printStackTrace();
		}
		
		serviceSender = new ServiceSender();

		initUI();
	}
	
	private void initUI() {
		textViewErrorMessage = (TextView) findViewById(R.id.sender_textView_errorMessage);
		editTextNumber = (EditText) findViewById(R.id.sender_editText_number);
		buttonSave = (Button) findViewById(R.id.sender_button_save);
		buttonSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(validate() == Boolean.FALSE) return;
				
				boolean result = saveOrUpdate();
				
				if(result == true) {
					if(requestCode == SenderFragment.REQUEST_CODE_EDIT) {
						showMessage(getString(R.string.sender_form_activity_dialogMessageUpdate), true);
					}
					else {
						showMessage(getString(R.string.sender_form_activity_dialogMessageSave), true);
					}
				}
				else {
					showMessage(getString(R.string.sender_form_activity_dialogMessageFailed), false);
				}
			}
		});
		
		if(requestCode == SenderFragment.REQUEST_CODE_EDIT) {
			buttonSave.setText(getString(R.string.sender_form_activity_buttonUpdate));
			sender = serviceSender.getById(id);
			
			fillData(sender);
		}
	}

	private void fillData(Sender sender) {
		editTextNumber.setText(sender.getNumber());
	}
	
	private boolean validate() {
		if(TextUtils.isEmpty(editTextNumber.getText())) { 
			textViewErrorMessage.setText(getString(R.string.sender_form_activity_validateNumberError));
			textViewErrorMessage.setVisibility(View.VISIBLE);
			return false;
		}
		else {
			return true;
		}
	}
	
	private boolean saveOrUpdate() {
		boolean result = false;
		
		if(requestCode == SenderFragment.REQUEST_CODE_SAVE) {
			sender = new Sender();
		}

		try {
			if(!TextUtils.isEmpty(editTextNumber.getText())) 
				sender.setNumber(editTextNumber.getText().toString());

			sender.setIsEnable(Boolean.TRUE);

			if(requestCode == SenderFragment.REQUEST_CODE_EDIT) {
				if(serviceSender.update(sender, id) == true) result = true;
			}
			else {
				serviceSender.insert(sender);
				if(sender.getId() != null) result = true;
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		}

		if(result == true) {
			setResult(SenderFragment.RESULT_REQUEST_REFRESH);
		}

		return result;
	}

	private void clearForm() {
		editTextNumber.setText("");
	}
	
	private void showMessage(String message, final boolean isCloseForm) {
		AlertDialog.Builder builder = new AlertDialog.Builder(SenderFormActivity.this);
		
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				if(isCloseForm == true) {
					finish();
				}
			}
		});

		// Create the AlertDialog
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}

