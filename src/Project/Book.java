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
	
	// TODO: Decide on this method
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
	
	public static boolean compareTime(String t)
	{
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Date currentTime = new Date();
		String currentTimeStr = dateFormat.format(currentTime);
		// System.out.println(currentTimeStr);
		int currentHour = Integer.parseInt(currentTimeStr.substring(0, currentTimeStr.indexOf(":")));
		int currentMinute = Integer.parseInt(currentTimeStr.substring(currentTimeStr.indexOf(":")+1));
		int givenHour = Integer.parseInt(t.substring(0, t.indexOf(":")));
		int givenMinute = Integer.parseInt(t.substring(t.indexOf(":")+1));
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
	
	// TODO: Shift this method somewhere else if possible
		/**
		 * Method to compare a given date with the current date
		 * @param String d1
		 * @return 1 if date > currentDate, -1 if date < currentDate, 0 otherwise
		 */
	public static int compareDate(String d1)
	{
		Date date1 = null, date2 = null;
		try{
			
			Calendar javaCalendar = null;
			String currentDate = "";
			javaCalendar = Calendar.getInstance();
			currentDate = javaCalendar.get(Calendar.DATE) + "/" + (javaCalendar.get(Calendar.MONTH) + 1) + "/" + javaCalendar.get(Calendar.YEAR);
			 
	   		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	   		date1 = sdf.parse(d1);
	       	date2 = sdf.parse(currentDate);
	
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

