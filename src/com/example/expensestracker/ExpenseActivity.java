package com.example.expensestracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ExpenseActivity extends Activity {

	
	private static final String FILENAME = "Claims.sav";
	private ArrayList<Claims> claims;
	private ExpenseArrayAdapter adapter;
	
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
	
	protected void onStart() {
		
		super.onStart();
		claims = loadFromFile(); //Load the ArrayList so we can keep the previous list and just append and save

		adapter = new ExpenseArrayAdapter(ExpenseActivity.this, claims.get(claimPosition).getExpenses());
		ExpenseList.setAdapter(adapter);
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
	
	private void saveInFile(ArrayList<Claims> claims) {
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
