package com.example.expensestracker;

import java.util.ArrayList;
import java.util.List;

public class Claims {
	
	protected List<Expense> list = new ArrayList<Expense>();
	
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

}
