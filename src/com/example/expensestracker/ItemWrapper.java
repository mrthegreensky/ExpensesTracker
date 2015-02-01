package com.example.expensestracker;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemWrapper implements Serializable{

	private static final long serialVersionUID = -8284844760144894886L;
	
	private ArrayList<Claims> claims;

	public ItemWrapper(ArrayList<Claims> claims) {
		this.claims = claims;
	}
	
	public ArrayList<Claims> getArrayList() {
		return this.claims;
	}
	
}
