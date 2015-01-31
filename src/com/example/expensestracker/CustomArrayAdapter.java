package com.example.expensestracker;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


//Adapted from http://theopentutorials.com/tutorials/android/listview/android-custom-listview-with-image-and-text-using-arrayadapter/
//Last accessed Jan 31, at 12:00PM
public class CustomArrayAdapter extends ArrayAdapter<Claims> {

	private ArrayList<Claims> claims = null;
	
	public CustomArrayAdapter(Context context, ArrayList<Claims> claims) {
		super(context, R.layout.claims_list, claims);
		
		this.claims = claims;
	}
	
	@Override
	public Claims getItem(int position) {
		return claims.get(position);
	}
	
	public void Submit(int position, Claims newClaim) {
		newClaim.setSubmit();
		claims.remove(position);
		claims.add(position, newClaim);
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		if(view == null) {
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.claims_list, null);
		}
		
		TextView item = (TextView) view.findViewById(R.id.ClaimName);
		TextView status = (TextView) view.findViewById(R.id.Status);
		
		String dest = claims.get(position).getYourDestination();
		String stat = claims.get(position).getStatus();
		
		item.setText("Destination: " + dest);
		status.setText("Status: " + stat);
		
		return view;
		
	}
	
}
