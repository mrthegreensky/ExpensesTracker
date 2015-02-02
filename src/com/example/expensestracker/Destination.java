package com.example.expensestracker;

import java.util.Date;

public class Destination {

	private String YourDestination;
	private Date StartDate;
	private Date EndDate;
	
	
	public Destination(String YourDestination, Date StartDate, Date EndDate) {
		this.YourDestination = YourDestination;
		this.StartDate = StartDate;
		this.EndDate = EndDate;
	}
	
	
	public String getYourDestination() {
		return YourDestination;
	}
	
	public Date getStartDate() {
		return StartDate;
	}
	
	public Date getEndDate() {
		return EndDate;
	}

}
