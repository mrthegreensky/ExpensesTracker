package com.example.expensestracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Claims extends Destination implements Serializable{
	
	//Auto-generated SerialVersionUID when implemented Serializable
	private static final long serialVersionUID = 703850910971252373L;

	protected ArrayList<Expense> list;
	
	private String status; // where status = 0 not submitted and is the default status
						// status = 1 is submitted but still being reviewed
						// status = 2 is rejected/returned and needs improvements to be done
						// status = 3 is accepted no further edits allowed
	
	public Claims(String YourDestination, Date StartDate, Date EndDate) {
		super(YourDestination, StartDate, EndDate);
		list = new ArrayList<Expense>();
		status = "Not submitted";
	}
	
	public void addExpense(Expense expense) {
		list.add(expense);
	}
	
	public void removeExpense(Expense expense) {
		list.remove(expense);
	}
	
	public void replaceExpense(Expense oldexpense, Expense newexpense) {
		list.set(list.indexOf(oldexpense), newexpense);
	}
	
	public ArrayList<Expense> getExpenses() {
		return list;
	}
	
	public Expense getExpense() {
		//Returns last claim
		if(list.size()>0) {
			return list.remove(list.size());
		}
		return null;
	}
	
	
	public String getStatus() {
		return this.status;
	}
	
	public void setSubmit() {	
		this.status = "Pending Review";
	}
	
	public void setReject() {
		this.status = "Rejected, Pending Review";
	}
	
	public void setAccept() {
		this.status = "Accepted";
	}

}