package com.example.expensestracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ClaimStatusActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimstatus); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim_status, menu);
		return true;
	}

}
