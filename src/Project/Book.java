package Project;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
	
	public abstract void cancelRequest(String UID);
	
	public static boolean isValidTimeFormat(String t)
	{
		Pattern pat = Pattern.compile("^\\d{1,2}([:])\\d{1,2}$");
		Matcher match = pat.matcher(t);
		if(match.find())
		{
			try {
				DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:ss");
				DateTime time1 = formatter.parseDateTime(t);
			}catch(IllegalFieldValueException e) {
				// System.out.println("Invalid time format");
				return false;
			}
			return true;
		}
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
		DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:ss");
		DateTime time1 = formatter.parseDateTime(t1), time2;
			
   		if(t2 == null)
   		{
   			Calendar javaCalendar = null;
			javaCalendar = Calendar.getInstance();
			String currentDateStr = javaCalendar.get(Calendar.HOUR) + ":" + (javaCalendar.get(Calendar.MINUTE));
			time2 = formatter.parseDateTime(currentDateStr);
   		}
   		else
   			time2 = formatter.parseDateTime(t2);

   		int result = DateTimeComparator.getInstance().compare(time1, time2); 
   		// System.out.println(time1.toString() + time2.toString() + " => " + result); // Debug statement
		
   		if(result == 1)
   			return true;
   		return false;
	}
	
	/**
	 * 
	 * @param String d1
	 * @return 1 if d1 > d2, -1 if d1 < d2, 0 otherwise
	 */
	public static int compareDate(String d1, String d2)
	{
		if(d1 == null)
		{
			System.out.println("NULL!!!!");
		}
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime date1 = formatter.parseDateTime(d1), date2;
		// int dow = date1.getDayOfWeek();
			
   		if(d2 == null)
   		{
   			Calendar javaCalendar = null;
   			javaCalendar = Calendar.getInstance();
   			String currentDateStr = javaCalendar.get(Calendar.DATE) + "/" + (javaCalendar.get(Calendar.MONTH) + 1) + "/" + javaCalendar.get(Calendar.YEAR);
   			date2 = formatter.parseDateTime(currentDateStr);
   		}
   		else
   			date2 = formatter.parseDateTime(d2);

   		int result = DateTimeComparator.getInstance().compare(date1, date2); 
   		// System.out.println(date1.toString() + date2.toString() + " => " + result); // Debug statement
		return result;
	}
}