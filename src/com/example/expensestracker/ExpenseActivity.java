package com.example.expensestracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ExpenseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_activity);
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
		startActivity(intent);
	}
}
