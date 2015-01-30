package com.example.expensestracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

public class DestinationActivity extends Activity {
	
	
	//These attributes were borrowed and modified from lab 3 code
	private static final String FILENAME = "Claims.sav";
	private ListView ClaimsList;
	private ArrayList<Claims> claims;
	private ArrayAdapter<Claims> adapter;
	
	private DatePicker toDate;
	private DatePicker fromDate;
	
	private Integer year;
	private Integer month;
	private Integer day;
	
	private Button okButton;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.destination);
		
		ClaimsList = (ListView) findViewById(R.id.ClaimsList); //Borrowed and modified from lab 3 code	
		
		
	}
	
	public void setCurrentDateToview() {
		
		fromDate = (DatePicker)findViewById(R.id.FromDatePicker);
		toDate = (DatePicker)findViewById(R.id.ToDatePicker);
		
		final Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DAY_OF_MONTH);		
		
		fromDate.init(year, month, day, null);
		toDate.init(year, month, day, null);
		
		
	}
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.destination, menu);
		return true;
	}
	
	//loadFromFile and saveInFile methods borrowed and modified from Lab3 code. 
	private ArrayList<Claims> loadFromFile() {
		Gson gson = new Gson();
		ArrayList<String> tweets = null;
		try {
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader isr = new InputStreamReader(fis);
			
			//Based on http://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html accessed on Jan 22, at 15:58
			Type listType = new TypeToken<ArrayList<String>>() {}.getType();
			claims = gson.fromJson(isr, listType);
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(tweets == null) {
			tweets = new ArrayList<String>();
		}
		
		return claims;
	}
	
	
	private void saveInFile(String YourDescription, Date startDate, Date endDate) {
		Gson gson = new Gson();
		try {
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


/*
The following links were used for the date picker: 
http://developer.android.com/guide/topics/ui/controls/pickers.html
http://www.mkyong.com/android/android-date-picker-example/
Last accessed on January 29, 5:51 PM
Portions of this page are modifications based on work created and shared by the Android Open Source Project and used according to terms described in the Creative Commons 2.5 Attribution License. 
*/

