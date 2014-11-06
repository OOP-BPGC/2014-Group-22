
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class RoomBook extends Book
{	
	Scanner inp = new Scanner(System.in);
	/*
	 *String array to hold 10 lines of reason for requesting a room.
	 */
	private String[] reason	= new String[10];
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
	private String bookingDay;
	/*
	 *String to store Starting Time of Booking in 24 hour format : hh:mm
	 */
	private String startingTime;
	/*
	 *String to store Booking duration in format : 'n' for n hours
	 */	
	private	String duration;
	/*
	 *User object to represent applicant.
	 */	
	private User applicant;
	
	public void setReason()
	{
		System.out.println("Enter the reason for booking: \n(Max. 10lines) \n(After you are done entering your reason, please enter '...' as final line.)\n");
		int j=0;
		while(j<10)
		{
			System.out.printf("%d\t",j);
			reason[j]=inp.nextLine();
			if(reason[j].equals("..."))
				break;
			j++;
		}
	}
	public void setAttendanceCount(int att)		
	{	attendanceCount=att;
	}
	public void setRoom(String room)
	{	this.room=room;
	}
	@Override
	public void generateForm()
	{
		applicant = new User();
		Scanner inp = new Scanner(System.in);
		/*if(applicant.setDetails()){*/ // removed this as we already
                                            // authenticated the user
        //user details have been taken.
        char roomch = 'y';
        //take reason for booking	
        //take attendance count			
        System.out.println("Room is to be booked for how many persons?");
        //put in check for negative values
        setAttendanceCount(inp.nextInt());
        RoomDB.displayRooms();
        System.out.println("\n\nChoose a room");
        
        while(roomch=='y'||roomch=='Y')
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
        }
			
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

/*
class RoomBookTester
{
	public static void main(String[] args)
	{
		RoomBook book = new RoomBook();
		book.generateForm();
	}
}
*/