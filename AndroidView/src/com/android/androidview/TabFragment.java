package com.android.androidview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment extends Fragment {
	private TextView textview;
	private String mTitle;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragmentview,null);
		  if (getArguments() != null)  
	        {  
	            mTitle = getArguments().getString("title");  
	        } 
		textview=(TextView) view.findViewById(R.id.my_text);
		textview.setText(mTitle);
		return view;
	}
}
