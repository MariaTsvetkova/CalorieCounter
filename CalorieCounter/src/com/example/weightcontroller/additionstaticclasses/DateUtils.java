package com.example.weightcontroller.additionstaticclasses;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static Date getDate(String dateStr) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date startDate = null;
		try {
			startDate = (Date) formatter.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return startDate;
	}

	public static int getYear(String dateStr) {
		Date birthDate = getDate(dateStr);
		Date currentDate = new Date();
		int difference = currentDate.getYear() - birthDate.getYear();
		return difference;
	}

	public static String getDateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = "";
		dateString = sdf.format(date);
		return dateString;
	}

}
