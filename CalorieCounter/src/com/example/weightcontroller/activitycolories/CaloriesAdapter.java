package com.example.weightcontroller.activitycolories;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weightcontroller.R;
import com.example.weightcontroller.database.Calories;

public class CaloriesAdapter extends BaseAdapter {

	private List<Calories> items;
	private Context context;
	private LayoutInflater inflater;
	private ImageView photo;
	private View view;

	public CaloriesAdapter(Context _context, List<Calories> _items) {
		inflater = LayoutInflater.from(_context);
		this.items = _items;
		this.context = _context;

	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Calories calorie = items.get(position);
		Log.d("myLogs", "getView in adapter: " + calorie.getId());

		view = convertView;

		if (view == null)
			view = inflater.inflate(R.layout.calorie_item, null);

		TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
		TextView calorieValue = (TextView) view.findViewById(R.id.tv_calorie);
		photo = (ImageView) view.findViewById(R.id.list_image);

		tv_time.setText(calorie.getTime() + " (min)");
		calorieValue.setText(calorie.getCalorieValue() + " (cal)");
		setImage(calorie);
		TextView tv_date = (TextView) view.findViewById(R.id.tv_date);
		tv_date.setText(calorie.getDate());
		return view;
	}

	public void setImage(Calories calorie) {

		if (calorie.getActivitySport().equalsIgnoreCase("WALK")) {
			photo.setImageDrawable(view.getResources().getDrawable(
					R.drawable.walk_icon));
		}
		if (calorie.getActivitySport().equalsIgnoreCase("RUN")) {
			photo.setImageDrawable(view.getResources().getDrawable(
					R.drawable.run_icon));
		}
		if (calorie.getActivitySport().equalsIgnoreCase("SWIMMING")) {
			photo.setImageDrawable(view.getResources().getDrawable(
					R.drawable.swimming_icon));
		}
		if (calorie.getActivitySport().equalsIgnoreCase("CYCLING")) {
			photo.setImageDrawable(view.getResources().getDrawable(
					R.drawable.cycle_icon));
		}
		if (calorie.getActivitySport().equalsIgnoreCase("AEROBICS")) {
			photo.setImageDrawable(view.getResources().getDrawable(
					R.drawable.aerobics_icon));
		}
		if (calorie.getActivitySport().equalsIgnoreCase("LIGHTWORKOUT")) {
			photo.setImageDrawable(view.getResources().getDrawable(
					R.drawable.light_workout));
		}
		if (calorie.getActivitySport().equalsIgnoreCase("VIGOROUS")) {
			photo.setImageDrawable(view.getResources().getDrawable(
					R.drawable.vigorous_icon));
		}

	}

}
