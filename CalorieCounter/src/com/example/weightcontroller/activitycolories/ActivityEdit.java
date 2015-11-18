package com.example.weightcontroller.activitycolories;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weightcontroller.R;
import com.example.weightcontroller.database.Calories;
import com.example.weightcontroller.database.SampleDBAdapter;
import com.example.weightcontroller.database.User;

public class ActivityEdit extends Activity {
	public enum WeightControler {
		WEIGHT45(45), WEIGHT57(57), WEIGHT68(68), WEIGHT79(79), WEIGHT91(91), WEIGHT102(
				102), WEIGHT113(113), WEIGHT125(125), WEIGHT136(136), WEIGHT147(
				147);

		public final int value;

		WeightControler(final int value) {
			this.value = value;
		}

	};

	private User user;
	private String date;
	private String sportActivity;
	private List<ActivityCalories> activityCaloriesList;
	private ActivityCalories activityCalorie = null;
	private TextView tv_date;
	private EditText et_time;
	private Button btn_ok;
	private Button btn_cancel;
	private TextView tv_calories;
	private Button btn_refresh;
	private TextView tv_sport;
	private Integer calorieValue;
	private Calories calories;
	private int calorieBurnt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editor);
		user = (User) getIntent().getSerializableExtra("uservalue");
		calories = (Calories) getIntent().getSerializableExtra("calories");
		date = calories.getDate();
		sportActivity = calories.getActivitySport();
		ActivityCaloriesParse parser = new ActivityCaloriesParse(
				getApplicationContext());
		activityCaloriesList = parser.getColoriesActivityList();

		for (ActivityCalories calorieActivity : activityCaloriesList) {
			if (calorieActivity.getName().equalsIgnoreCase(
					sportActivity.toString())) {
				activityCalorie = calorieActivity;
				break;
			}
		}
		calorieBurnt = getWeightRang();

		init();
		tv_date.setText(date);
		tv_sport.setText(sportActivity.toString());

		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btn_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!et_time.getText().toString().matches("")) {
					Integer etTime = Integer.valueOf(et_time.getText()
							.toString());

					calorieValue = etTime * (calorieBurnt / 60);
					tv_calories.setText(calorieValue.toString());

				}

			}
		});
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!et_time.getText().toString().matches("")) {
					Integer etTime = Integer.valueOf(et_time.getText()
							.toString());

					calorieValue = etTime * (calorieBurnt / 60);
					tv_calories.setText(calorieValue.toString());

				} else {
					return;
				}

				calories.setActivitySport(sportActivity.toString());
				calories.setCalorieValue(tv_calories.getText().toString());
				calories.setDate(date);
				calories.setTime(et_time.getText().toString());
				calories.setUserId(user.getId() + "");
				SampleDBAdapter bdHelper = new SampleDBAdapter(
						getApplicationContext());
				Log.d("myLogs", "activity id for edit for ActivityEdit = "
						+ calories.getId());
				boolean added = bdHelper.editActivity(calories);
				if (!added) {
					Toast.makeText(getApplicationContext(),
							"Activity data not added. Please try again",
							Toast.LENGTH_LONG).show();
				} else {
					showMessageBox();

				}
			}

		});

	}

	private void init() {
		tv_date = (TextView) findViewById(R.id.tv_date);
		et_time = (EditText) findViewById(R.id.et_time);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		tv_calories = (TextView) findViewById(R.id.tv_calories);
		btn_refresh = (Button) findViewById(R.id.btn_refresh);
		tv_sport = (TextView) findViewById(R.id.tv_sport);

		tv_date.setText(date);
		et_time.setText(calories.getTime());
		tv_sport.setText(sportActivity);
		Integer etTime = Integer.valueOf(et_time.getText().toString());
		calorieValue = etTime * (calorieBurnt / 60);
		tv_calories.setText(calorieValue.toString());
		btn_ok.setText("EDIT");

	}

	private int getWeightRang() {
		int userWeight = Integer.valueOf(user.getWeight());
		if (userWeight <= WeightControler.WEIGHT45.value) {

			return activityCalorie.getWeight45();
		} else if (userWeight > WeightControler.WEIGHT45.value
				&& userWeight <= WeightControler.WEIGHT57.value) {

			return activityCalorie.getWeight57();

		} else if (userWeight > WeightControler.WEIGHT57.value
				&& userWeight <= WeightControler.WEIGHT68.value) {

			return activityCalorie.getWeight68();

		} else if (userWeight > WeightControler.WEIGHT68.value
				&& userWeight <= WeightControler.WEIGHT79.value) {

			return activityCalorie.getWeight79();

		} else if (userWeight > WeightControler.WEIGHT79.value
				&& userWeight <= WeightControler.WEIGHT91.value) {

			return activityCalorie.getWeight79();

		} else if (userWeight > WeightControler.WEIGHT91.value
				&& userWeight <= WeightControler.WEIGHT102.value) {

			return activityCalorie.getWeight102();

		} else if (userWeight > WeightControler.WEIGHT102.value
				&& userWeight <= WeightControler.WEIGHT113.value) {

			return activityCalorie.getWeight113();

		} else if (userWeight > WeightControler.WEIGHT113.value
				&& userWeight <= WeightControler.WEIGHT125.value) {

			return activityCalorie.getWeight125();

		} else if (userWeight > WeightControler.WEIGHT125.value
				&& userWeight <= WeightControler.WEIGHT136.value) {

			return activityCalorie.getWeight136();

		} else {

			return activityCalorie.getWeight147();
		}
	}

	public void showMessageBox() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Message").setMessage("Your activity was edited")
				.setIcon(R.drawable.ic_launcher).setCancelable(false)
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						finish();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
