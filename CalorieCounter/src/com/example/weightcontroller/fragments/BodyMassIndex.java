package com.example.weightcontroller.fragments;

import java.util.Objects;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weightcontroller.MainCaloriesController;
import com.example.weightcontroller.R;
import com.example.weightcontroller.additionstaticclasses.DateUtils;
import com.example.weightcontroller.database.SampleDBAdapter;
import com.example.weightcontroller.database.User;

public class BodyMassIndex extends Fragment {

	private ImageView ivIcon;
	private TextView tvItemName;

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";
	private OnDataPass dataPasser;
	private User user;
	private View view;
	private RadioGroup radioSexGroup;

	public interface OnDataPass {
		public void onDataPass(String data);
	}

	public BodyMassIndex() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.body_mass_index, container, false);

		ivIcon = (ImageView) view.findViewById(R.id.frag1_icon);
		tvItemName = (TextView) view.findViewById(R.id.frag1_text);

		tvItemName.setText(getArguments().getString(ITEM_NAME));
		ivIcon.setImageDrawable(view.getResources().getDrawable(
				getArguments().getInt(IMAGE_RESOURCE_ID)));
		user = ((MainCaloriesController) getActivity()).getUser();
		radioSexGroup = (RadioGroup) view.findViewById(R.id.radioSex);

		onDataPass(tvItemName.getText().toString());
		initUI();
		setSex();
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		dataPasser = (OnDataPass) activity;

	}

	public void onDataPass(String data) {
		dataPasser.onDataPass(data);
	}

	public void initUI() {
		TextView tv_height = (TextView) view.findViewById(R.id.tv_height);
		tv_height.setText("Height: " + user.getHeight() + " cm");
		TextView tv_weight = (TextView) view.findViewById(R.id.tv_weight);
		tv_weight.setText("Weight: " + user.getWeight() + " kg");
		tv_height.setBackgroundColor(Color.GREEN);
		tv_weight.setBackgroundColor(Color.GREEN);
		int age = DateUtils.getYear(user.getBirthDate());

		if (age <= 25 && age >= 18) {
			TextView tv_25Age = (TextView) view.findViewById(R.id.tv_25Age);
			tv_25Age.setBackgroundColor(Color.GREEN);
		} else if (age > 20 && age <= 46) {
			TextView tv_46Age = (TextView) view.findViewById(R.id.tv_46Age);
			tv_46Age.setBackgroundColor(Color.GREEN);
		} else if (age >= 47 && age <= 65) {
			TextView tv_65Age = (TextView) view.findViewById(R.id.tv_65Age);
			tv_65Age.setBackgroundColor(Color.GREEN);
		}
		double weight = Double.valueOf(user.getWeight());
		double height = Double.valueOf(user.getHeight());
		double massIndex = weight / (Math.pow((height / 100.0), 2));

		massIndex = Math.rint(massIndex * 100) / 100;
		String massIndexString = "Your body mass index is "
				+ String.valueOf(massIndex) + " and it is ";

		if (isMaleChecked()) {
			massIndexString += checkMaleMassIndex(massIndex);
		} else {
			massIndexString += checkFemaleMassIndex(massIndex);
		}
		tvItemName.setText(massIndexString);

	}

	private String checkFemaleMassIndex(double massIndex) {
		if (massIndex < 19) {
			return "Underweight";
		} else if (massIndex < 24 && massIndex >= 19) {
			return "Normal (healthy weight)";
		} else if (massIndex < 30 && massIndex >= 24) {
			return " Small overweight";
		} else if (massIndex >= 30 && massIndex < 40) {
			return "Overweight";
		}

		return "Very severely obese";
	}

	private String checkMaleMassIndex(double massIndex) {
		if (massIndex < 20) {
			return "Underweight";
		} else if (massIndex < 25 && massIndex >= 20) {
			return "Normal (healthy weight)";
		} else if (massIndex < 30 && massIndex >= 25) {
			return " Small overweight";
		} else if (massIndex >= 30 && massIndex < 49) {
			return "Overweight";
		}

		return "Very severely obese";
	}

	public boolean isMaleChecked() {
		RadioButton radioBtn = (RadioButton) view.findViewById(R.id.radioMale);
		if (radioBtn.isChecked()) {
			return true;
		}
		return false;
	}

	public void setSex() {
		radioSexGroup.clearCheck();

		RadioButton maleRadioBtn = (RadioButton) view
				.findViewById(R.id.radioMale);
		RadioButton femaleRadioBtn = (RadioButton) view
				.findViewById(R.id.radioFemale);
		if (user.getSex().toString().equalsIgnoreCase(maleRadioBtn.getText().toString())) {
			maleRadioBtn.setChecked(true);
			maleRadioBtn.setTextColor(Color.GREEN);
			femaleRadioBtn.setTextColor(Color.WHITE);
			

		} else {
			femaleRadioBtn.setChecked(true);
			maleRadioBtn.setTextColor(Color.WHITE);
			femaleRadioBtn.setTextColor(Color.GREEN);

		}

	}

	@Override
	public void onResume() {
		super.onResume();
		SampleDBAdapter handler = new SampleDBAdapter(view.getContext());
		user = handler.getUser(user.getId());
		setSex();
		initUI();
	}

}
