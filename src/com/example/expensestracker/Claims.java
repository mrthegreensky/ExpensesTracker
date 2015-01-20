package com.example.expensestracker;

import java.util.ArrayList;
import java.util.List;

public class Claims {
	
	protected List<Expense> list = new ArrayList<Expense>();
	
	private int status = 0; // where status = 0 not submitted and is the default status
						// status = 1 is submitted but still being reviewed
						// status = 2 is rejected/returned and needs improvements to be done
						// status = 3 is accepted no further edits allowed
	
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
