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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ExpenseActivity extends Activity {

	
	private static final String FILENAME = "Claims.sav";
	private ArrayList<Claims> claims;
	private ExpenseArrayAdapter adapter;
	private String status;
	private boolean edit = false;
	private int claimPosition;
	private ListView ExpenseList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_activity);
		
		
		ExpenseList = (ListView) findViewById(R.id.ExpenseList); //this line of code was borrowed and modified from lab 3 code 
		
		//Borrowed and modified from http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/
		//Last accessed Feb 1, 8:08 PM
		registerForContextMenu(ExpenseList);
		

		Bundle extras = this.getIntent().getExtras();
		claimPosition = extras.getInt("claimPosition");
		status = extras.getString("status");
		if(!(status.equals("Pending Review") || status.equals("Accepted"))) {
			edit = true;
		}
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
		if(edit) {
		Toast.makeText(this, "Add Expense", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(ExpenseActivity.this, NewExpenseActivity.class);
		intent.putExtra("claimPosition", claimPosition);
		startActivity(intent);
		} else {
			Toast.makeText(this, "Cannot add new expense", Toast.LENGTH_SHORT).show();
		}
	}
	
	

	//Borrowed and modified from http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/
	//Last accessed Feb 1, 8:08 PM
	public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(contextMenu, view, menuInfo);	
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
		
		contextMenu.setHeaderTitle(claims.get(claimPosition).getExpenses().get(info.position).getDescription());
		contextMenu.add(0, view.getId(), 0,"Edit");
		contextMenu.add(0, view.getId(), 1, "Delete");
		
	}
	
	
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		if(edit) {
			if(item.getTitle() == "Edit") {
				Toast.makeText(this, "Edit expense", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(ExpenseActivity.this, EditExpenseActivity.class);
				intent.putExtra("claimPosition", claimPosition);
				intent.putExtra("expensePosition", info.position);
				startActivity(intent);
				
			//Use of bundle borrowed and modified from http://stackoverflow.com/questions/14333449/passing-data-through-intent-using-serializable
			//Last accessed Jan 31, 2015 at 2:33 PM
			} else if(item.getTitle() == "Delete") {
	
				Toast.makeText(this, "Deleted expense", Toast.LENGTH_SHORT).show();
				adapter.remove(claims.get(claimPosition).getExpenses().get(info.position));
				
			} else {
				return false;
				
			}
			
			saveInFile(claims);
			adapter.notifyDataSetChanged();
			return true;
		} else {
			Toast.makeText(this, "Cannot edit or delete claims at this moment", Toast.LENGTH_SHORT).show();
			return false;
		}
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
			//deleteFile(FILENAME);
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
