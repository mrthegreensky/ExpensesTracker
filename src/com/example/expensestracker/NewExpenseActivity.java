package com.example.expensestracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class NewExpenseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newexpense);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_expense, menu);
		return true;
	}

}
