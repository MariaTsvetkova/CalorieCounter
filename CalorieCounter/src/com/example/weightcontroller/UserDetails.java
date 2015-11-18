package com.example.weightcontroller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.nsd.NsdManager.RegistrationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weightcontroller.additionstaticclasses.ImageUtils;
import com.example.weightcontroller.database.SampleDBAdapter;
import com.example.weightcontroller.database.User;

public class UserDetails extends Activity {

	private SampleDBAdapter handler;
	private Bundle extras;
	private String user_id;
	private User user = new User();
	public static final int IMAGE_HEIGHT = 150;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_details);

		// Get intent data
		handler = new SampleDBAdapter(getApplicationContext());

		user = (User) getIntent().getSerializableExtra("uservalue");

		ImageView iv_photo = (ImageView) findViewById(R.id.iv_contact_photo);

		String imageStr = user.getPhotograph();
		if (imageStr != null && !user.getPhotograph().equals("")) {
			iv_photo.setImageBitmap(ImageUtils.convertBitmap(user
					.getPhotograph()));
		}
		TextView tv_name = (TextView) findViewById(R.id.tv_contact_name);
		tv_name.setText(user.getName());

		TextView tv_age = (TextView) findViewById(R.id.tv_contact_age);
		tv_age.setText(user.getBirthDate());

		TextView tv_height = (TextView) findViewById(R.id.tv_contact_height);
		tv_height.setText(user.getHeight()+" cm");

		TextView tv_weight = (TextView) findViewById(R.id.tv_contact_weight);
		tv_weight.setText(user.getWeight()+ " kg");

		Log.d("myLogs",
				"detailes of = " + user.getId() + " name = " + user.getName());
		user_id = String.valueOf(user.getId());

		Button btn_back = (Button) findViewById(R.id.btn_back_to_contact);
		Button btn_edit_menu=(Button)findViewById(R.id.ButtonMenuEdit);
		registerForContextMenu(btn_edit_menu);
		
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
				// Intent intent = new Intent(UserDetails.this,
				// MainActivity.class);
				// startActivity(intent);
			}
		});

	}


	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.add(1, v.getId(), 0, "Edit");
		menu.add(1, v.getId(), 1, "Delete");
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if(item.getTitle()=="Edit"){
			editContact();
			return true;
	
		}  
        else if(item.getTitle()=="Delete"){
			Log.d("myLogs", "id for delete = " + user.getName());
		
				if (handler.deleteContact((int) user.getId())) {
					Toast.makeText(getApplicationContext(),
						"Contact has been deleted.", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(UserDetails.this, MainActivity.class);
					startActivity(intent);
					return true;
				}
				
        	}  
        else {
        	return false;
        	}  

		return super.onContextItemSelected(item);
	}
	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Toast.makeText(getApplicationContext(), "should be menu here", Toast.LENGTH_LONG);
		
		menu.add(1, 1, 0, "Edit");
		menu.add(1, 2, 1, "Delete");
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int itemid = item.getItemId();

		switch (itemid) {
		case 1:
			editContact();
			break;
		case 2:
			Log.d("myLogs", "id for delete = " + user.getName());
			Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_LONG)
					.show();
			if (handler.deleteContact((int) user.getId())) {
				Toast.makeText(getApplicationContext(),
						"Contact has been deleted.", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(UserDetails.this, MainActivity.class);

				startActivity(intent);
			}
			break;

		}

		return super.onOptionsItemSelected(item);
	}
	
	*/

	public void editContact() {
		Intent intent = new Intent(UserDetails.this, EditContact.class);
		intent.putExtra("uservalue", user);
		startActivity(intent);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		user = handler.getUser(Long.valueOf(user_id));

		ImageView iv_photo = (ImageView) findViewById(R.id.iv_contact_photo);

		if (user.getPhotograph() != null && !user.getPhotograph().equals("")) {
			iv_photo.setImageBitmap(BitmapFactory.decodeFile(user
					.getPhotograph()));
		}

		TextView tv_name = (TextView) findViewById(R.id.tv_contact_name);
		tv_name.setText(user.getName());

		TextView tv_age = (TextView) findViewById(R.id.tv_contact_age);
		tv_age.setText(user.getBirthDate());

		TextView tv_height = (TextView) findViewById(R.id.tv_contact_height);
		tv_height.setText(user.getHeight());

		TextView tv_weight = (TextView) findViewById(R.id.tv_contact_weight);
		tv_weight.setText(user.getWeight());

	}

}
