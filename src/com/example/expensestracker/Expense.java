package com.example.expensestracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;

public class Expense {
	
	private String currency;
	private int cost;
	
	
	private String category;
	private String description;
	private Date date; // May have to use calendar 
	private Calendar calendar; // If I have to use Calendar
	
	public void expense(int cost, String currency, String category, String description, String date) {
		this.cost = cost;
		this.currency = currency;
		this.category = category;
		this.description = description;
		try{
		this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date); //Assuming date is in text 
		} catch(ParseException e) {
			System.out.println("Please input a valid date for your birthday in the format of yyyy-MM-dd");
		}
	}
	
	
	public int getCost() {
		return this.cost;
	}
	
	public String getCurrency() {
		return this.currency;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public String getDescription() { 
		return this.description;
	}
	
	public String getDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(this.date);
	}

}
