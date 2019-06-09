package com.rs.game.player.content.custom;

import java.util.Calendar;

public class TimeManager {

	final static int SUNDAY = 1;
	final static int MONDAY = 2;
	final static int TUESDAY = 3;
	final static int WEDNESDAY = 4;
	final static int THURSDAY = 5;
	final static int FRIDAY = 6;
	final static int SATURDAY = 7;
	
	public static int dayOfWeek() {
	      Calendar cal = Calendar.getInstance();
	      return cal.get(Calendar.DAY_OF_WEEK);
	}
	   
	public static boolean isWeekend() {
	   return dayOfWeek() == SUNDAY ? true:
	          dayOfWeek() == FRIDAY ? true:
	          dayOfWeek() == SATURDAY ? true: false;
	  }
	
}
