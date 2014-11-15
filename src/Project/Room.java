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

	public boolean setBookingDate(int day, int month, int year)
	{
		int flag=0;
		if(year>=2014)
		{
			if(month==2)
			{	
				if(day>=1&&day<=28)
					flag=1;
			}
			else if(month==1||month==3||month==5||month==7||month==8||month==10||month==12)	
			{
				if(day>=1&&day<=31)
					flag=1;
			}
			else if(month==4||month==6||month==8||month==9||month==11)
			{
				if(day>=1&&day<=30)
					flag=1;
			}
		}
		if(flag==1)
		{
			bookingDate.add(day+"/"+month+"/"+year);
			return true;
		}
		return false;
	}
	public void setBookingDate(String bd)
	{
		bookingDate.add(bd);
	}
	public boolean setStartingTime(int hour, int minutes)
	{
		if(hour>=0&&hour<=24&&minutes>=0&&minutes<=60)
		{	
			startingTime.add(hour+":"+minutes);
			return true;	
		}
		return false;
	}
	public void setStartingTime(String st)
	{
		startingTime.add(st);
	}
	
	public boolean setDuration(double hours)
	{
		if(hours>0&&hours<=15)
		{
			duration.add(hours+"");
			return true;
		}
		return false;
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
}
