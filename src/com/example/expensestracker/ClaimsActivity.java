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
import android.text.format.DateFormat;
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
		
		//Borrowed and modified from http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/
		//Last accessed Feb 1, 8:08 PM
		registerForContextMenu(ClaimsList);
	}

	
	//The following method was borrowed and modified from lab 3 code 
	protected void onStart() {
		
		super.onStart();
		claims = loadFromFile(); //Load the ArrayList so we can keep the previous list and just append and save

		adapter = new CustomArrayAdapter(ClaimsActivity.this, claims);
		ClaimsList.setAdapter(adapter);
	}
	
	
	//Borrowed and modified from http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/
	//Last accessed Feb 1, 8:08 PM
	//Menus 4 and 5 ONLY show when the claim has been submitted
	//Should use r.string's to define the strings
	//would be implemented in future versions
	public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(contextMenu, view, menuInfo);	
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
		String status = claims.get(info.position).getStatus();
		
		
		contextMenu.setHeaderTitle(claims.get(info.position).getYourDestination());
		contextMenu.add(0,view.getId(), 0, "Expenses");
		contextMenu.add(0, view.getId(), 1,"Edit");
		contextMenu.add(0, view.getId(), 2, "Delete");
		contextMenu.add(0, view.getId(), 3, "Submit");
		contextMenu.add(0, view.getId(), 4, "Email claim");
		if((status.equals("Pending Review"))) {
			contextMenu.add(0, view.getId(), 5, "Set claim as rejected");
			contextMenu.add(0, view.getId(), 6, "Set claim as accepted");
		}
		
	}
	
	
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		String status = claims.get(info.position).getStatus();
		if(item.getTitle() == "Expenses") {
			Toast.makeText(this, "Your expenses", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(ClaimsActivity.this, ExpenseActivity.class);
			intent.putExtra("claimPosition", info.position);
			intent.putExtra("status", status);
			startActivity(intent);
			
		} else if(!(status == "Accepted")) {
			if(item.getTitle() == "Set claim as rejected") {
				Toast.makeText(this, "Rejecting this claim", Toast.LENGTH_SHORT).show();
				claims.get(info.position).setReject();
			} else if(item.getTitle() == "Set claim as accepted") {
				Toast.makeText(this, "Accepting this claim", Toast.LENGTH_SHORT).show();
				claims.get(info.position).setAccept();
			}
		}
		
		//This fragment is borrowed and modified from https://github.com/aktse/akt-notes/blob/master/akt-notes/src/cs/ualberta/akt/akt_notes/EmailActivity.java
		//With the permission of author
		//Last accessed February 2, 2015 at 00:53
		if(item.getTitle().equals("Email claim") ) {

			ArrayList<Expense> emailClaim = claims.get(info.position).getExpenses();
			
			String contents = "Your claims for " + claims.get(info.position).getYourDestination() + " are: \n";
			int lineNumber = 1;
			Expense expense;
			for(int index = 0; index < emailClaim.size(); index ++) {
				expense = emailClaim.get(index);
				contents+= contents + lineNumber + ". " + expense.getDescription() + "\n";
				contents+= contents + expense.getCost().toString() + " " + expense.getCurrency() +"\n";
				contents+= contents + "Date: " + DateFormat.format("yyyy-MM-DD", expense.getDate()) + "\n";
				lineNumber++;
			}
			
			Intent emailIntent = new Intent(Intent.ACTION_SEND);
			emailIntent.setType("message/rfc822");
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your claims: ");
			emailIntent.putExtra(Intent.EXTRA_TEXT, contents);
			try {
				startActivity(Intent.createChooser(emailIntent, "Preparing to send your email"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		if(!(status.equals("Pending Review") || status.equals("Accepted"))) {
			if(item.getTitle() == "Edit") {
				Toast.makeText(this, "Edit claim", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(ClaimsActivity.this, EditDestinationActivity.class);
				intent.putExtra("position", info.position);
				startActivity(intent);
			
			} else if(item.getTitle() == "Delete") {
				Toast.makeText(this, "Deleted claim", Toast.LENGTH_SHORT).show();
				adapter.remove(claims.get(info.position));
			
			} else if(item.getTitle() == "Submit") {
				Toast.makeText(this, "Submit claim", Toast.LENGTH_SHORT).show();
				Claims claim = adapter.getItem(info.position);
				adapter.Submit(info.position, claim);
				
			} else {
				return false;
			
			}
			//Do not want to show this message if Expenses is selected
		} else if(!(item.getTitle() == "Expenses" || item.getTitle() == "Set claim as rejected" || item.getTitle() == "Set claim as accepted" || item.getTitle() == "Email claim")){
			Toast.makeText(this, "Cannot edit, delete or submit claims at this moment", Toast.LENGTH_SHORT).show();
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