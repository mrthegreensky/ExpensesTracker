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
public class ExpenseArrayAdapter extends ArrayAdapter<Expense> {

	ArrayList<Expense> expenses;
	
	
	public ExpenseArrayAdapter(Context context, ArrayList<Expense> expenses) {
		super(context, R.layout.expense_activity, expenses);
		
		this.expenses = expenses;
	}
	

	@Override
	public Expense getItem(int position) {
		return expenses.get(position);
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		if(view == null) {
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.expenses_list, null);
		}
		
		TextView desc = (TextView) view.findViewById(R.id.ExpenseDescription);
		TextView expense = (TextView) view.findViewById(R.id.Expense);
		TextView expenseDate = (TextView) view.findViewById(R.id.ExpenseDate);
		
		
		String description = expenses.get(position).getDescription();
		String cost = expenses.get(position).getCost().toString();
		String currency = expenses.get(position).getCurrency();
		
		//Displaying the cost of the expense with the respective currency of the cost
		desc.setText("Expense: " + description);
		if(currency.equals("CAD")) {
			expense.setText("CAD: " + cost);
		} else if (currency.equals("USD")) {
			expense.setText("USD: " + cost);
		} else if (currency.equals("EUR")) {
			expense.setText("EUR: " + cost);
		} else if (currency.equals("GBP")) {
			expense.setText("GBP: " + cost);
		}
		
		Date date = expenses.get(position).getDate();
		String sDate = DateFormat.format("yyyy-MM-dd", date).toString();
		expenseDate.setText("Date: " + sDate);
		
		return view;
		
	}
	
}
