package Project;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.Serializable;
/*
 *@author AbhishekTiwari
 */
public class Request implements Serializable
{		
	/*
	 *String to hold 1 line reason for requesting a room.
	 */
	private String reason;
	/*
	 *Expected attendance for the room to be booked.
	 */
	private int attendanceCount = 0;
	/*
	 *String to hold choice of room.
	 */
	private String room;
	/*
	 *Boolean var to hold projector required or not.
	 */
	private boolean proj;

	/*
	 *String to hold request UID.
	 */
	private	String requestUID;
	/*
	 *String to store Booking Date in format : dd/mm/yyyy
	 */
	private String bookingDate;
	/*
	 *String to store Starting Time of Booking in 24 hour format : hh:mm
	 */
	private String startingTime;
	/*
	 *String to store Booking duration in format : 'n' for n hours
	 */	
	private	String duration;
	/*
	 *String to store Student ID
	 */	
	private String ID;
	/*
	 *String to hold booking status : Approved, Denied;
	 */
	private String bookingStatus;
	/*
	 *Flag to store whether or not the request has already been evaluated.
	 */
	private boolean evaluated;
	/*
	 * To store final time = initial time + duration.
	 */
	private int[] finalTime = new int[2];
	public Request()
	{
		setBookingStatus("Pending");
		setEvaluated(false);
	}
	public void setReason(String reason)
	{
		this.reason=reason;
	}
	public boolean setAttendanceCount(int att)		
	{	
		if(att>0&&att<500)
		{
			attendanceCount=att;
			return true;
		}	
		else
			System.out.println("!ERROR! For attendance, enter a value between 1-500");
		return false;
	}
	public void setRoom(String room)
	{	
		this.room=room;
	}
	public boolean setBookingDate(int day, int month, int year)
	{
		Calendar javaCalendar = null;
		javaCalendar = Calendar.getInstance();
		int flag=0;
		if(year>=2014)
		{
			if(month==2)
			{	
				if(day>=1&&day<=28)
					flag=1;
				else
					System.out.println("\n!ERROR! For given month enter a day between 1-28.");
			}
			else if(month==1||month==3||month==5||month==7||month==8||month==10||month==12)	
			{
				if(day>=1&&day<=31)
					flag=1;
				else
					System.out.println("\n!ERROR! For given month enter a day between 1-31.");
			}
			else if(month==4||month==6||month==8||month==9||month==11)
			{
				if(day>=1&&day<=30)
					flag=1;
				else
					System.out.println("\n!ERROR! For given month enter a day between 1-30.");
			}
			else
				System.out.println("\n!ERROR! Enter a value for month between 1-12.");
		}
		else 
			System.out.println("\n!ERROR! Enter Current Year.");
		if(flag==1)
		{
			String tempDate ="";
			tempDate=(day+"/"+month+"/"+year);
			if(Book.compareDate(tempDate,null)==-1)
			{
				System.out.println("\n!ERROR! You cannot make a booking in the past!");
				return false;
			}
			//at this point, date is in valid format and not of the past
			int currentDay = javaCalendar.get(Calendar.DATE);
			if(day-currentDay>7)
			{
				System.out.println("\n!ERROR! You cannot book a room more than 7 days in advance!");
				return false;
			}

			bookingDate=(day+"/"+month+"/"+year);
			return true;
		}
		return false;
	}
	public boolean setStartingTime(int hour, int minutes)
	{
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime date1 = formatter.parseDateTime(bookingDate);
		int dow = date1.getDayOfWeek();
		int flag=0;
		switch(dow)
		{
			case 1 :
			case 2 :
			case 3 :
			case 4 :
			case 5 : 	if(hour>=17&&hour<=22&&minutes>=0&&minutes<=60)
					{	
						if(minutes==60){
							hour+=1;
							minutes=0;
						}
						startingTime=(hour+":"+minutes);
						flag=1;	
					}
					else
						System.out.println("\n\n!ERROR! On weekdays, bookings are allowed only between 17:00 - 23:00");
					break;
			case 6 :	if(hour>=14&&hour<=22&&minutes>=0&&minutes<=60)
					{	
						if(minutes==60){
							hour+=1;
							minutes=0;
						}
						startingTime=(hour+":"+minutes);
						flag=1;	
					}
					else
						System.out.println("\n\n!ERROR! On Saturdays, bookings are allowed only between 14:00 - 23:00");
					break;
			case 7 :	if(hour>=9&&hour<=22&&minutes>=0&&minutes<=60)
					{	
						if(minutes==60){
							hour+=1;
							minutes=0;
						}
						startingTime=(hour+":"+minutes);
						flag=1;	
					}
					else
						System.out.println("\n\n!ERROR! On Sundays, bookings are allowed only between 12:00 - 23:00");
				break;
		}
		if(flag==1)
		{
			Calendar javaCalendar = null;
			javaCalendar = Calendar.getInstance();
			int hCurr = javaCalendar.get(Calendar.HOUR);
			int mCurr = javaCalendar.get(Calendar.MINUTE);
			if(Book.compareDate(bookingDate,null)==0)
			{
				if(hour>12)
					hour-=12;
				if(hour<hCurr)
				{
					System.out.println("\n!ERROR! The entered Time of Booking has already passed.");
					return false;
				}
				else if(hour-hCurr<2)
				{
					System.out.println("\n!ERROR! You must book atleast 2 hours in advance.");
					return false;
				}
				return true;
			}
			return true;
		}
		return false;
	}
	public boolean setDuration(double hours)
	{
		String[] temp = startingTime.split(":",0);
		int ht1 = Integer.parseInt(temp[0]);
		int mt1 = Integer.parseInt(temp[1]);
		int ht2=0, mt2=0;
		if(Math.floor(hours)==hours)
		{
			ht2=ht1+(int)hours;
			mt2=mt1;
		}
		else
		{
			if(mt1+30>=60)
			{
				ht2=ht1+1+(int)hours;
				mt2=mt1+30-60;
			}
			else
			{
				ht2=ht1+(int)hours;
				mt2=mt1+30;
			}
		}
		setFinalTime(ht2,mt2);
		if(ht2<22||(ht2==22&&mt2<60))
		{
			duration=(hours+"");
			return true;
		}
		else
			System.out.println("\n!ERROR! Booking cannot go past 23:00");
		return false;
	}
	private void setFinalTime(int h, int m)
	{
		finalTime[0]=h;
		finalTime[1]=m;
	}
	public boolean setBookingDate(String dt)
	{
		Pattern pat = Pattern.compile("^\\d{1,2}([/])\\d{1,2}([/])\\d{4}$");
		Matcher match = pat.matcher(dt);
		if(match.find())
			bookingDate=dt;
		else
		{
			System.out.println("Failed to set Booking Date. Please date as 7/9/2014.");
			return false;
		}
		return true;
	}
	public boolean setStartingTime(String begin)
	{
		Pattern pat = Pattern.compile("^\\d{1,2}([:])\\d{1,2}$");
		Matcher match = pat.matcher(begin);
		if(match.find())
			startingTime=begin;
		else
		{
			System.out.println("Failed to set beginning time of booking. Please enter time in hh:mm format.");
			return false;
		}
		return true;
	}
	public boolean setDuration(String d)
	{
		Pattern pat=Pattern.compile("\\d|\\d([.])\\d");
		Matcher match=pat.matcher(d);
		if(match.find())
			duration=d;
		else
		{
			System.out.println("Failed to set 'duration; of Booking. Please enter no of hours for booking. Example : 1 for 1 hour or 1.5 for 1 and a 1/2 hours.");
			return false;
		}
		return true;
	}
	public void setBookingStatus(String bs)
	{
		bookingStatus = bs;
	}
	public void setRequestUID(String uid)
	{
		requestUID = uid;
	}
	public String getReason()
	{
		return reason;
	}
	public int getAttendanceCount()
	{
		return attendanceCount;
	}
	public String getRoom()
	{
		return room;
	}
	public String getRequestUID()
	{
		return requestUID;
	}
	public String getBookingDate()
	{
		return bookingDate;
	}
	public String getStartingTime()
	{
		return startingTime;
	}
	public String getDuration()
	{
		return duration;
	}
	public String getBookingStatus()
	{
		return bookingStatus;
	}
	public void displayRequest()
	{
		System.out.println("Student ID                 :	"+Login.getId()+
				   "\nRequest UID              :	"+requestUID+
				   "\nRoom Requested           :	"+room+
				   "\nReason Listed            :	"+reason+
				   "\nProjector Requested      :	"+proj+
				   "\nAttendance               :	"+attendanceCount+
				   "\nBooking Date             :	"+bookingDate+
				   "\nStarting Time            :	"+startingTime+
				   "\nDuration(Hours)          :	"+duration);

	}
	public void setEvaluated(boolean set)
	{	evaluated=set;
	}
	public boolean getEvaluated()
	{	return evaluated;
	}
	public boolean setProjectorRequired(char a)
	{
		if(a=='y'||a=='Y')
		{
			proj=true;
			return true;
		}
		else if(a=='n'||a=='N')
		{
			proj=false;
			return true;
		}
		else 
			System.out.println("!ERROR! Enter y/n for whether projector is required or not.");
		return false;
	}
	public boolean getProjectorRequired()
	{
		return proj;
	}
	/*
	public void setID()
	{
		ID=Login.getID();
	}
	public String getID()
	{	return ID;
	}*/
}
