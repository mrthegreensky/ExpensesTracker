package com.example.expensestracker;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ExpenseActivity extends Activity {

	
	private int claimPosition;
	private ListView ExpenseList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_activity);
		
		
		ExpenseList = (ListView) findViewById(R.id.ExpenseList); //this line of code was borrowed and modified from lab 3 code 
		
		//Borrowed and modified from http://stackoverflow.com/questions/21283636/create-a-context-menu-when-click-long-in-a-custom-listview
		//Last accessed Jan 31, 2015 at 11:38 AM
		registerForContextMenu(ExpenseList);
		

		Bundle extras = this.getIntent().getExtras();
		claimPosition = extras.getInt("claimPosition");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expense, menu);
		return true;
	}

	public void AddExpense(MenuItem menu) {
		Toast.makeText(this, "Add Expense", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(ExpenseActivity.this, NewExpenseActivity.class);
		intent.putExtra("claimPosition", claimPosition);
		startActivity(intent);
	}
	
	
	
	
	
	
	
	
}
