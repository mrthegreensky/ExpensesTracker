package com.example.expensestracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Claims extends Destination implements Serializable{
	
	//Auto-generated SerialVersionUID when implemented Serializable
	private static final long serialVersionUID = 703850910971252373L;

	protected List<Expense> list;
	
	private int status; // where status = 0 not submitted and is the default status
						// status = 1 is submitted but still being reviewed
						// status = 2 is rejected/returned and needs improvements to be done
						// status = 3 is accepted no further edits allowed
	
	public Claims(String YourDestination, Date StartDate, Date EndDate) {
		super(YourDestination, StartDate, EndDate);
		list = new ArrayList<Expense>();
		status = 0;
	}
	
	public void addClaim(Expense expense) {
		list.add(expense);
	}
	
	public void removeClaim(Expense expense) {
		list.remove(expense);
	}
	
	public void replaceClaim(Expense oldexpense, Expense newexpense) {
		list.set(list.indexOf(oldexpense), newexpense);
	}
	
	public List<Expense> getClaims() {
		return list;
	}
	
	public Expense getClaim() {
		//Returns last claim
		if(list.size()>0) {
			return list.remove(list.size());
		}
		return null;
	}
	
	
	public int getStatus() {
		return this.status;
	}
	
	public void setSubmit() {	
		this.status = 1;
	}
	
	public void setReject() {
		this.status = 2;
	}
	
	public void setAccept() {
		this.status = 3;
	}

}