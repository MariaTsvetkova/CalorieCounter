package com.example.weightcontroller.fragments;

import com.example.weightcontroller.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CalendarFragment extends Fragment {
	
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.body_mass_index, container, false);
		return view;
	}

}
