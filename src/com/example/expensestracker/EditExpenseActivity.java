package com.example.expensestracker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class EditExpenseActivity extends NewExpenseActivity {
	

	private ArrayList<Claims> claims;
	
	private Claims newClaim;
	private Expense expense;
	private int claimPosition;
	private int expensePosition;
	
	private EditText descriptionText;
	private EditText amountText;
	private Spinner currencySpinner;
	
	private DatePicker date;

	
	private Button okButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newexpense); //Uses same layout as creating a new expense
		
		descriptionText = (EditText) findViewById(R.id.DescriptionText);
		amountText = (EditText) findViewById(R.id.AmountText);
		date = (DatePicker) findViewById(R.id.datePickerExpense);
		currencySpinner = (Spinner) findViewById(R.id.CurrencySpinner);
		okButton = (Button) findViewById(R.id.button1);
		

		Bundle extras = this.getIntent().getExtras();
		claimPosition = extras.getInt("claimPosition");
		expensePosition = extras.getInt("expensePosition");
		
		claims = loadFromFile();
		expense = claims.get(claimPosition).getExpenseat(expensePosition);
		
		
		descriptionText.setText(expense.getDescription());
		amountText.setText(expense.getCost().toString());
		String currency= expense.getCurrency();
		setCurrentDateToview();
		
		//To set the currency to the previous one used
		int currencyPosition = 0; //set to CAD by default
		if (currency.equals("USD")) {
			currencyPosition = 1;
		} else if (currency.equals("EUR")) {
			currencyPosition = 2;
		} else if (currency.equals("GBP")) {
			currencyPosition = 3;
		}
		currencySpinner.setSelection(currencyPosition);
		
		
		//Have to check if button is pressed  and if there is input in the Destination EditText
		//Uses anonymous function, borrowed and modified from LonelyTwitterActivity
		if(descriptionText.toString().trim().length() > 0) {
			
			okButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					newClaim = claims.get(claimPosition);
					String stringAmount = amountText.getText().toString();
					Double amount = Double.parseDouble(stringAmount);
					newClaim.replaceExpense(expense, new Expense(amount, currencySpinner.getSelectedItem().toString(), descriptionText.getText().toString(), getDate(date)));
					claims.remove(claimPosition);
					claims.add(claimPosition, newClaim);
					saveInFile(claims);
					finish();
					}
			});
		}
			
		
	}
	
	//Sets the date on the datepicker
	public void setCurrentDateToview() {
		
		date = (DatePicker)findViewById(R.id.datePickerExpense);

		Date expenseDate = expense.getDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(expenseDate);
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
	
		date.init(year, month, day, null);
		
	}
	
	
}
