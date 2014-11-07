package Project;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.Serializable;

public class Room implements Serializable
{
	/*
	 *To hold room number.
	 */
	private String roomNumber;
	/*
	 *To tell if the room has a projector or not.
	 */
	private boolean projectorStatus;
	/*
	 *To store room status : available, booked.
	 */
	private String status;
	/*
	 *To store booking date.
	 */
	private String bookingDate;
	/*
	 *bookingTime[0] stores begining of booking time.
	 *bookingTime[1] stores duration of booking.
	 */
	private String[] bookingTime = new String[2];
	/*
	 *To hold upper limit of capacity for each room
	*/
	private int capacity;
	public void setCapacity(int cpc)
	{	capacity=cpc;
	}
	public void setRoomNumber(String rn)
	{	roomNumber = rn;
	}
	public void setProjectorStatus(boolean p)
	{	projectorStatus=p;
	}
	public void setStatus(String stat)
	{	status=stat;
	}
	public boolean setBookingTime(String begin, String duration)
	{		
		Pattern pat = Pattern.compile("^\\d{1,2}([:])\\d{1,2}$");
		Matcher match = pat.matcher(begin);
		if(match.find())
			bookingTime[0]=begin;
		else
		{
			System.out.println("Failed to set beginning time of booking. Please enter time in hh:mm format.");
			return false;
		}
		pat=Pattern.compile("\\d|\\d([.])\\d");
		match=pat.matcher(duration);
		if(match.find())
			bookingTime[1]=duration;
		else
		{
			System.out.println("Failed to set 'duration; of Booking. Please enter no of hours for booking. Example : 1 for 1 hour or 1.5 for 1 and a 1/2 hours.");
			return false;
		}
		return true;
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
	public String getRoomNumber()
	{	return roomNumber;
	}
	public boolean getProjectorStatus()
	{	return projectorStatus;
	}
	public String getStatus()
	{	return status;
	}
	public String getStartingTime()
	{	return bookingTime[0];
	}
	public String getDuration()
	{	return bookingTime[1];
	}
	public String getBookingDate()
	{	return bookingDate;
	}
	public int getCapacity()
	{	return capacity;
	}
	public void displayRoom()
	{
		System.out.println("Room Number:\t"+roomNumber+"\nProjector Status:\t"+projectorStatus+"\nRoom Status:\t"+status+"\nRoom Capacity:\t"+capacity);
		if(status.equals("Booked"))
		{
			System.out.println("Booking Date :\t"+bookingDate);
			System.out.println("Rooms is booked from:\t"+bookingTime[0]+"\nFor the duration of:\t"+bookingTime[1]+" hours.");
		}
		System.out.println("-----------------------------");
	}
	public static void main(String[] args)
	{
		Room C306 = new Room();
		C306.setRoomNumber("C306");
		C306.setBookingTime("1:45","1.5");
		C306.setBookingDate("5/11/14");		
	}
}
