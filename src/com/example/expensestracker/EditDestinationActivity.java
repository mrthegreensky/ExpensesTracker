package com.example.expensestracker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class EditDestinationActivity extends DestinationActivity {
	private static final String FILENAME = "Claims.sav";
	private ListView ClaimsList;
	private ArrayList<Claims> claims;
	
	private DatePicker toDate;
	private DatePicker fromDate;
	
	private Integer year;
	private Integer month;
	private Integer day;
	
	private Button okButton;
	private EditText userDestination;

	private Claims oldClaim;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.destination);
		
		ClaimsList = (ListView) findViewById(R.id.ClaimsList); //Borrowed and modified from lab 3 code	
		okButton = (Button) findViewById(R.id.SubmitDestination);
		userDestination = (EditText) findViewById(R.id.DestinationText);
		claims = new ArrayList<Claims>();
		
		
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		oldClaim = (Claims)bundle.getSerializable("Claim");
		
		//userDestination.setText(oldClaim.getYourDestination());
		//userDestination.setText(oldClaim.getYourDestination(), EditText.BufferType.EDITABLE);
		
		//Have to check if button is pressed  and if there is input in the Destination EditText
		//Uses anonymous function, borrowed and modified from LonelyTwitterActivity
		if(userDestination.toString().trim().length() > 0) {
			
			okButton.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					claims = loadFromFile();
					fromDate = (DatePicker)findViewById(R.id.FromDatePicker);
					toDate = (DatePicker)findViewById(R.id.ToDatePicker);
					Claims newClaim = new Claims(userDestination.getText().toString(), getDate(toDate), getDate(fromDate));
					claims.add(newClaim);
					saveInFile(claims);
					finish();
					}
			});
		
		}
		
		
	}
	
	
	public void onStart() {
		super.onStart();

		
		
		//setCurrentDateToview();
		
	}
	
	public void setCurrentDateToview() {
		
		fromDate = (DatePicker)findViewById(R.id.FromDatePicker);
		toDate = (DatePicker)findViewById(R.id.ToDatePicker);
		
		Date startDate = oldClaim.getStartDate();
		Date endDate = oldClaim.getEndDate();
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		endCalendar.setTime(endDate);
		
		int syear = startCalendar.get(Calendar.YEAR);
		int smonth = startCalendar.get(Calendar.MONTH) + 1;
		int sday = startCalendar.get(Calendar.DAY_OF_MONTH);		
		
		int eyear = endCalendar.get(Calendar.YEAR);
		int emonth = endCalendar.get(Calendar.MONTH) + 1;
		int eday = startCalendar.get(Calendar.DAY_OF_MONTH);
		
		//Need to change to avoid deprecated
		fromDate.init(syear, smonth, sday, null);
		toDate.init(eyear, emonth, eday, null);
		
		
	}
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_destination, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	*/
}
