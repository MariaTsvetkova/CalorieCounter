package com.example.weightcontroller.activitycolories;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;

public class ActivityCaloriesParse {

	private ArrayList<ActivityCalories> coloriesActivityList = null;

	public ActivityCaloriesParse(Context context) {
		XmlPullParserFactory pullParserFactory;
		try {
			pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();

			InputStream in_s = context.getAssets()
					.open("colories_activity.xml");
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in_s, null);

			parseXML(parser);

		} catch (XmlPullParserException e) {

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseXML(XmlPullParser parser) throws XmlPullParserException,
			IOException {

		int eventType = parser.getEventType();
		ActivityCalories currentActivity = null;

		while (eventType != XmlPullParser.END_DOCUMENT) {
			String name = null;

			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				coloriesActivityList = new ArrayList();
				break;
			case XmlPullParser.START_TAG:
				name = parser.getName();
				if (name.equals("activity")) {
					currentActivity = new ActivityCalories();
				} else if (currentActivity != null) {

					if (name.equals("name")) {
						currentActivity.setName(parser.nextText());
					} else if (name.equals("weight45")) {
						currentActivity.setWeight45(Integer.valueOf(parser
								.nextText()));
					} else if (name.equals("weight57")) {
						currentActivity.setWeight57(Integer.valueOf(parser
								.nextText()));
					} else if (name.equals("weight68")) {
						currentActivity.setWeight68(Integer.valueOf(parser
								.nextText()));
					} else if (name.equals("weight79")) {
						currentActivity.setWeight79(Integer.valueOf(parser
								.nextText()));
					} else if (name.equals("weight91")) {
						currentActivity.setWeight91(Integer.valueOf(parser
								.nextText()));
					} else if (name.equals("weight102")) {
						currentActivity.setWeight102(Integer.valueOf(parser
								.nextText()));
					} else if (name.equals("weight113")) {
						currentActivity.setWeight113(Integer.valueOf(parser
								.nextText()));
					} else if (name.equals("weight125")) {
						currentActivity.setWeight125(Integer.valueOf(parser
								.nextText()));
					} else if (name.equals("weight136")) {
						currentActivity.setWeight136(Integer.valueOf(parser
								.nextText()));
					} else if (name.equals("weight147")) {
						currentActivity.setWeight147(Integer.valueOf(parser
								.nextText()));
					}
				}
				break;
			case XmlPullParser.END_TAG:
				name = parser.getName();
				if (name.equalsIgnoreCase("activity")
						&& currentActivity != null) {
					coloriesActivityList.add(currentActivity);
				}
			}
			eventType = parser.next();
		}

	}

	public ArrayList<ActivityCalories> getColoriesActivityList() {
		return coloriesActivityList;
	}

	public void setColoriesActivityList(
			ArrayList<ActivityCalories> coloriesActivityList) {
		this.coloriesActivityList = coloriesActivityList;
	}

}
