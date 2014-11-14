package Project;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.Serializable;
import java.util.ArrayList;

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
	 *ArrayList to store booking dates.
	 */
	private ArrayList<String> bookingDate = new ArrayList<String>();
	/*
	 *ArrayList to store starting time of bookings.
	 */
	private ArrayList<String> startingTime = new ArrayList<String>();
	/*
	 *ArrayList to store duration of bookings.
	 */
	private ArrayList<String> duration = new ArrayList<String>();
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

	public boolean setBookingDate(String dt)
	{
		Pattern pat = Pattern.compile("^\\d{1,2}([/])\\d{1,2}([/])\\d{4}$");
		Matcher match = pat.matcher(dt);
		if(match.find())
			bookingDate.add(dt);
		else
		{
			System.out.println("Failed to set Booking Date. Please enter date in the format 7/9/2014.");
			return false;
		}
		
		return true;
	}
	public boolean setBookingTime(String begin, String dtn)
	{	
		Pattern pat = Pattern.compile("^\\d{1,2}([:])\\d{1,2}$");
		Matcher match = pat.matcher(begin);
		if(match.find())
			startingTime.add(begin);
		else
		{
			System.out.println("Failed to set beginning time of booking. Please enter time in hh:mm format.");
			return false;
		}
		pat=Pattern.compile("\\d|\\d([.])\\d");
		match=pat.matcher(dtn);
		if(match.find())
			duration.add(dtn);
		else
		{
			System.out.println("Failed to set 'duration; of Booking. Please enter no of hours for booking. Example : 1 for 1 hour or 1.5 for 1 and a 1/2 hours.");
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
	public ArrayList<String> getStartingTime()
	{	return startingTime;
	}
	public ArrayList<String> getDuration()
	{	return duration;
	}
	public ArrayList<String> getBookingDate()
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
			int noOfBookings = bookingDate.size();
			for(int j=0; j<noOfBookings; j++)
			{
				System.out.println("Booking no. :"+j);
				System.out.println("Booking Date :\t"+bookingDate.get(j));
				System.out.println("Rooms is booked from:\t"+startingTime.get(j)+"\nFor the duration of:\t"+duration.get(j)+" hours.");
			}
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
