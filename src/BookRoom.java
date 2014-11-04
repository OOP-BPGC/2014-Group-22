package Project;
import Project.User;
import Project.Book;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

class Room
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
	private String[] bookingTime;
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
			System.out.println("Failed to set bbeginning time of booking. Please enter time in hh:mm format.");
			return false;
		}
		pat=Pattern.compile("\\d|\\d([.])\\d");
		match=pat.matcher(duration);
		if(match.find())
			bookingTIme[1]=duration;
		else
		{
			System.out.println("Failed to set 'duration; of Booking. Please enter no of hours for booking. Example : 1 for 1 hour or 1.5 for 1 and a 1/2 hours.");
			return false;
		}
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

}
public class BookRoom extends Book
{	
	/*
	 *RoomList stores all rooms along with their relevant information.
	 */
	private ArrayList<Room> RoomList;
	@Override
	public void generateForm()
	{
		User applicant = new User();
		Scanner inp = new Scanner(System.in);
		if(applicant.setDetails())
		{
			//new fields for the form in addition to that of user 
			String[] reason	= new String[10];
			String attendanceCount = "";
			String room = "";
			String requestUID="";
			//format dd/mm/yyyy hh:mm HH:MM
			String bookingDay="";	
			String startingTime="";	
			String duration="";	
			int j=0;
			char choice = 'y';
			System.out.println("Enter the reason for booking: \n(Max. 10lines) \n(After you are done entering your reason, please enter '...' as final line.)\n");
			while(j<10)
			{
				System.out.printf("%d\t",j);
				reason[j]=inp.nextLine();
				if(reason[j].equals("..."))
					break;
				j++;
			}
			System.out.println("Room is to be booked for how many persons?");
			attendanceCount=inp.next();
			//displayRooms(attendanceCount);
			System.out.println("Choose a room");/*
			while(ch=='y'||ch=='Y')
			{
				room=inp.next();				
				if(roomChoiceCheck(room)==-1)
					System.out.println("Sorry!! The room chosen does not exist.");
				else if(roomChoiceCheck(room)==0)
					System.out.println("Sorry!! The room chosen is not available.");
				else if(roomChoiceCheck(room)==1)
				{
					System.out.println(room+"\t Has been marked for booking for you.");
					requestUID=generateUID();
					System.out.println("Your request UID is\t"+requestUID+"\nYou can use it to review request status or cancel Booking Request.");
					
				}
			}*/
			
		
			
		}
		else 
			System.out.println("Could not register request. Please ensure that you enter a Valid ID. Please Try Again.");
	}	
	@Override
	public void displayStatus(String UID)
	{
		System.out.println("UnderConstruction");
	}
	@Override
	public void cancelRequest(String UID)
	{
		System.out.println("UnderConstruction");
	}
}
class BookRoomTester
{
	public static void main(String[] args)
	{
		BookRoom book = new BookRoom();
		book.generateForm();
	}
}
