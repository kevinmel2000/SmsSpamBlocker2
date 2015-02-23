package com.anjar.smsspamblocker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.anjar.smsspamblocker.model.Content;
import com.anjar.smsspamblocker.service.ServiceContent;

public class ContentFormActivity extends ActionBarActivity {
	private EditText editTextContent;
	private RadioGroup radioGroupPosition;
	private TextView textViewErrorMessage;
	private Button buttonSave;
	
	private int requestCode = ContentFragment.REQUEST_CODE_SAVE;
	private int id = -1;
	
	private Content content;
	private ServiceContent serviceContent;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_form_activity);
		
		try {
			Intent intent = getIntent();
			id = intent.getExtras().getInt("id");
			
			requestCode = intent.getExtras().getInt("requestCode");
		}
		catch(Exception e) {
			//e.printStackTrace();
		}
		
		serviceContent = new ServiceContent();

		initUI();
	}
	
	private void initUI() {
		editTextContent = (EditText) findViewById(R.id.content_editText_content);
		radioGroupPosition = (RadioGroup) findViewById(R.id.content_radioGroup_position);
		textViewErrorMessage = (TextView) findViewById(R.id.content_textView_errorMessage);
		buttonSave = (Button) findViewById(R.id.content_button_save);
		buttonSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(validate() == Boolean.FALSE) return;

				boolean result = saveOrUpdate();
				
				if(result == true) {
					if(requestCode == ContentFragment.REQUEST_CODE_EDIT) {
						showMessage(getString(R.string.content_form_activity_dialogMessageUpdate), true);
					}
					else {
						showMessage(getString(R.string.content_form_activity_dialogMessageSave), true);
					}
				}
				else {
					showMessage(getString(R.string.content_form_activity_dialogMessageFailed), false);
				}
			}
		});

		if(requestCode == ContentFragment.REQUEST_CODE_EDIT) {
			buttonSave.setText(getString(R.string.content_form_activity_buttonUpdate));
			content = serviceContent.getById(id);
			
			fillData(content);
		}
	}

	private void fillData(Content content) {
		editTextContent.setText(content.getContent());
		
		if(content.getType() != null) {
			if(content.getType() == Content.CONTENT_POSITION_START) {
				radioGroupPosition.check(R.id.content_radioButton_position1);
			}
			else if(content.getType() == Content.CONTENT_POSITION_CENTER) {
				radioGroupPosition.check(R.id.content_radioButton_position2);
			}
			else {
				radioGroupPosition.check(R.id.content_radioButton_position3);
			}
		}
	}
	
	private boolean validate() {
		if(TextUtils.isEmpty(editTextContent.getText())) { 
			textViewErrorMessage.setText(getString(R.string.content_form_activity_validateContentError));
			textViewErrorMessage.setVisibility(View.VISIBLE);
			return false;
		}
		else {
			return true;
		}
	}
	
	private boolean saveOrUpdate() {
		boolean result = false;

		if(requestCode == ContentFragment.REQUEST_CODE_SAVE) {
			content = new Content();
		}

		try {
			if(!TextUtils.isEmpty(editTextContent.getText())) 
				content.setContent(editTextContent.getText().toString());

			content.setIsEnable(Boolean.TRUE);
			
			int indexRadioCheckedIsEnable = radioGroupPosition.indexOfChild(findViewById(radioGroupPosition.getCheckedRadioButtonId()));
			content.setType(indexRadioCheckedIsEnable);
			
			if(requestCode == ContentFragment.REQUEST_CODE_EDIT) {
				if(serviceContent.update(content, id) == true) result = true;
			}
			else {
				serviceContent.insert(content);
				if(content.getId() != null) result = true;
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		}

		if(result == true) {
			setResult(ContentFragment.RESULT_REQUEST_REFRESH);
		}

		return result;
	}

	private void clearForm() {
		editTextContent.setText("");
	}
	
	private void showMessage(String message, final boolean isCloseForm) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ContentFormActivity.this);
		
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

