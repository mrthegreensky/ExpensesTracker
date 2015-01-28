package com.example.expensestracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ClaimsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claims_activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claims, menu);
		return true;
	}


	
	public void CreateANewClaim(MenuItem menu) {
		Toast.makeText(this, "Create A New Claim", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(ClaimsActivity.this, DestinationActivity.class);
		startActivity(intent);
	}
	
	
}
