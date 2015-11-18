package com.example.weightcontroller;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.weightcontroller.database.SampleDBAdapter;
import com.example.weightcontroller.database.User;

public class MainActivity extends Activity {

	private SampleDBAdapter handler;

	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		handler = new SampleDBAdapter(getApplicationContext());

		Button btn_add_new = (Button) findViewById(R.id.btn_add_contact);
		btn_add_new.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, NewUser.class);
				startActivity(intent);
			}
		});

		lv = (ListView) findViewById(R.id.lv_contact_list);

		loadContactData();

	}

	private void loadContactData() {

		final List<User> users = handler.readAllContacts();

		CustomAdapter adapter = new CustomAdapter(this, users);
		lv.setAdapter(adapter);

		for (User c : users) {
			String record = "ID=" + c.getId() + " | Name=" + c.getName()
					+ " | " + c.getBirthDate();
			Log.d("Record", record);
			
		}

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String id_user = String.valueOf(users.get(position).getId());

				Intent intent = new Intent(MainActivity.this,
						MainCaloriesController.class);

				User user = handler.getUser(users.get(position).getId());
				
				// handler.updateActivitiesMoreThanYear(user.getId());   
				intent.putExtra("uservalue", user);
				startActivity(intent);

			}

		});

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		final List<User> users = handler.readAllContacts();

		CustomAdapter adapter = new CustomAdapter(this, users);
		lv.setAdapter(adapter);

		for (User c : users) {
			String record = "ID=" + c.getId() + " | Name=" + c.getName()
					+ " | " + c.getBirthDate();

			Log.d("Record", record);
		}

	}
}
