package com.example.expensestracker;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
		
		TextView desc = (TextView) view.findViewById(R.id.ExpenseDescription);
		
		TextView expense = (TextView) view.findViewById(R.id.Expense);
		
		String description = expenses.get(position).getDescription();
		String cost = expenses.get(position).getCost().toString();
		String currency = expenses.get(position).getCurrency();
		
		//Displaying the cost of the expense with the respective currency of the cost
		desc.setText("Description: " + description);
		if(currency.equals("CAD")) {
			expense.setText("CAD: " + cost);
		} else if (currency.equals("USD")) {
			expense.setText("USD: " + cost);
		} else if (currency.equals("EUR")) {
			expense.setText("EUR: " + cost);
		} else if (currency.equals("GBP")) {
			expense.setText("GBP: " + cost);
		}
		
		return view;
		
	}
	
}
