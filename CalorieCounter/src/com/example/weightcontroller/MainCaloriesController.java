package com.example.weightcontroller;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.weightcontroller.activitycolories.ActivityCalories;
import com.example.weightcontroller.activitycolories.ActivityCaloriesParse;
import com.example.weightcontroller.database.SampleDBAdapter;
import com.example.weightcontroller.database.User;
import com.example.weightcontroller.fragments.BodyMassIndex;
import com.example.weightcontroller.fragments.BodyMassIndex.OnDataPass;
import com.example.weightcontroller.fragments.Diary;
import com.example.weightcontroller.fragments.GraphicalFragment;
import com.example.weightcontroller.fragments.IdealWeight;
import com.example.weightcontroller.navigationdrawer.CustomDrawerAdapter;
import com.example.weightcontroller.navigationdrawer.DrawerItem;

public class MainCaloriesController extends ActionBarActivity implements
		OnDataPass {
	private long id_user;
	private User user;
	private SampleDBAdapter handler;

	private String[] menuTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private CharSequence mTitle;
	private ActionBarDrawerToggle mDrawerToggle;

	CustomDrawerAdapter adapter;
	List<DrawerItem> dataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_colories_activity);
		handler = new SampleDBAdapter(getApplicationContext());
		user = (User) getIntent().getSerializableExtra("uservalue");
		id_user = user.getId();

		/*****************************************************************/
		dataList = new ArrayList<DrawerItem>();
		dataList.add(new DrawerItem("body mass index (BMI)", R.drawable.bmi));
		dataList.add(new DrawerItem("ideal weight", R.drawable.weight));
		dataList.add(new DrawerItem("activity diary", R.drawable.sport));
		dataList.add(new DrawerItem("activity graph", R.drawable.chart));

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		adapter = new CustomDrawerAdapter(this, R.layout.drawer_item, dataList);

		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_launcher, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				// setTitle(menuTitles[position]);
				// getSupportActionBar().setTitle("Something");
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle("Menu");
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		/*************************************************************************************/
		ActivityCaloriesParse activityColoriesParse = new ActivityCaloriesParse(
				getApplicationContext());
		ArrayList<ActivityCalories> activityColoriesList = activityColoriesParse
				.getColoriesActivityList();
		/*************************************************************************************/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_bar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.menuBar) {
			Intent intent = new Intent(MainCaloriesController.this,
					UserDetails.class);

			intent.putExtra("uservalue", user);
			startActivity(intent);
		} else if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void selectItem(int position) {

		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(menuTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position,
				long id) {
			selectItemMenu(position);
		}
	}

	public void selectItemMenu(int possition) {

		Fragment fragment = null;
		Bundle args = new Bundle();
		switch (possition) {
		case 0:
			fragment = new BodyMassIndex();
			args.putString(BodyMassIndex.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(BodyMassIndex.IMAGE_RESOURCE_ID, dataList
					.get(possition).getImgResID());
			break;
		case 1:
			fragment = new IdealWeight();
			break;
		case 2:
			fragment = new Diary();
			break;
		case 3:
			fragment = new GraphicalFragment();
			break;
		default:
			return;
			// break;
		}

		fragment.setArguments(args);
		FragmentManager frgManager = getSupportFragmentManager();
		frgManager.beginTransaction().replace(R.id.content_frame, fragment)
				.commit();

		mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	public void onDataPass(String data) {
		Log.d("myLogs", "value from fragment = " + data);

	}

	public User getUser() {
		return this.user;
	}

	@Override
	protected void onRestart() {
		user = handler.getUser(id_user);
		super.onRestart();
	}
}
