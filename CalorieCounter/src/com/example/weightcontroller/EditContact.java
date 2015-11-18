package com.example.weightcontroller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
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

public class EditContact extends Activity {

	private static int RESULT_LOAD_IMAGE = 1;

	private SampleDBAdapter handler;

	private EditText et_name;
	private EditText et_age;
	private EditText et_height;
	private EditText et_weight;
	int myYear = 2011;
	int myMonth = 02;
	int myDay = 03;
	private final static int DIALOG_DATE = 1;
	private ImageView iv_user_photo;
	private Spinner spinnerProfessions;
	private User user;
	private int spinnerPosition;
	public static final int IMAGE_HEIGHT = 100;

	private Bitmap image;
	private RadioGroup radioSexGroup;
	String[] spinnerDataProfessions = { "sedentary life",
			"minor physical activity", "moderate physical activity",
			"high physical activity" };

	private String transDateString;

	private String photograph;
	public static final String ERROR_MESSAGE = "Some field are still empty. Check it.";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_user);
		Calendar calendar = Calendar.getInstance();
		myYear = calendar.get(Calendar.YEAR);
		myMonth=calendar.get(Calendar.MONTH);
		myDay=calendar.get(Calendar.DATE);
		spinnerProfessions = (Spinner) findViewById(R.id.spinner_profession);

		user = (User) getIntent().getSerializableExtra("uservalue");

		radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
		handler = new SampleDBAdapter(getApplicationContext());

		TextView tv_title = (TextView) findViewById(R.id.tv_new_contact_title);
		tv_title.setVisibility(View.GONE);

		et_name = (EditText) findViewById(R.id.et_name);
		et_name.setText(user.getName());

		et_age = (EditText) findViewById(R.id.et_age);
		et_age.setText(user.getBirthDate());
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
		et_age.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				d.show();
			}

		});

		et_height = (EditText) findViewById(R.id.et_height);
		et_height.setText(user.getHeight());

		et_weight = (EditText) findViewById(R.id.et_weight);
		et_weight.setText(user.getWeight());

		iv_user_photo = (ImageView) findViewById(R.id.iv_user_photo);
		if (user.getPhotograph() != null && !user.getPhotograph().equals("")) {
			image = ImageUtils.decodeSampledBitmapFromPath(
					user.getPhotograph(), IMAGE_HEIGHT);
			iv_user_photo.setImageBitmap(image);
		}

		addItemsOnSpinnerProfession();
		addListenerOnSpinnerItemSelection();

		spinnerProfessions
				.setSelection(Integer.valueOf(user.getProfessionId()));
		setSex();
		Button btn_update = (Button) findViewById(R.id.btn_add);
		btn_update.setText("Update");
		btn_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (areEmptyFields()) {
					Toast.makeText(getApplicationContext(), ERROR_MESSAGE,
							Toast.LENGTH_LONG).show();

				} else {
					User contact = new User();
					contact.setId((user.getId()));
					contact.setName(et_name.getText().toString());
					contact.setBirthDate(et_age.getText().toString());
					contact.setHeight(et_height.getText().toString());
					contact.setWeight(et_weight.getText().toString());
					contact.setPhotograph(photograph);
					contact.setProfessionId(String.valueOf(spinnerPosition));
					int selectedId = radioSexGroup.getCheckedRadioButtonId();
					RadioButton radioSexButton = (RadioButton) findViewById(selectedId);
					contact.setSex(radioSexButton.getText().toString());

					Boolean updated = handler.editContact(contact);

					if (updated) {
						// Intent intent = new Intent(EditContact.this,
						// MainActivity.class);
						// startActivity(intent);
						finish();
					} else {
						Toast.makeText(getApplicationContext(),
								"Contact data not updated. Please try again",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});

		iv_user_photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(intent, RESULT_LOAD_IMAGE);

			}
		});

	}

	public void addItemsOnSpinnerProfession() {

		spinnerProfessions = (Spinner) findViewById(R.id.spinner_profession);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spinnerDataProfessions);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerProfessions.setAdapter(dataAdapter);
	}

	public void addListenerOnSpinnerItemSelection() {
		spinnerProfessions = (Spinner) findViewById(R.id.spinner_profession);
		spinnerProfessions
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

	public void setSex() {
		radioSexGroup.clearCheck();

		RadioButton maleRadioBtn = (RadioButton) findViewById(R.id.radioMale);
		RadioButton femaleRadioBtn = (RadioButton) findViewById(R.id.radioFemale);
		if (maleRadioBtn.getText().equals(user.getSex())) {
			maleRadioBtn.setChecked(true);

		} else {
			femaleRadioBtn.setChecked(true);
		}

	}

	public boolean areEmptyFields() {
		if (et_age.getText().toString().equals("")
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
		et_age.setText(sdf.format(c.getTime()));
		transDateString = sdf.format(c.getTime());
		// Toast.makeText(getApplicationContext(), "date = " + transDateString,
		// Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri imageUri = data.getData();
			String picturePath = "";
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(imageUri,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			picturePath = cursor.getString(columnIndex);
			cursor.close();

			ImageView imageView = (ImageView) findViewById(R.id.iv_user_photo);
			if (picturePath != null && !picturePath.equals("")) {
				photograph = picturePath;
				Bitmap bm = ImageUtils.convertBitmap(picturePath);
				imageView.setImageBitmap(bm);
			}

		}
	}

	@Override
	public void onBackPressed() {
		System.exit(0);
		super.onBackPressed();
	}

}