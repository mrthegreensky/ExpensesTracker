package com.example.expensestracker;

import java.io.Serializable;
import java.util.Date;


//Not sure if this class needs Serializable
public class Expense implements Serializable{
	
	//Auto-generated serialVersionUID
	private static final long serialVersionUID = 6479358641782024794L;
	private String currency;
	private double cost;
	private String description;
	private Date date;
	
	public Expense(Double cost, String currency, String description, Date date) {
		this.cost = cost;
		this.currency = currency;
		this.description = description;
		this.date = date;
	}
	
	
	public Double getCost() {
		return this.cost;
	}
	
	public String getCurrency() {
		return this.currency;
	}
	
	public String getDescription() { 
		return this.description;
	}
	
	public Date getDate() {
		return this.date;
	}

}
