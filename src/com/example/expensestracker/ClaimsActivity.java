package com.example.expensestracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

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
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
	
	
	//Following two methods orrowed and modified from http://stackoverflow.com/questions/21283636/create-a-context-menu-when-click-long-in-a-custom-listview
	//Last accesed Jan 31 2015 at 11:38 AM
	public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(contextMenu, view, menuInfo);	
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		
		contextMenu.setHeaderTitle(claims.get(info.position).getYourDestination());
		contextMenu.add(0,view.getId(), 0, "Expenses");
		contextMenu.add(0, view.getId(), 1,"Edit");
		contextMenu.add(0, view.getId(), 2, "Delete");
		contextMenu.add(0, view.getId(), 3, "Submit");
		
	}
	
	
	public boolean onContextItemSelected(MenuItem item) {
		if(item.getTitle() == "Expenses") {
			
		} else if(item.getTitle() == "Edit") {
			
		} else if(item.getTitle() == "Delete") {
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			adapter.remove(claims.get(info.position));
			claims.remove(info.position);
		} else if(item.getTitle() == "Submit") {
			
		} else {
			return false;
		}
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
	
	
}