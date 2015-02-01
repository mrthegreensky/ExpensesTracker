package com.example.expensestracker;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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
		
		TextView item = (TextView) view.findViewById(R.id.ExpenseDescription);
		/*
		Spinner expenseCAD = (Spinner) view.findViewById(R.id.ExpenseCAD);
		Spinner expenseUSD = (Spinner) view.findViewById(R.id.ExpenseUSD);
		Spinner expenseEUR = (Spinner) view.findViewById(R.id.ExpenseEUR);
		Spinner expenseGBP = (Spinner) view.findViewById(R.id.ExpenseGBP);
		*/
		String description = expenses.get(position).getDescription();
		/*
		String currency = expenses.get(position).getCurrency();
		
		if(currency == "CAD") {
			
		}
		*/
		
		
		item.setText("Description: " + description);
		//status.setText("Status: " + stat);
		
		return view;
		
	}
	
	
}
