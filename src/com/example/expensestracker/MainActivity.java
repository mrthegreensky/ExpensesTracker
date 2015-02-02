package com.example.expensestracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView Claims =(TextView)findViewById(R.id.ClaimsExpenses);
		if(Claims!= null) {
			Claims.setOnClickListener(this);
		}
	}
	
	
	@Override
	public void onClick(View selectedView) {
		if(selectedView.getId() == R.id.ClaimsExpenses) {
			Intent intent = new Intent(MainActivity.this, ClaimsActivity.class);
			Toast.makeText(this, "Your claims", Toast.LENGTH_SHORT).show();
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
}
