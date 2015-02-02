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
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

public class ClaimsActivity extends Activity {

	//the following attributes were borrowed and modified from lab 3 code
	private static final String FILENAME = "Claims.sav";
	private ListView ClaimsList;
	private ArrayList<Claims> claims;
	private CustomArrayAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claims_activity);
		
		ClaimsList = (ListView) findViewById(R.id.ClaimsList); //this line of code was borrowed and modified from lab 3 code 
		
		//Borrowed and modified from http://stackoverflow.com/questions/21283636/create-a-context-menu-when-click-long-in-a-custom-listview
		//Last accessed Jan 31, 2015 at 11:38 AM
		registerForContextMenu(ClaimsList);
		
		
	}

	//The following method was borrowed and modified from lab 3 code 
	protected void onStart() {
		
		super.onStart();
		claims = loadFromFile(); //Load the ArrayList so we can keep the previous list and just append and save

		adapter = new CustomArrayAdapter(ClaimsActivity.this, claims);
		ClaimsList.setAdapter(adapter);
	}
	
	
	//Following two methods borrowed and modified from http://stackoverflow.com/questions/21283636/create-a-context-menu-when-click-long-in-a-custom-listview
	//Last accessed Jan 31 2015 at 11:38 AM
	public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(contextMenu, view, menuInfo);	
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
		
		contextMenu.setHeaderTitle(claims.get(info.position).getYourDestination());
		contextMenu.add(0,view.getId(), 0, "Expenses");
		contextMenu.add(0, view.getId(), 1,"Edit");
		contextMenu.add(0, view.getId(), 2, "Delete");
		contextMenu.add(0, view.getId(), 3, "Submit");
		
	}
	
	
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		if(item.getTitle() == "Expenses") {
			Intent intent = new Intent(ClaimsActivity.this, ExpenseActivity.class);
			intent.putExtra("claimPosition", info.position);
			startActivity(intent);
			
		//Use of bundle borrowed and modified from http://stackoverflow.com/questions/14333449/passing-data-through-intent-using-serializable
		//Last accessed Jan 31, 2015 at 2:33 PM
		} else if(item.getTitle() == "Edit") {
			Intent intent = new Intent(ClaimsActivity.this, EditDestinationActivity.class);
			
			intent.putExtra("position", info.position);
			
			startActivity(intent);
			
		} else if(item.getTitle() == "Delete") {
			adapter.remove(claims.get(info.position));
			
		} else if(item.getTitle() == "Submit") {
			Claims claim = adapter.getItem(info.position);
			
			adapter.Submit(info.position, claim);
		} else {
			return false;
			
		}
		
		saveInFile(claims);
		adapter.notifyDataSetChanged();
		return true;
		
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