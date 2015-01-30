package com.example.expensestracker;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;


//Not sure if this class needs Serializable
public class Expense implements Serializable{
	
	//Auto-generated serialVersionUID
	private static final long serialVersionUID = 6479358641782024794L;
	private String currency;
	private int cost;
	private String category;
	private String description;
	private Date date;
	
	public void expense(int cost, String currency, String category, String description, String date) {
		this.cost = cost;
		this.currency = currency;
		this.category = category;
		this.description = description;
		try{
		this.date = new SimpleDateFormat("yyyy/MM/dd").parse(date);
		} catch(ParseException e) {
			System.out.println("Please input a valid date for your birthday in the format of YYYY/MM/dd");
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
		return new SimpleDateFormat("YYYY/MM/dd").format(this.date);
	}

}
