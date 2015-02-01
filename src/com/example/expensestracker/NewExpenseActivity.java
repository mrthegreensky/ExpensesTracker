package com.example.expensestracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class NewExpenseActivity extends Activity {
	
	
	
	private static final String FILENAME = "Claims.sav";
	private ListView ClaimsList;
	private ArrayList<Claims> claims;
	private CustomArrayAdapter adapter;
	
	private Claims newClaim;
	private int claimPosition;
	
	private EditText descriptionText;
	private EditText amountText;
	private Spinner currencySpinner;
	
	private DatePicker date;
	private Date theDate;
	
	private Integer year;
	private Integer month;
	private Integer day;
	
	private Button okButton;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newexpense);
		
		descriptionText = (EditText) findViewById(R.id.DescriptionText);
		amountText = (EditText) findViewById(R.id.AmountText);
		date = (DatePicker) findViewById(R.id.datePickerExpense);
		currencySpinner = (Spinner) findViewById(R.id.CurrencySpinner);
		okButton = (Button) findViewById(R.id.button1);
		

		Bundle extras = this.getIntent().getExtras();
		claimPosition = extras.getInt("claimPosition");
		
		//Have to check if button is pressed  and if there is input in the Destination EditText
		//Uses anonymous function, borrowed and modified from LonelyTwitterActivity
		if(descriptionText.toString().trim().length() > 0) {
			
			okButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					claims = loadFromFile();
					newClaim = claims.get(claimPosition);
					String stringAmount = amountText.getText().toString();
					Double amount = Double.parseDouble(stringAmount);
					newClaim.addExpense(new Expense(amount, currencySpinner.getSelectedItem().toString(), descriptionText.getText().toString(), getDate(date)));
					claims.remove(claimPosition);
					claims.add(claimPosition, newClaim);
					saveInFile(claims);
					finish();
					}
			});
		}
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_expense, menu);
		return true;
	}
	
	//loadFromFile and saveInFile methods borrowed and modified from Lab3 code. 
	private ArrayList<Claims> loadFromFile() {
		Gson gson = new Gson();
		ArrayList<Claims> claims = null;
		try {
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader isr = new InputStreamReader(fis);
			
			//Based on http://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html accessed on Jan 22, at 15:58
			Type listType = new TypeToken<ArrayList<Claims>>() {}.getType();
			claims = gson.fromJson(isr, listType);
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(claims == null) {
			claims = new ArrayList<Claims>();
		}
		
		return claims;
	}
	
	public Date getDate(DatePicker date) {
		year = date.getYear();
		month = date.getMonth();
		day = date.getDayOfMonth();
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		
		return calendar.getTime();
		
	}
	
	
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
