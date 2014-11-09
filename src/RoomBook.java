
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
//DB management
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
public class RoomBook extends Book
{		
	/*
	 *String to hold 1 line reason for requesting a room.
	 */
//	private String[] reason	= new String[10];
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
	private void setReason()
	{
		Scanner inp = new Scanner(System.in);
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
	}*/

	public void setAttendanceCount(int att)		
	{	attendanceCount=att;
	}
	public void setRoom(String room)
	{	this.room=room;
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
	}/*
	public void setID()
	{
		ID=Login.getID();
	}
	public String getID()
	{	return ID;
	}*/
	@Override
	public void generateForm()
	{
		Scanner inp = new Scanner(System.in);
		int roomBookedFlag=0;
		//setID();
		//user details have been taken.
		String room="";
		//char variable to run room choosing menu
		char roomch = 'y';
		//take reason for booking	
		System.out.println("Enter Reason for requesting room. Use only 1 Line.");	
		reason = inp.nextLine();
		//take attendance count			
		System.out.println("Room is to be booked for how many persons? (Enter digit 'n' for 'n' persons)");
		//put in check for negative values
		setAttendanceCount(inp.nextInt());
		//take booking day input
		System.out.println("Please enter the following Booking Details to resolve clashes with other bookings, if any...");
		System.out.println("Room is to be booked for which day? (Enter date in format as 7/1/2014 or 12/11/2014 only)");
		setBookingDate(inp.next());
		//take starting time of booking
		System.out.println("Starting time of booking? (Enter time in 24 hour format as hh:mm only)");
		setStartingTime(inp.next());
		//take 'duration' of booking input
		System.out.println("Room is to be booked for how long (enter digit 'n' for 'n' hours or 'm.5' for m and a half hours)?");
		setDuration(inp.next());
		//display rooms from DB for choosing
		while(roomch=='y'||roomch=='Y')
		{
			roomBookedFlag=0;
			System.out.println("ROOMS\n----------------------------");
			RoomDB.displayRooms();
			System.out.println("\n\nChoose a room");
			room=inp.next();				
			if(roomChoiceCheck(room)==-1)
				System.out.println("Sorry!! The room you chose does not exist.");
			else if(roomChoiceCheck(room)==0)
				System.out.println("Sorry!! The room chosen is not available.");
			else if(roomChoiceCheck(room)==1)
			{
				setRoom(room);
				System.out.println(room+"\t Has been marked for booking for you.");
				System.out.println("Please keep in mind that in case there are multiple bookings for the same room\nand at the same time slot then the admin will approve the first posted booking request with valid reason and the others shall be denied.");
				requestUID = generateUID();
				System.out.println("Your request UID is\t"+requestUID+"\nYou can use it to review request status or cancel booking Request.");	
				roomBookedFlag=1;
			}
			System.out.println("Choose a different Room?(y/n)\n[Current request will be discarded.]");
			roomch=inp.next().charAt(0);
		}
		if(roomBookedFlag==1)		
			writeRequestToDB();
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
	private int roomChoiceCheck(String room)
	{
		ArrayList<Room> roomList = new ArrayList<Room>();
		roomList=RoomDB.getRoomList();
		String roomName = "";
		for(Room aroom : roomList)
		{
			roomName=aroom.getRoomNumber();
			int clashFlag=0;
			if(roomName.equals(room))
			{
				if(aroom.getStatus().equals("Booked"))
				{
					ArrayList<String> bookingDates = aroom.getBookingDate();
					int bookingIndex = 0;
					ArrayList<String> startingTimes = aroom.getStartingTime();
					//now check if room is booked for the same day and date!
					for(String aday : bookingDates)
					{
						if(aday.equals(bookingDate))
						{
							for(String time : startingTimes)
							{
								if(time.equals(startingTime))
								{
				
								  System.out.println(room+" is already booked for the same Day and Time.");
									clashFlag=1;
									break;	
								}
							}
						}
						if(clashFlag==1)
							return 0;
					}
					//room requested IS Booked but not for the same day and time!
					return 1;
				}
				else if((aroom.getStatus()).equals("Available"))
					return 1;				
			}
		}
		return -1;//no such room found		
	}
	private void writeRequestToDB()
	{	
		PrintWriter out = null;
		try{
			out = new PrintWriter(new FileWriter("RequestRoomDB",true));
			//'true' flag in above tells FileWriter to append rather than clear.
			out.println("Login.getID()here");
			out.println(requestUID);
			out.println(room);
			out.println(reason);
			out.println(bookingDate);
			out.println(startingTime);
			out.println(duration);
			out.println(attendanceCount);
//			out.println("*");
		}catch(IOException e1){
				System.out.println("Caught IOException while writing booking request to DB.");
		}finally{
			if(out!=null)
				out.close();
		}	
	}
}

class RoomBookTester
{
	public static void main(String[] args)
	{
		RoomBook book = new RoomBook();
		book.generateForm();
	}
}