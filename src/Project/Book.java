package Project;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *@author AbhishekTiwari
 */
abstract class Book
{
	protected String UID;
	
	public String getUID()
	{
		return this.UID;
	}
	public String generateUID()
	{
		String[] letters={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		String[] nos={"0","1","2","3","4","5","6","7","8","9"};
		int len1 = letters.length;
		int len2 = nos.length;
		int rand1=(int)(Math.random()*len1);
		int rand2=(int)(Math.random()*len2);
		int rand3=(int)(Math.random()*len1);
		int rand4=(int)(Math.random()*len2);
		return letters[rand1]+nos[rand2]+letters[rand3]+nos[rand4];		
	}
	public abstract void generateForm();
	public abstract void displayStatus(String UID);
	
	// TODO: Tell Abhishek to incorporate this into his part
	public abstract void cancelRequest(String UID);
	
	public static boolean isValidTimeFormat(String t)
	{
		Pattern pat = Pattern.compile("^\\d{1,2}([:])\\d{1,2}$");
		Matcher match = pat.matcher(t);
		if(match.find())
			return true;
		return false;
	}
	
	public static boolean isValidDateFormat(String dt)
	{
		Pattern pat = Pattern.compile("^\\d{1,2}([/])\\d{1,2}([/])\\d{4}$");
		Matcher match = pat.matcher(dt);
		if(match.find()) // If date format is correct, check for date validity
			return true;
		return false;
	}
	
	/**
	 * 
	 * @param t1
	 * @param t2
	 * @return true if t1 > t2, false otherwise
	 */
	public static boolean compareTime(String t1, String t2)
	{
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		int currentHour, currentMinute;
		if(t2 == null)
		{
			Date currentTime = new Date();
			String currentTimeStr = dateFormat.format(currentTime);
			// System.out.println(currentTimeStr);
			currentHour = Integer.parseInt(currentTimeStr.substring(0, currentTimeStr.indexOf(":")));
			currentMinute = Integer.parseInt(currentTimeStr.substring(currentTimeStr.indexOf(":")+1));
		}
		else
		{
			currentHour = Integer.parseInt(t2.substring(0, t2.indexOf(":")));
			currentMinute = Integer.parseInt(t2.substring(t2.indexOf(":")+1));
		}
		int givenHour = Integer.parseInt(t1.substring(0, t1.indexOf(":")));
		int givenMinute = Integer.parseInt(t1.substring(t1.indexOf(":")+1));

		if(givenHour >= givenHour)
		{
			if(givenHour > currentHour)
				return true;
			else
			{
				if(givenMinute >= currentMinute)
				{
					if(givenMinute > currentMinute)
						return true;
					else
						return false;
				}
				else
					return false;
			}
		}
		else
			return false;
	}
	
	/**
	 * 
	 * @param String d1
	 * @return 1 if d1 > d2, -1 if d1 < d2, 0 otherwise
	 */
	public static int compareDate(String d1, String d2)
	{
		Date date1 = null, date2 = null;
		try{
			
			Calendar javaCalendar = null;
			String currentDate = "";
			javaCalendar = Calendar.getInstance();
			currentDate = javaCalendar.get(Calendar.DATE) + "/" + (javaCalendar.get(Calendar.MONTH) + 1) + "/" + javaCalendar.get(Calendar.YEAR);
			 
	   		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	   		date1 = sdf.parse(d1);
	   		if(d2 == null)
	   			date2 = sdf.parse(currentDate);
	   		else
	   			date2 = sdf.parse(d2);
	
	       	// System.out.println(sdf.format(date1));
	       	// System.out.println(sdf.format(date2));
	
	   	} catch(ParseException ex) {
	   		ex.printStackTrace();
	   	}
		
		if(date1.after(date2)){
	   		// System.out.println("Date1 is after Date2");
	   		return 1;
	   	}
	    	else if(date1.before(date2)){
    		// System.out.println("Date1 is before Date2");
    		return -1;
    	}
	    	// if(date1.equals(date2)){
    		// System.out.println("Date1 is equal Date2");
    	return 0;
	    	// }
	}
}

