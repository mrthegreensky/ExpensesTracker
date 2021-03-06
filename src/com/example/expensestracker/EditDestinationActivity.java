package com.example.expensestracker;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


public class EditDestinationActivity extends DestinationActivity {
	private static final String FILENAME = "Claims.sav";
	private ArrayList<Claims> claims;
	private int position;
	private Claims oldclaim;
	
	private DatePicker toDate;
	private DatePicker fromDate;
	private Button okButton;
	private EditText userDestination;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.destination);
		
		okButton = (Button) findViewById(R.id.SubmitDestination);
		userDestination = (EditText) findViewById(R.id.DestinationText);
		
		//Use of bundle borrowed and modified from http://stackoverflow.com/questions/5424135/passing-integer-between-activities-and-intents-in-android-is-always-resulting-in
		//Last accessed Feb 1, 2015 at 10:46 AM
		Bundle extras = this.getIntent().getExtras();
		
		position = extras.getInt("position");

		claims = loadFromFile();
		oldclaim = claims.get(position);
		userDestination.setText(oldclaim.getYourDestination());
		setCurrentDateToview();
		
		
		//Have to check if button is pressed  and if there is input in the Destination EditText
		//Uses anonymous function, borrowed and modified from LonelyTwitterActivity
		if(userDestination.toString().trim().length() > 0) {
			
			okButton.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					
					fromDate = (DatePicker)findViewById(R.id.FromDatePicker);
					toDate = (DatePicker)findViewById(R.id.ToDatePicker);
					Claims newClaim = new Claims(userDestination.getText().toString(), getDate(toDate), getDate(fromDate));
					claims.remove(position);
					claims.add(position, newClaim);
					saveInFile(claims);
					finish();
					}
			});
		
		}
		
		
	}
	
	//Shows DatePicker from claim that was previously created 
	public void setCurrentDateToview() {
		
		fromDate = (DatePicker)findViewById(R.id.FromDatePicker);
		toDate = (DatePicker)findViewById(R.id.ToDatePicker);
		
		Date startDate = oldclaim.getStartDate();
		Date endDate = oldclaim.getEndDate();
		
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		
		startCalendar.setTime(startDate);
		endCalendar.setTime(endDate);
		
		int syear = startCalendar.get(Calendar.YEAR);
		int smonth = startCalendar.get(Calendar.MONTH);
		int sday = startCalendar.get(Calendar.DAY_OF_MONTH);		
		
		int eyear = endCalendar.get(Calendar.YEAR);
		int emonth = endCalendar.get(Calendar.MONTH);
		int eday = endCalendar.get(Calendar.DAY_OF_MONTH);
	
		
		toDate.init(syear, smonth, sday, null);
		fromDate.init(eyear, emonth, eday, null);
		
	}
	
	//Deletes the file and resaves the claim. Does not append to the previous claim in the file
	protected void saveInFile(ArrayList<Claims> claims) {
		Gson gson = new Gson();
		try {
			deleteFile(FILENAME);
			FileOutputStream fos = openFileOutput(FILENAME, 0); // zero is the default thing that will clear it when it opens 
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(claims, osw);
			osw.flush(); // flush after writing
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
