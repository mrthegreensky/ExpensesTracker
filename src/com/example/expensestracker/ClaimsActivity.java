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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ClaimsActivity extends Activity {

	//the following attributes were borrowed and modified from lab 3 code
	private static final String FILENAME = "Claims.sav";
	private ListView ClaimsList;
	private ArrayList<Claims> claims;
	private ArrayAdapter<Claims> adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claims_activity);
		
		ClaimsList = (ListView) findViewById(R.id.ClaimsList); //this line of code was borrowed and modified from lab 3 code 
		
		/*
		ClaimsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			
			public boolean onItemLongClick(AdapterView<?> arg0, View v, int index, long arg3) {
				
				
				return true;
			}
			
		});
		*/
		
		//Borrowed and modified from http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/
		//Last accessed on January 30 
		//registerForContextMenu(ClaimsList);
		
		
	}

	//The following method was borrowed and modified from lab 3 code 
	protected void onStart() {
		
		super.onStart();
		claims = loadFromFile(); //Load the ArrayList so we can keep the previous list and just append and save

		adapter = new ArrayAdapter<Claims>(this, R.layout.claims_list, claims);
		ClaimsList.setAdapter(adapter);
	}
	
	/*
	public boolean onLongClick(View v) {
		return true;
	}
	*/
	
	
	//Borrowed and modified from http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/
	//Last accessed on January 30 
	public void onCreateTextMenu(ContextMenu contextMenu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(contextMenu, view, menuInfo);	
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		contextMenu.setHeaderTitle(claims.get(info.position).getYourDestination());
		String[] menuItems = getResources().getStringArray(R.array.Claims_Menu);
		for(int i = 0; i < menuItems.length; i++) {
			contextMenu.add(Menu.NONE, i, i, menuItems[i]);
		}
	}
	
	/*
	public void onContextMenuSelected(MenuItem item) {
		switch(item.getItemId()) {
		case Edit:
			
		}
	}
	*/
	
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