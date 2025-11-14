package com.testautomation.apitesting.pojos;

public class Booking {
	
	private String firstname;
	private String lastname;
	private boolean depositpaid;
	private int totalprice;
	private String additionalneeds;
	private BookingDates bookingdates;
	
	public Booking() {
		
	}
	
	public Booking(String fname, String lname, boolean deppaid, int tprice, String addneed, BookingDates bdates) {
		
		setFirstname(fname);
		setLastname(lname);
		setAdditionalneeds(addneed);
		setDepositpaid(deppaid);
		setTotalprice(tprice);
		setBookingdates(bdates);
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	
	
	public boolean isDepositpaid() {
		return depositpaid;
	}
	public void setDepositpaid(boolean depositpaid) {
		this.depositpaid = depositpaid;
	}
	public int getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}
	public String getAdditionalneeds() {
		return additionalneeds;
	}
	public void setAdditionalneeds(String additionalneeds) {
		this.additionalneeds = additionalneeds;
	}
	public BookingDates getBookingdates() {
		return bookingdates;
	}
	public void setBookingdates(BookingDates bookingdates) {
		this.bookingdates = bookingdates;
	}
	

}
