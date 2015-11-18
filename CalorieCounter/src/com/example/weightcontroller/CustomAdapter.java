package com.example.weightcontroller;

import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weightcontroller.database.User;

public class CustomAdapter extends BaseAdapter {

	private List<User> items;
	private Context context;
	private LayoutInflater inflater;

	public CustomAdapter(Context _context, List<User> _items) {
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
		User user = items.get(position);
		Log.d("myLogs", "getView in adapter: " + user.getId());

		View view = convertView;

		if (view == null)
			view = inflater.inflate(R.layout.user_item, null);

		TextView name = (TextView) view.findViewById(R.id.tv_full_name);
		TextView age = (TextView) view.findViewById(R.id.tv_age);
		ImageView photo = (ImageView) view.findViewById(R.id.list_image);

		name.setText(user.getName());
		age.setText(user.getBirthDate());
		if (user.getPhotograph() != null && !user.getPhotograph().equals("")) {
			photo.setImageBitmap(BitmapFactory.decodeFile(user.getPhotograph()));
		}

		return view;
	}

}