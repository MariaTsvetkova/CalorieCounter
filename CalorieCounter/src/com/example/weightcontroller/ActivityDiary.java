package com.example.weightcontroller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.weightcontroller.activitycolories.ActivitySave;
import com.example.weightcontroller.database.User;

public class ActivityDiary extends Activity implements OnClickListener {

	private User user;
	private Button walkBtn, runBtn, swimmingBtn, cyclingBtn, aerobicsBtn,
			lightWorkoutBtn, vigorousBtn;
	private EditText et_Date;
	int myYear = 2011;
	int myMonth = 02;
	int myDay = 03;
	private String transDateString;

	public enum SportActivity {
		WALK, RUN, SWIMMING, CYCLING, AEROBICS, LIGHTWORKOUT, VIGOROUS
	};

	private SportActivity sportActivity;
	private String date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout);
		Calendar calendar = Calendar.getInstance();
		myYear = calendar.get(Calendar.YEAR);
		myMonth=calendar.get(Calendar.MONTH);
		myDay=calendar.get(Calendar.DATE);
		user = (User) getIntent().getSerializableExtra("uservalue");
		date = getIntent().getStringExtra("date");

		walkBtn = (Button) findViewById(R.id.walkBtn);
		runBtn = (Button) findViewById(R.id.runBtn);
		swimmingBtn = (Button) findViewById(R.id.swimmingBtn);
		cyclingBtn = (Button) findViewById(R.id.cyclingBtn);
		aerobicsBtn = (Button) findViewById(R.id.aerobicsBtn);
		lightWorkoutBtn = (Button) findViewById(R.id.lightWorkoutBtn);
		vigorousBtn = (Button) findViewById(R.id.vigorousBtn);
		et_Date = (EditText) findViewById(R.id.et_Date);
		et_Date.setText(date);

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
		et_Date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				d.show();
			}

		});

		walkBtn.setOnClickListener(this);
		runBtn.setOnClickListener(this);
		swimmingBtn.setOnClickListener(this);
		cyclingBtn.setOnClickListener(this);
		aerobicsBtn.setOnClickListener(this);
		lightWorkoutBtn.setOnClickListener(this);
		vigorousBtn.setOnClickListener(this);

		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.walkBtn:
			sportActivity = SportActivity.WALK;
			break;
		case R.id.runBtn:
			sportActivity = SportActivity.RUN;
			break;
		case R.id.swimmingBtn:
			sportActivity = SportActivity.SWIMMING;
			break;
		case R.id.cyclingBtn:
			sportActivity = SportActivity.CYCLING;
			break;
		case R.id.aerobicsBtn:
			sportActivity = SportActivity.AEROBICS;
			break;
		case R.id.lightWorkoutBtn:
			sportActivity = SportActivity.LIGHTWORKOUT;
			break;
		case R.id.vigorousBtn:
			sportActivity = SportActivity.VIGOROUS;
			break;
		default:
			break;
		}

		Intent intent = new Intent(this, ActivitySave.class);
		intent.putExtra("uservalue", user);
		intent.putExtra("sportactivity", sportActivity);
		intent.putExtra("date", et_Date.getText().toString());
		startActivity(intent);

	}

	private void updateDisplay() {

		GregorianCalendar c = new GregorianCalendar(myYear, myMonth, myDay);
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");

		sdf = new SimpleDateFormat("dd/MM/yyyy");
		et_Date.setText(sdf.format(c.getTime()));
		transDateString = sdf.format(c.getTime());

	}

}
