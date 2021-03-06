package com.example.expensestracker;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


//Adapted from http://theopentutorials.com/tutorials/android/listview/android-custom-listview-with-image-and-text-using-arrayadapter/
//and https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
//Last accessed Feb 1, 2015 at 8:16 PM
//This ArrayAdapter is for ClaimsActivity
public class CustomArrayAdapter extends ArrayAdapter<Claims> {

	private ArrayList<Claims> claims = null;
	
	private Double CAD = 0.0;
	private Double USD = 0.0;
	private Double EUR = 0.0;
	private Double GBP = 0.0;
	
	
	public CustomArrayAdapter(Context context, ArrayList<Claims> claims) {
		super(context, R.layout.claims_list, claims);
		
		this.claims = claims;
	}
	
	
	@Override
	public Claims getItem(int position) {
		return claims.get(position);
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		if(view == null) {
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.claims_list, null);
		}
		
		TextView name = (TextView) view.findViewById(R.id.ClaimName);
		TextView status = (TextView) view.findViewById(R.id.Status);
		
		TextView expenseCAD = (TextView) view.findViewById(R.id.ExpenseCAD);
		TextView expenseUSD = (TextView) view.findViewById(R.id.ExpenseUSD);
		TextView expenseEUR = (TextView) view.findViewById(R.id.ExpenseEUR);
		TextView expenseGBP = (TextView) view.findViewById(R.id.ExpenseGBP);
		
		TextView startDate = (TextView) view.findViewById(R.id.startDate);
		TextView endDate = (TextView) view.findViewById(R.id.endDate);
		
		//Getting the costs of each expense in the Claim
		ArrayList<Expense> claim = claims.get(position).getExpenses();
		for(Expense expense: claim) {
			String currency = expense.getCurrency();
			if(currency.equals("CAD")) {
				CAD+= expense.getCost();
			} else if (currency.equals("USD")) {
				USD+= expense.getCost();
			} else if (currency.equals("EUR")) {
				EUR+= expense.getCost();
			} else if (currency.equals("GBP")) {
				GBP+= expense.getCost();
			}
		}
		
		expenseCAD.setText("CAD: " + Double.toString(CAD));
		expenseUSD.setText("USD: " + Double.toString(USD));
		expenseEUR.setText("EUR: " + Double.toString(EUR));
		expenseGBP.setText("GBP: " + Double.toString(GBP));
		
		resetDoubles();
		String dest = claims.get(position).getYourDestination();
		String stat = claims.get(position).getStatus();
		
		Date StartDate = claims.get(position).getStartDate();
		Date EndDate = claims.get(position).getEndDate();
		String sDate = DateFormat.format("yyyy-MM-dd", StartDate).toString();
		String eDate = DateFormat.format("yyyy-MM-dd", EndDate).toString();
		
		name.setText("Claim: " + dest);
		status.setText("Status: " + stat);
		startDate.setText("Start date: " + sDate);
		endDate.setText("End date: " + eDate);
		return view;
	}
	
	
	//Resets the values of the Doubles so that they don't carry onto the next ListView
	//Corrected problem of the double values taking the previous claims expenses and adding their own to it
	private void resetDoubles() {
		CAD = 0.0;
		USD = 0.0;
		EUR = 0.0;
		GBP = 0.0;
	}
	
	
	//Sets status of claim to be submit
	public void Submit(int position, Claims newClaim) {
		newClaim.setSubmit();
		claims.remove(position);
		claims.add(position, newClaim);
	}
	
}
