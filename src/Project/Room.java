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
	 *ArrayList to store UIDS bookings.
	 */
	private ArrayList<String> uid = new ArrayList<String>();
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
	public void setBookingDate(String bd)
	{
		bookingDate.add(bd);
	}
	public void setStartingTime(String st)
	{
		startingTime.add(st);
	}
	public void setUID(String id)
	{
		uid.add(id);
	}
	public void setDuration(String dt)
	{
		duration.add(dt);
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
	public ArrayList<String> getUID()
	{	return uid;
	}
	public int getCapacity()
	{	return capacity;
	}
	public void displayRoom()
	{
		System.out.println("\n**********************************************************************************");
		System.out.println("Room Number:\t\t\t\t"+roomNumber+"\nProjector Status:\t\t\t"+projectorStatus+"\nRoom Status:\t\t\t\t"+status+"\nRoom Capacity:\t\t\t\t"+capacity);
		if(status.equals("Booked"))
		{
			System.out.println("\nEXISTING BOOKINGS FOR "+roomNumber);
			int noOfBookings = bookingDate.size();
			for(int j=0; j<noOfBookings; j++)
			{
				System.out.println("\nBooking no. :\t\t\t\t"+(j+1));
				System.out.println("----------------------------------------------------");
				System.out.println("Booking Date :\t\t\t\t"+bookingDate.get(j));
				System.out.println("Rooms is booked from:\t\t\t"+startingTime.get(j)+"\nFor the duration of:\t\t\t"+duration.get(j)+" hours.");
			}
		}
		
	}
	public void removeBooking(int index)
	{
		bookingDate.remove(index);
		startingTime.remove(index);
		duration.remove(index);
		uid.remove(index);
		if(bookingDate.size()==0)
			status="Available";
	}
}
