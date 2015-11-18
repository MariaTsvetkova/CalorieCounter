package com.example.weightcontroller;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CustomNumberPickerDialog extends Dialog implements
		android.view.View.OnClickListener {

	public Activity activity;
	public Dialog dialog;
	public Button submitBtn, cancelBtn;

	Button btnUp, btnDown;
	TextView textViewUp, textViewMid, textViewBottom;

	int nStart = 5;
	int nEnd = 250;

	private OnNumberPickerDialogDataPass dataPasser;

	public interface OnNumberPickerDialogDataPass {
		void onNumberPickerDialogDataPass(String data);
	}

	public CustomNumberPickerDialog(Activity activity) {
		super(activity);
		this.activity = activity;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.number_picker_dialog);

		submitBtn = (Button) findViewById(R.id.submitBtn);

		submitBtn.setOnClickListener(this);

		this.setCanceledOnTouchOutside(true);

		btnUp = (Button) findViewById(R.id.button1);
		btnDown = (Button) findViewById(R.id.button2);

		textViewUp = (TextView) findViewById(R.id.textView1);
		textViewMid = (TextView) findViewById(R.id.textView2);
		textViewBottom = (TextView) findViewById(R.id.textView3);

		textViewUp.setText("5");
		textViewMid.setText("6");
		textViewBottom.setText("7");

		btnUp.setOnClickListener(this);
		btnDown.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		String getString = String.valueOf(textViewMid.getText());
		int curent = Integer.parseInt(getString);
		switch (v.getId()) {
		case R.id.submitBtn:
			// et_value.setText(bar.getProgress());
			onDataPass(String.valueOf(4));
			dismiss();
			break;
		case R.id.button1:

			if (curent < nEnd) {
				curent++;
				textViewUp.setText(String.valueOf(curent - 1));
				textViewMid.setText(String.valueOf(curent));
				textViewBottom.setText(String.valueOf(curent + 1));
			}

			break;

		case R.id.button2:

			if (curent > nStart) {
				curent--;
				textViewUp.setText(String.valueOf(curent - 1));
				textViewMid.setText(String.valueOf(curent));
				textViewBottom.setText(String.valueOf(curent + 1));
			}

			break;

		/*
		 * case R.id.cancelBtn: this.dismiss(); break;
		 */
		default:
			break;
		}

	}

	private void onDataPass(String progress) {
		dataPasser.onNumberPickerDialogDataPass(progress);

	}

}
