package Project;
//import Project.User;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
//DB management
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
//Exceptions
import java.io.IOException;
import java.io.FileNotFoundException;
/*
 *@author AbhishekTiwari
 */
public class RoomBook extends Book
{	
	private Request newrequest = new Request();
	private static ArrayList<Request> RequestsMade = new ArrayList<Request>();	
	@Override
	public void generateForm()
	{
		System.out.println("\n############################################################################");
		System.out.println("\nROOM LIST WITH THEIR BOOKINGS");
		RoomDB.displayRooms();
		System.out.println("\n____________________________________________________________________________________________");
		System.out.println("\nPlease scroll through the list of available rooms and choose a room which meets your");
		System.out.println("requirements. The bookings are also listed to help you avoid conflicting choices...");
		System.out.println("\n____________________________________________________________________________________________");

		int confirmRoom = 2;
		Scanner inp = new Scanner(System.in);
		String room = null;
		String bookingDate = null;
		String startingTime = null;
		String line = null;
		int attendance=0;
		int day=0;
		int month=0;
		int year=0;
		int hour=0;
		int minutes=0;
		double duration=0;
		int roomBooked=0;

		System.out.println("#################################");
		System.out.println("FILL IN THE FOLLOWING FIELDS");
		System.out.println("#################################");
		//take booking day input
		do{
		System.out.println("Room is to be booked for which day?");
		System.out.println("Enter day (1-31) and press enter");
		System.out.println("then enter month (1-12) and press enter");
		System.out.println("then enter year (>2014) and press enter");
		day=inp.nextInt();
		month=inp.nextInt();
		year=inp.nextInt();
		}while(!newrequest.setBookingDate(day,month,year));

		System.out.println("\n\n");
		//take starting time of booking
		do{
		System.out.println("Starting time of booking?");
		System.out.println("Enter time in 24 hour format,");
		System.out.println("Enter hours (8-23) press enter,");
		System.out.println("then enter minutes (0-60) and press enter.");
		hour=inp.nextInt();		
		minutes=inp.nextInt();
		}while(!newrequest.setStartingTime(hour,minutes));
		
		System.out.println("\n\n");
		//take 'duration' of booking input
		do{
		System.out.println("Room is to be booked for how long?");
		System.out.println("Enter value in hours eg : 1.5, 2.5, 3 etc.");
		duration=inp.nextDouble();
		}while(!newrequest.setDuration(duration));
		
		System.out.println("\n\n");	
		//take attendance count			
		System.out.println("Room is to be booked for how many persons?");
		attendance=inp.nextInt();
		newrequest.setAttendanceCount(attendance);

		System.out.println("\n\n");
		//take reason for booking	
		inp.nextLine();
		System.out.println("Enter Reason for requesting room. Use only 1 Line.");	
		line=inp.nextLine();
		newrequest.setReason(line);
		inp.nextLine();

		while(confirmRoom==2)
		{
			System.out.println("Enter choice of room:");
			System.out.println("(Do not change capital letters to small))");
			room=inp.next();
			int result =roomChoiceCheck(room,day,month,year,hour,minutes,duration,attendance);
			if(result==-1)
			{
				System.out.println("#################################");
				System.out.println(room+" does not exist.");
			}
			else if(result==2)
			{
				System.out.println("#################################");
				System.out.println("The capacity of "+room+" is less than Expected Attendance given.");
			}
			else if(result==3)
			{
				System.out.println("#################################");
				System.out.println("The room "+room+" is already booked for the chosen time slot.");
				System.out.println("Please choose a different room.");
			}
			else if(result==4)
			{
				System.out.println("#################################");
				System.out.println("Your requested time slot is conflicting with an existing booking of "+room);
				System.out.println("Please choose a different room.");
			}
			else if(result==1)
			{
				newrequest.setRoom(room);
				newrequest.setRequestUID(generateUID());
				newrequest.setBookingStatus("Pending");
				System.out.println("---------------------------------------------------------------------------");
				System.out.println("\n------------Request Posted. UID is :"+newrequest.getRequestUID()+"-----------------------------------");

			}
			System.out.println("1.Confirm Room.");
			System.out.println("2.Choose a different Room.");
			System.out.println("3.Discard booking.");
			System.out.println("Enter Choice:");	
			confirmRoom=inp.nextInt();			
			if(confirmRoom==1)
			{	
				if(result==1)
				{	roomBooked=1;	
					break;}
				else
				{	System.out.println("In this case booking cannot be confirmed. Discarding Request...");
					break;
				}
			}
			else if(confirmRoom==2)
				continue;
			else if(confirmRoom==3)
				break;
		}
		if(roomBooked==1)
		//this will write to RoomRequestDB from where the AdminRoom class method will read for 'Evaluate Requests'
			writeRequestToDB();
	}
	/*	@return
	 *	-1 : Room does not exist.
	 *	 1 : Room is available 'for' the requested time slot without conflicting with previous and next bookings.
	 *	 2 : Room capacity < expected Attendance
	 *	 3 : Requested room is already booked at exactly the same time slot and day.
	 *	 4 : Requested time slot is in conflict with an existing booking.
	 */
	public static int roomChoiceCheck(String room, int day,int month,int year, int hour,int minutes, double duration,int attendance)
	{
		ArrayList<Room> roomList = new ArrayList<Room>();
		roomList=RoomDB.getRoomList();
		String date = day+"/"+month+"/"+year;
		String time = hour+":"+minutes;
		for(Room aroom : roomList)
		{
			if(aroom.getRoomNumber().equals(room))
			{
				if(aroom.getCapacity()<attendance)
					return 2;
				else if(aroom.getStatus().equals("Available"))
					return 1;	
				else if(aroom.getStatus().equals("Booked"))
				{
					if(aroom.getBookingDate().contains(date))
					{
						ArrayList<String> bookingDates = aroom.getBookingDate();
						ArrayList<String> startingTimes = aroom.getStartingTime();	
						ArrayList<String> durations = aroom.getDuration();
						int size = bookingDates.size();
					//if there are multiple bookings on the same day they'll be in seperate objects of the ArrayLists
						for(int j=0; j<size; j++)
						{
							if(bookingDates.get(j).equals(date))
							{
								String timeOfBooking = startingTimes.get(j);
								String[] booked = timeOfBooking.split(":",0);
								String[] requested = time.split(":",0);
								//timing details of existing booking 
								int hourBooked = Integer.parseInt(booked[0]);
								int minutesBooked = Integer.parseInt(booked[1]);
								double durationBooked = Double.parseDouble(durations.get(j));
								if(hour==hourBooked&&minutes==minutesBooked)
									return 3;
								else if(hour<hourBooked&&(hour+duration)<=hourBooked)
									return 1;
								else if(hour>hourBooked && hourBooked+durationBooked<=hour)
									return 1;
								else
									return 4;
							}
						}
					}
					else
						return 1;//Because room is booked but not for the day requested by user.
				}
			}
		}
		return -1;
	}
	@Override
	public void displayStatus(String UID)
	{	
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		int index =-1;
		ArrayList<Request> requestsInDB = new ArrayList<Request>();
		//read from RequestRoomDB
		requestsInDB = (ArrayList<Request>)getRequestsMade();
		if(requestsInDB.size()>0)
		{
			//search for request with supplied UID
			for(Request areq : requestsInDB)
			{
				if(areq.getRequestUID().equals(UID))
				{
					System.out.println("Your Request Status is :\t\t"+areq.getBookingStatus());
					break;
				}
			}

		}
		else
			System.out.println("No request found matching the supplied UID.");
	}
	@Override
	public void cancelRequest(String UID)
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		int index =-1;
		ArrayList<Request> requestsInDB = new ArrayList<Request>();
		//read from RequestRoomDB
		requestsInDB = (ArrayList<Request>)getRequestsMade();
		//search for request with supplied UID
		for(Request areq : requestsInDB)
		{
			if(areq.getRequestUID().equals(UID))
			{
				System.out.println("Found Request in DB. Removing request...");
				index=requestsInDB.indexOf(areq);
				//also modify
				break;
			}
		}
		if(index>=0)
			requestsInDB.remove(index);
		else
			System.out.println("No request found matching the supplied UID.");
		updateRoomRequestDB(requestsInDB);
	}
	private void writeRequestToDB()
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		File fileCheck = new File("RequestRoomDB");
		System.out.println("RequestRoomDB exists?\t"+fileCheck.exists());	
		//this check ensures system doesnt crash with FNF exception when writing to DB for the first Time	
		if(fileCheck.exists())
			readRequestFromDB();
		//Here can do this because the session allows only 1 booking and not multiple bookings unlike in AddRoom where 
		//1 session allowed multiple additions to be done to the RoomList ArrayList
		RequestsMade.add(newrequest);
		try{
			fos = new FileOutputStream("RequestRoomDB");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(RequestsMade);
		}catch(IOException e){
			System.out.println("Caught IOException while writing to RequestRoomDB. Error 21");
		}finally{
			try{
				if(fos!=null)
					fos.close();
			}catch(IOException e){
				System.out.println("IOEx1. Error 22");
			}
		}
	}
	private static void readRequestFromDB()
	{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			fis = new FileInputStream("RequestRoomDB");
			ois = new ObjectInputStream(fis);
			RequestsMade=(ArrayList<Request>)ois.readObject();
		}catch(FileNotFoundException e){
			System.out.println("Caught FileNotFoundException while reading from RequestRoomDB. Error 23");
		}catch(ClassNotFoundException e){
			System.out.println("Caught ClassNotFoundException while reading from RequestRoomDB. Error 24");
		}catch(IOException e){
			System.out.println("Caught IOException while reading from RequestRoomDB. Error 25");
		}finally{
			try{
				if(fis!=null)
					fis.close();
			}catch(IOException e)
			{	System.out.println("Caught IOEx while closing fileinputstream for reading from RequestRoomDB. Error 26");
			}
		}
	}
	public static void updateRoomRequestDB(ArrayList<Request> updatedList)
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try{
			fos = new FileOutputStream("RequestRoomDB");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(updatedList);
		}catch(IOException e){
			System.out.println("Caught IOException while writing to RequestRoomDB. Error 27");
		}finally{
			try{
				if(fos!=null)
					fos.close();
			}catch(IOException e){
				System.out.println("IOEx1. Error 28");
			}
		}

	}
	public static ArrayList<Request> getRequestsMade()
	{
		readRequestFromDB();
		return RequestsMade;
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
