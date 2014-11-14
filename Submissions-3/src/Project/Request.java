package Project;
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
	public Request()
	{
		setBookingStatus("Pending");
	}
	public void setReason(String reason)
	{
		this.reason=reason;
	}
	public void setAttendanceCount(int att)		
	{	
		attendanceCount=att;
	}
	public void setRoom(String room)
	{	
		this.room=room;
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
		System.out.println("Student ID        :	Login.getID() goes here"+
				   "\nRequest UID     :	"+requestUID+
				   "\nRoom Requested  :	"+room+
				   "\nReason Listed   :	"+reason+
				   "\nAttendance      :	"+attendanceCount+
				   "\nBooking Date    :	"+bookingDate+
				   "\nStarting Time   :	"+startingTime+
				   "\nDuration(Hours) :	"+duration);
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
