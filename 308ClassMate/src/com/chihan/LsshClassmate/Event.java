package com.chihan.LsshClassmate;

public class Event {
	private int month;
	private int date;
	private int hour;
	private int min;
	private String location;
	public Event(int month, int date, int hour, int min, String loc) {
		// TODO Auto-generated constructor stub
		this.month = month;
		this.date = date;
		this.hour = hour;
		this.min = min;
		this.location = loc;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getDate(){
		return date;
	}
	
	public int getHour(){
		return hour;
	}
	
	public int getMin(){
		return min;
	}
	
	public String getLoc(){
		return location;
	}
	
	public String getTextSMS(){
		String SMS = "Hey Guys! I woule like to hold a reunion. :)\n" +
				"Date: " + month +"/" + date + "\n" +
				"Time: " + hour + ":" + min +"\n" +
				"Location: " + location +"\n" +
				"If you want to come please reply me, so I can get a headcount.";
		return SMS;
	}

}
