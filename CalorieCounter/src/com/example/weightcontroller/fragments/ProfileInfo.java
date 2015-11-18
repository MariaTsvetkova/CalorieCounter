package com.example.weightcontroller.fragments;


import com.example.weightcontroller.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProfileInfo extends Fragment {
	
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		
		view=inflater.inflate(R.layout.user_details, container, false);
		
		
		return view;
		
		             
	}

}
