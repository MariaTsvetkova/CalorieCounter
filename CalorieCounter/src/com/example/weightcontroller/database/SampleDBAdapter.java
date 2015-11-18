package com.example.weightcontroller.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SampleDBAdapter extends SQLiteOpenHelper {
	private static final String LOG = "myLogs";
	// Database name
	private static final String DATABASE_NAME = "userManager";

	// Database version
	private static final int DATABASE_VERSION = 1;

	// table names
	private static final String TABLE_USER = "users";
	private static final String TABLE_ACTIVITY_CALORIE_BURN = "activity_calorie_burn";

	// common column names
	private static final String COLUMN_ID = "id";

	// user table column
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_BIRTDAY = "birthday";
	private static final String COLUMN_HEIGHT = "height";
	private static final String COLUMN_WEIGHT = "weight";
	private static final String COLUMN_PHOTOGRAPH = "photograph";
	private static final String COLUMN_ID_PROFESSION = "id_profession";
	private static final String COLUMN_SEX = "sex";

	// activity_calorie_burn table column
	private static final String COLUMN_DATE = "date";
	private static final String COLUMN_TIME = "time";
	private static final String COLUMN_ACTIVITY = "activity";
	private static final String COLUMN_CALORIES = "calorie";
	private static final String COLUMN_USET_ID = "userId";

	private static final String CREATE_USERS_TABLE = "CREATE TABLE "
			+ TABLE_USER + "(" + COLUMN_ID
			+ " INTEGER PRIMARY KEY autoincrement," + COLUMN_NAME
			+ " TEXT not null," + COLUMN_BIRTDAY + " TEXT not null,"
			+ COLUMN_HEIGHT + " INTEGER not null," + COLUMN_WEIGHT
			+ " INTEGER not null," + COLUMN_PHOTOGRAPH + " TEXT,"
			+ COLUMN_ID_PROFESSION + " INTEGER not null, " + COLUMN_SEX
			+ " TEXT not null" + ");";

	private static final String CREATE_ACTIVITY_TABLE = "CREATE TABLE "
			+ TABLE_ACTIVITY_CALORIE_BURN + "(" + COLUMN_ID
			+ " INTEGER PRIMARY KEY autoincrement," + COLUMN_ACTIVITY
			+ " TEXT not null," + COLUMN_DATE + " TEXT not null," + COLUMN_TIME
			+ " INTEGER not null," + COLUMN_CALORIES + " INTEGER not null, "
			+ COLUMN_USET_ID + " INTEGER not null" + ");";

	// constructor
	public SampleDBAdapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Create table
	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_USERS_TABLE);
		db.execSQL(CREATE_ACTIVITY_TABLE);

		Log.d(LOG, "create: ");

	}

	// Drop table if older version exist
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(LOG, "update: ");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY_CALORIE_BURN);
		onCreate(db);
	}

	// Add Users
	public boolean addContactDetails(User user) {
		// Get db writable
		SQLiteDatabase db = this.getWritableDatabase();

		// Get the values to insert
		ContentValues vals = new ContentValues();
		vals.put(COLUMN_NAME, user.getName());
		vals.put(COLUMN_BIRTDAY, user.getBirthDate());
		vals.put(COLUMN_HEIGHT, user.getHeight());
		vals.put(COLUMN_WEIGHT, user.getWeight());
		vals.put(COLUMN_PHOTOGRAPH, user.getPhotograph());
		vals.put(COLUMN_ID_PROFESSION, user.getProfessionId());
		vals.put(COLUMN_SEX, user.getSex());

		// Insert values into table
		long i = db.insert(TABLE_USER, null, vals);
		db.close();
		Log.d(LOG, "value is inserted: " + i);
		Log.d(LOG,
				"inserted valuse: " + user.getName() + ", "
						+ user.getBirthDate());
		if (i != 0) {
			return true;
		} else {
			return false;
		}
	}

	// Reading all users
	public List<User> readAllContacts() {
		// Get db writable
		SQLiteDatabase db;
		try {
			db = this.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = this.getReadableDatabase();
		}

		// Define user list
		List<User> users = new ArrayList<User>();
		String selectQuery = "SELECT * FROM " + TABLE_USER;

		Cursor cursor = db.rawQuery(selectQuery, null);

		User user = null;

		if (cursor.moveToFirst()) {
			do {
				user = new User();
				user.setId(Integer.parseInt(cursor.getString(0)));
				user.setName(cursor.getString(1));
				user.setBirthDate(cursor.getString(2));
				user.setHeight(cursor.getString(3));
				user.setWeight(cursor.getString(4));
				user.setPhotograph(cursor.getString(5));
				user.setProfessionId(cursor.getString(6));
				user.setSex(cursor.getString(7));
				users.add(user);
				Log.d(LOG,
						"select all valuse: " + user.getName() + ", "
								+ user.getBirthDate());
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return users;
	}

	public User getUser(long id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
				+ COLUMN_ID + " = " + id;
		Cursor c = db.rawQuery(selectQuery, null);
		if (c != null) {
			c.moveToFirst();
		}
		User user = new User();
		user.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
		user.setBirthDate(c.getString(c.getColumnIndex(COLUMN_BIRTDAY)));
		user.setHeight(c.getString(c.getColumnIndex(COLUMN_HEIGHT)));
		user.setWeight(c.getString(c.getColumnIndex(COLUMN_WEIGHT)));
		user.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
		user.setPhotograph(c.getString(c.getColumnIndex(COLUMN_PHOTOGRAPH)));
		user.setProfessionId(c.getString(c.getColumnIndex(COLUMN_ID_PROFESSION)));
		user.setSex((c.getString(c.getColumnIndex(COLUMN_SEX))));
		c.close();
		db.close();
		return user;
	}

	// Update user
	public boolean editContact(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues vals = new ContentValues();
		vals.put(COLUMN_NAME, user.getName());
		vals.put(COLUMN_BIRTDAY, user.getBirthDate());
		vals.put(COLUMN_HEIGHT, user.getHeight());
		vals.put(COLUMN_WEIGHT, user.getWeight());
		vals.put(COLUMN_PHOTOGRAPH, user.getPhotograph());
		vals.put(COLUMN_ID_PROFESSION, user.getProfessionId());
		vals.put(COLUMN_SEX, user.getSex());

		// updating row
		int i = db.update(TABLE_USER, vals, COLUMN_ID + " = ?",
				new String[] { String.valueOf(user.getId()) });

		db.close();

		if (i != 0) {
			return true;
		} else {
			return false;
		}
	}

	// Deleting user
	public boolean deleteContact(int id) {

		deleteAllActivitiesByUser(id);

		SQLiteDatabase db = this.getWritableDatabase();
		int i = db.delete(TABLE_USER, COLUMN_ID + " = ?",
				new String[] { String.valueOf(id) });

		db.close();

		if (i != 0) {
			return true;
		} else {
			return false;
		}
	}

	public void deleteUsersTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		db.close();
	}

	// /////////////////////////////////////////////////////////////

	// Add Calories
	public boolean addActivityDetails(Calories calories) {
		// Get db writable
		SQLiteDatabase db = this.getWritableDatabase();

		// Get the values to insert
		ContentValues vals = new ContentValues();
		vals.put(COLUMN_DATE, calories.getDate());
		vals.put(COLUMN_TIME, calories.getTime());
		vals.put(COLUMN_ACTIVITY, calories.getActivitySport());
		vals.put(COLUMN_CALORIES, calories.getCalorieValue());
		vals.put(COLUMN_USET_ID, calories.getUserId());

		// Insert values into table
		long i = db.insert(TABLE_ACTIVITY_CALORIE_BURN, null, vals);
		db.close();
		Log.d(LOG, "value is inserted: " + i);
		Log.d(LOG, "inserted valuse: " + calories.getActivitySport() + ", "
				+ calories.getTime());
		if (i != 0) {
			return true;
		} else {
			return false;
		}
	}

	// Reading all activities
	public List<Calories> readAllActivities(long userId) {
		// Get db writable
		SQLiteDatabase db;
		try {
			db = this.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = this.getReadableDatabase();
		}

		// Define calories list
		List<Calories> calories = new ArrayList<Calories>();
		String selectQuery = "SELECT * FROM " + TABLE_ACTIVITY_CALORIE_BURN
				+ " WHERE " + COLUMN_USET_ID + " = " + userId;
		Cursor cursor = db.rawQuery(selectQuery, null);

		Calories calorie = null;

		if (cursor.moveToFirst()) {
			do {
				calorie = new Calories();
				calorie.setId(Integer.parseInt(cursor.getString(0)));
				calorie.setActivitySport(cursor.getString(1));
				calorie.setDate(cursor.getString(2));
				calorie.setTime(cursor.getString(3));
				calorie.setCalorieValue(cursor.getString(4));
				calorie.setUserId(cursor.getString(5));

				calories.add(calorie);
				Log.d(LOG, "select all valuse: " + calorie.getActivitySport()
						+ ", " + calorie.getTime());
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return calories;
	}

	public List<Calories> getActivityByDay(long user_id, String date) {
		SQLiteDatabase db;
		try {
			db = this.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = this.getReadableDatabase();
		}

		List<Calories> calories = new ArrayList<Calories>();
		String selectQuery = "SELECT * FROM " + TABLE_ACTIVITY_CALORIE_BURN
				+ " WHERE " + COLUMN_USET_ID + " = " + user_id + " AND "
				+ COLUMN_DATE + " = \"" + date + "\"";
		Cursor c = db.rawQuery(selectQuery, null);
		Calories calorie = null;

		if (c.moveToFirst()) {
			do {
				calorie = new Calories();
				calorie.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
				calorie.setActivitySport(c.getString(c
						.getColumnIndex(COLUMN_ACTIVITY)));
				calorie.setDate(c.getString(c.getColumnIndex(COLUMN_DATE)));
				calorie.setTime(c.getString(c.getColumnIndex(COLUMN_TIME)));
				calorie.setCalorieValue(c.getString(c
						.getColumnIndex(COLUMN_CALORIES)));
				calorie.setUserId(c.getString(c.getColumnIndex(COLUMN_USET_ID)));
				calories.add(calorie);
			} while (c.moveToNext());
		}

		c.close();
		db.close();
		return calories;
	}

	public Calories getActivity(long id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_ACTIVITY_CALORIE_BURN
				+ " WHERE " + COLUMN_ID + " = " + id;
		Cursor c = db.rawQuery(selectQuery, null);
		if (c != null) {
			c.moveToFirst();
		}
		Calories calorie = new Calories();
		calorie.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
		calorie.setActivitySport(c.getString(c.getColumnIndex(COLUMN_ACTIVITY)));
		calorie.setDate(c.getString(c.getColumnIndex(COLUMN_DATE)));
		calorie.setTime(c.getString(c.getColumnIndex(COLUMN_TIME)));
		calorie.setCalorieValue(c.getString(c.getColumnIndex(COLUMN_CALORIES)));
		calorie.setUserId(c.getString(c.getColumnIndex(COLUMN_USET_ID)));
		c.close();
		db.close();
		return calorie;
	}

	// Update activity table
	public boolean editActivity(Calories calorie) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues vals = new ContentValues();
		vals.put(COLUMN_ACTIVITY, calorie.getActivitySport());
		vals.put(COLUMN_DATE, calorie.getDate());
		vals.put(COLUMN_TIME, calorie.getTime());
		vals.put(COLUMN_CALORIES, calorie.getCalorieValue());
		vals.put(COLUMN_USET_ID, calorie.getUserId());

		// updating row
		int i = db.update(TABLE_ACTIVITY_CALORIE_BURN, vals,
				COLUMN_ID + " = ?",
				new String[] { String.valueOf(calorie.getId()) });
		Log.d("myLogs", "activity id for edit = " + calorie.getId());
		db.close();

		if (i != 0) {
			return true;
		} else {
			return false;
		}
	}

	// Deleting activity
	public boolean deleteActivity(long id) {
		SQLiteDatabase db = this.getWritableDatabase();

		int i = db.delete(TABLE_ACTIVITY_CALORIE_BURN, COLUMN_ID + " = ?",
				new String[] { String.valueOf(id) });

		db.close();

		if (i != 0) {
			return true;
		} else {
			return false;
		}
	}

	public void deleteAllActivitiesByDay(int userId, String date) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("DELETE FROM " + TABLE_ACTIVITY_CALORIE_BURN + " WHERE "
				+ COLUMN_USET_ID + " = " + userId + " AND " + COLUMN_DATE
				+ " = \"" + date + "\"");
		db.close();

	}
//////////////////////////// &&&&&&&&&&&&&&&&&&&&&&&	
	public void updateActivitiesMoreThanYear(long userId) {
		SQLiteDatabase db = this.getWritableDatabase();
		Calendar todayDate= Calendar.getInstance();
		int yearBefore=todayDate.get(Calendar.YEAR)-1;
		 
		
		Log.d("myLogs", "yearBefoe= = " + String.valueOf(yearBefore));

		db.execSQL("DELETE FROM " + TABLE_ACTIVITY_CALORIE_BURN + " WHERE "
				+ COLUMN_USET_ID + " = " + userId + " AND " + COLUMN_DATE
				+ " < \"" +"01/01/"+ yearBefore + "\"");
		db.close();

	}

	public void deleteAllActivitiesByUser(int userId) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("DELETE FROM " + TABLE_ACTIVITY_CALORIE_BURN + " WHERE "
				+ COLUMN_USET_ID + " = " + userId);
		db.close();

	}

	public void deleteActivityTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY_CALORIE_BURN);
		db.close();
	}

}
