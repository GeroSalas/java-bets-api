
package com.gen.desafio.api.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtils {
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	public static Date copyTime(Date from, Date to) {
		Calendar cFrom = Calendar.getInstance();
		cFrom.setTime(from);
		Calendar cTo = Calendar.getInstance();
		cTo.setTime(to);
		cTo.set(Calendar.HOUR_OF_DAY, cFrom.get(Calendar.HOUR_OF_DAY));
		cTo.set(Calendar.MINUTE, cFrom.get(Calendar.MINUTE));
		cTo.set(Calendar.SECOND, cFrom.get(Calendar.SECOND));
		cTo.set(Calendar.MILLISECOND, cFrom.get(Calendar.MILLISECOND));
		return cTo.getTime();
	}

	public static String format(Date date) {
		return formatter.format(date);
	}
	
	
	public static boolean ableToBetMatch(Date date) {
        boolean enabled = false;
        Date nowServerTime = new Date();
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, -1);
        date = cal.getTime();     
        
        if(nowServerTime.before(date)){
        	enabled = true;
        }
        
        return enabled;
    }
	
	public static boolean ableToBetGroupWinner() {
        boolean enabled = false;
        Date nowServerTime = new Date();
		Calendar cal = new GregorianCalendar(2016,8,4,11,0,0);   // Top Date: Thursday 04 August 11:00am
        Date ableDate = cal.getTime();     
        
        if(nowServerTime.before(ableDate)){
        	enabled = true;
        }
        
        return enabled;
    }
	
	
	
}
