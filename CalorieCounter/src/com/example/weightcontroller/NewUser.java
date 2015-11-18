package com.example.weightcontroller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weightcontroller.additionstaticclasses.ImageUtils;
import com.example.weightcontroller.database.SampleDBAdapter;
import com.example.weightcontroller.database.User;

public class NewUser extends Activity {

	private static int RESULT_LOAD_IMAGE = 1;

	private SampleDBAdapter handler;

	private String picturePath = "";
	public static final int IMAGE_HEIGHT = 100;
	private String name;
	private String birthday;
	private String height;
	private String weight;

	private int spinnerPosition;

	// private final static int DIALOG_DATE = 1;
	// private static final int DIALOG_NUMBER = 2;
	
	int myYear = 2011;
	int myMonth = 02;
	int myDay = 03;
	private TextView et_birthday;
	private EditText et_height;
	private EditText et_name;
	private EditText et_weight;
	private RadioButton radioSexButton;
	private Spinner spinnerLifeStyle;
	private String transDateString;
	String[] spinnerDataProfessions = { "sedentary life",
			"minor physical activity", "moderate physical activity",
			"high physical activity" };

	public static final String ERROR_MESSAGE = "Some field are still empty. Check it.";

	private RadioGroup radioSexGroup;

	private String photograph = "";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_user);
		Calendar calendar = Calendar.getInstance();
		myYear = calendar.get(Calendar.YEAR);
		myMonth=calendar.get(Calendar.MONTH);
		myDay=calendar.get(Calendar.DATE);
		
		
		handler = new SampleDBAdapter(getApplicationContext());
		radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
		ImageView iv_user_photo = (ImageView) findViewById(R.id.iv_user_photo);
		iv_user_photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(intent, RESULT_LOAD_IMAGE);

			}
		});

		Button btn_add = (Button) findViewById(R.id.btn_add);
		et_birthday = (TextView) findViewById(R.id.et_age);
		et_height = (EditText) findViewById(R.id.et_height);
		et_name = (EditText) findViewById(R.id.et_name);
		et_weight = (EditText) findViewById(R.id.et_weight);

		DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				myYear = year;
				myMonth = monthOfYear;
				myDay = dayOfMonth;
				updateDisplay();
			}
		};
		final DatePickerDialog d = new DatePickerDialog(this, mDateSetListener,
				myYear, myMonth, myDay);
		et_birthday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				d.show();
			}

		});

		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (areEmptyFields()) {
					Toast.makeText(getApplicationContext(), ERROR_MESSAGE,
							Toast.LENGTH_LONG).show();

				} else {
					name = et_name.getText().toString().trim();
					birthday = et_birthday.getText().toString().toUpperCase()
							.trim();
					weight = et_weight.getText().toString().trim();
					height = et_height.getText().toString().trim();
					User contact = new User();
					contact.setName(name);
					contact.setBirthDate(birthday);
					contact.setHeight(height);
					contact.setWeight(weight);
					contact.setPhotograph(photograph);
					contact.setProfessionId(String.valueOf(spinnerPosition));

					int selectedId = radioSexGroup.getCheckedRadioButtonId();
					radioSexButton = (RadioButton) findViewById(selectedId);
					contact.setSex(radioSexButton.getText().toString());

					Boolean added = handler.addContactDetails(contact);
					if (added) {
						Intent intent = new Intent(NewUser.this,
								MainActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(getApplicationContext(),
								"Contact data not added. Please try again",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});

		et_height.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		addItemsOnSpinnerLifeStyle();
		addListenerOnSpinnerItemSelection();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri imageUri = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(imageUri,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			picturePath = cursor.getString(columnIndex);
			cursor.close();
			photograph = picturePath;
			ImageView imageView = (ImageView) findViewById(R.id.iv_user_photo);
			if (imageView != null && photograph != null
					&& !photograph.equals("")) {
				Bitmap bm = ImageUtils.decodeSampledBitmapFromPath(picturePath,
						IMAGE_HEIGHT);
				imageView.setImageBitmap(bm);
				// imageView.setImageBitmap(BitmapFactory.decodeFile(photograph));
			} else {
				imageView.setImageResource(R.drawable.user_icon);
			}

		}
	}

	OnDateSetListener myCallBack = new OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			myYear = year;
			myMonth = monthOfYear;
			myDay = dayOfMonth;
			et_birthday.setText(myDay + "/" + myMonth + "/" + myYear);
		}
	};

	public void addItemsOnSpinnerLifeStyle() {

		spinnerLifeStyle = (Spinner) findViewById(R.id.spinner_profession);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spinnerDataProfessions);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerLifeStyle.setAdapter(dataAdapter);
	}

	public void addListenerOnSpinnerItemSelection() {
		spinnerLifeStyle = (Spinner) findViewById(R.id.spinner_profession);
		spinnerLifeStyle
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {

						spinnerPosition = position;

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	public boolean areEmptyFields() {
		if (et_birthday.getText().toString().equals("")
				|| et_height.getText().toString().equals("")
				|| et_name.getText().toString().equals("")
				|| et_weight.getText().toString().equals("")) {
			return true;
		}
		return false;
	}

	private void updateDisplay() {

		GregorianCalendar c = new GregorianCalendar(myYear, myMonth, myDay);
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");

		sdf = new SimpleDateFormat("dd/MM/yyyy");
		et_birthday.setText(sdf.format(c.getTime()));
		transDateString = sdf.format(c.getTime());

	}

}
