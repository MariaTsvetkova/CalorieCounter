package com.example.weightcontroller.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weightcontroller.MainCaloriesController;
import com.example.weightcontroller.R;
import com.example.weightcontroller.database.SampleDBAdapter;
import com.example.weightcontroller.database.User;

public class IdealWeight extends Fragment {
	private View view;
	private User user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.ideal_weight_fragment, container,
				false);
		user = ((MainCaloriesController) getActivity()).getUser();
	

		return view;
	}

	public String getResultByBrock() {
		String result;
		double weight = Double.valueOf(user.getWeight());
		double height = Double.valueOf(user.getHeight());
		double minusNumber = 0.0;
		if (user.getSex().equals("male")) {
			minusNumber = 100.0;
		} else {
			minusNumber = 110.0;
		}
		result = String.valueOf(Math
				.rint(((height - minusNumber) * 1.15) * 100) / 100);
		return result;
	}

	public String getResultByLorentz() {
		String result;
		if (user.getSex().equalsIgnoreCase("male")) {
			result = "-";
			
		} else {
			double weight = Double.valueOf(user.getWeight());
			double height = Double.valueOf(user.getHeight());
			double minusNumber = 0.0;
			double resultValue = (height - 100.0) - (height - 150.0) / 2;
			result = String.valueOf(Math.rint(resultValue * 100) / 100);
		}
		return result;

	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		SampleDBAdapter handler = new SampleDBAdapter(view.getContext());
		user = handler.getUser(user.getId());
		initUI();
	}

	private void initUI() {
		TextView tv_brock = (TextView) view.findViewById(R.id.tv_brock);
		TextView tv_loretz = (TextView) view.findViewById(R.id.tv_lorentz);
		tv_brock.setText(getResultByBrock());
		tv_loretz.setText(getResultByLorentz());
		
	}

}
