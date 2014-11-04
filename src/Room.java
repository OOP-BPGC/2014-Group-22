package Project;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Room
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
	 *bookingTime[0] stores begining of booking time.
	 *bookingTime[1] stores duration of booking.
	 */
	private String[] bookingTime = new String[2];
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
	public String getroomNumber()
	{	return roomNumber;
	}
	public boolean getProjectorStatus()
	{	return projectorStatus;
	}
	public String getStatus()
	{	return status;
	}
	public static void main(String[] args)
	{
		Room C306 = new Room();
		C306.setRoomNumber("C306");
		C306.setBookingTime("1.45","1.5");
		
	}
}
