package com.example.weightcontroller.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.weightcontroller.ActivityDiary;
import com.example.weightcontroller.MainCaloriesController;
import com.example.weightcontroller.R;
import com.example.weightcontroller.activitycolories.ActivityEdit;
import com.example.weightcontroller.activitycolories.CaloriesAdapter;
import com.example.weightcontroller.database.Calories;
import com.example.weightcontroller.database.SampleDBAdapter;
import com.example.weightcontroller.database.User;

public class Diary extends Fragment implements OnClickListener {

	private User user;
	private Button bt_add_activity;
	private EditText et_add_date;
	int myYear = 2011;
	int myMonth = 02;
	int myDay = 03;
	private String transDateString;
	private View view;
	private ListView lv_contact_list;
	private SampleDBAdapter handler;
	private Button bt_refresh;
	private List<Calories> activities;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.diary, container, false);
		user = ((MainCaloriesController) getActivity()).getUser();
		Calendar calendar = Calendar.getInstance();
		myYear = calendar.get(Calendar.YEAR);
		myMonth=calendar.get(Calendar.MONTH);
		myDay=calendar.get(Calendar.DATE);
		bt_add_activity = (Button) view.findViewById(R.id.bt_add_activity);
		et_add_date = (EditText) view.findViewById(R.id.et_add_date);
		bt_add_activity.setOnClickListener(this);
		bt_refresh = (Button) view.findViewById(R.id.bt_refresh);
		DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				myYear = year;
				myMonth = monthOfYear;
				myDay = dayOfMonth;
				updateDisplay();
			}
		};
		final DatePickerDialog d = new DatePickerDialog(view.getContext(),
				mDateSetListener, myYear, myMonth, myDay);
		et_add_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				d.show();
			}

		});
		handler = new SampleDBAdapter(view.getContext());
		lv_contact_list = (ListView) view.findViewById(R.id.lv_contact_list);
		loadActivitiesList();
		bt_refresh.setOnClickListener(this);
		lv_contact_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String id_user = String.valueOf(activities.get(position)
						.getId());

				Intent intent = new Intent(view.getContext(),
						ActivityEdit.class);

				User user = handler.getUser(Long.valueOf(activities.get(
						position).getUserId()));
				Calories calories = handler.getActivity(activities
						.get(position).getId());
				intent.putExtra("uservalue", user);
				intent.putExtra("calories", calories);
				startActivity(intent);

			}

		});

		lv_contact_list
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {

						showMessageBoxForDelete(activities.get(position)
								.getId());

						return false;
					}

				});

		return view;
	}

	private void loadActivitiesList() {
		activities = handler.readAllActivities(user.getId());
		for (Calories c : activities) {
			String record = "ID=" + c.getId() + " | Name="
					+ c.getActivitySport() + " | " + c.getTime();
			Log.d("Record", record);
		}
		CaloriesAdapter adapter = new CaloriesAdapter(getActivity(), activities);
		lv_contact_list.setAdapter(adapter);
		et_add_date.setText("");

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_add_activity:
			if (!et_add_date.getText().toString().matches("")) {
				Intent intent = new Intent(getActivity(), ActivityDiary.class);
				intent.putExtra("uservalue", user);
				intent.putExtra("date", transDateString);
				startActivity(intent);
			} else {
				Toast.makeText(getActivity(), "Some fields are empty.",
						Toast.LENGTH_LONG).show();
			}
			break;

		case R.id.bt_refresh:
			if (!et_add_date.getText().toString().matches("")) {
				loadActivitiesListByDate();
			} else {
				loadActivitiesList();
			}

			break;
		default:
			break;
		}

	}

	private void updateDisplay() {

		GregorianCalendar c = new GregorianCalendar(myYear, myMonth, myDay);
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");

		sdf = new SimpleDateFormat("dd/MM/yyyy");
		et_add_date.setText(sdf.format(c.getTime()));
		transDateString = sdf.format(c.getTime());

	}

	private void loadActivitiesListByDate() {
		String date = et_add_date.getText().toString();
		activities = (List<Calories>) handler.getActivityByDay(user.getId(),
				date);
		for (Calories c : activities) {
			String record = "ID=" + c.getId() + " | Name="
					+ c.getActivitySport() + " | " + c.getTime();
			Log.d("Record", record);
		}
		CaloriesAdapter adapter = new CaloriesAdapter(getActivity(), activities);
		lv_contact_list.setAdapter(adapter);

	}

	@Override
	public void onResume() {
		Log.d("myLogs", "list is refreshed");
		lv_contact_list.invalidateViews();
		loadActivitiesList();
		super.onResume();
	}

	public void showMessageBoxForDelete(final long activityId) {
		AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
		builder.setTitle("Message")
				.setMessage("Would you like to delete activity?")
				.setIcon(R.drawable.ic_launcher)
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						SampleDBAdapter handler = new SampleDBAdapter(view
								.getContext());

						boolean deleted = handler.deleteActivity(activityId);
						if (!deleted) {
							Toast.makeText(
									view.getContext(),
									"Activity data not added. Please try again",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(view.getContext(),
									"Activity was deleted", Toast.LENGTH_SHORT)
									.show();
						}

						dialog.cancel();
						loadActivitiesList();
					}
				})
				.setNeutralButton("CANCEL",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
