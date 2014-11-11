package Project;
//import Project.User;
import Project.Book;
import Project.Room;
import Project.RoomDB;
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

public class RoomBook extends Book
{	
	private Request newrequest = new Request();
	private static ArrayList<Request> RequestsMade = new ArrayList<Request>();	
	@Override
	public String generateUID()
	{
		String[] letters={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		String[] nos={"0","1","2","3","4","5","6","7","8","9"};
		int len1 = letters.length;
		int len2 = nos.length;
		int rand1=(int)(Math.random()*len1);
		int rand2=(int)(Math.random()*len2);
		int rand3=(int)(Math.random()*len1);
		int rand4=(int)(Math.random()*len2);
		return letters[rand1]+nos[rand2]+letters[rand3]+nos[rand4];		
	}
	@Override
	public void generateForm()
	{
		Scanner inp = new Scanner(System.in);
		String line="";
		int roomBookedFlag=0;
		//setID();
		//user details have been taken.
		String room="";
		//char variable to run room choosing menu
		char roomch = 'y';

		//take reason for booking	
		System.out.println("Enter Reason for requesting room. Use only 1 Line.");	
		line=inp.nextLine();
		newrequest.setReason(line);

		//take booking day input
		System.out.println("Please enter the following Booking Details to resolve clashes with other bookings, if any...");
		System.out.println("Room is to be booked for which day? (Enter date in format as 7/1/2014 or 12/11/2014 only)");
		line=inp.nextLine();
		newrequest.setBookingDate(line);

		//take starting time of booking
		System.out.println("Starting time of booking? (Enter time in 24 hour format as hh:mm only)");
		newrequest.setStartingTime(inp.next());

		//take 'duration' of booking input
		System.out.println("Room is to be booked for how long (enter digit 'n' for 'n' hours or 'm.5' for m and a half hours)?");
		newrequest.setDuration(inp.next());

		//take attendance count			
		System.out.println("Room is to be booked for how many persons? (Enter digit 'n' for 'n' persons)");
		newrequest.setAttendanceCount(inp.nextInt());

		//display rooms from DB for choosing
		char confirmBooking='y';
		while(roomch=='y'||roomch=='Y')
		{
			roomBookedFlag=0;
			System.out.println("ROOMS\n----------------------------");
			RoomDB.displayRooms();
			System.out.println("\n\nChoose a room");
			room=inp.next();				
			if(roomChoiceCheck(room)==-1)
				System.out.println("Sorry!! The room you chose does not exist.\nMake sure you enter the exact room name, for example: for 'A605' do not enter 'a605'.");
			else if(roomChoiceCheck(room)==0)
				System.out.println("Sorry!! The room chosen is not available.");
			else if(roomChoiceCheck(room)==1)
			{
				newrequest.setRoom(room);
				System.out.println("\n\n"+room+" has been marked for booking for you.");
				System.out.println("\nPlease keep in mind that in case there are multiple bookings for the same room\nand at the same time slot then the admin will approve the first posted booking\nrequest with valid reason and the others shall be denied.");
				newrequest.setRequestUID(generateUID());
				System.out.println("Your request UID is\t"+newrequest.getRequestUID()+"\nYou can use it to review request status or cancel booking Request.");
				//Ask user to confirm Booking
				System.out.println("Confirm that you want to make this request. Enter 'y' or 'n'.");
				confirmBooking=inp.next().charAt(0);
				if(confirmBooking=='y'||confirmBooking=='Y')
				{
					roomBookedFlag=1;//confirm	
					break;
				}
				else
				{
					//discard booking
					System.out.println("Your request has been discarded.");
					System.out.println("EXECUTING BREAK;");
					break;
				}
			}
			System.out.println("Choose a different Room?(y/n)");
			roomch=inp.next().charAt(0);
		}
		if(roomBookedFlag==1)
			writeRequestToDB();
	}	
	@Override
	public void displayStatus(String UID)
	{
		ArrayList<String> adminResponses = AdminRoom.getResponseAndUID();
		String[] temp = new String[2];
		int found =0;
		for(String line : adminResponses)
		{
			temp = line.split("-",0);
			if(temp[0].equals(UID))
			{
				System.out.println("Your request status is\t\t:\t"+temp[1]);
				found=1;
				break;
			}
		}
		if(found==0)
			System.out.println("No request found in Database that matches supplied UID");			
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
				break;
			}
		}
		if(index>=0)
			requestsInDB.remove(index);
		else
			System.out.println("No request found matching the supplied UID.");
		updateRoomRequestDB(requestsInDB);
	}
	private int roomChoiceCheck(String room)
	{
		ArrayList<Room> roomList = new ArrayList<Room>();
		roomList=RoomDB.getRoomList();
		String roomName = "";
		for(Room aroom : roomList)
		{
			roomName=aroom.getRoomNumber();
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
						if(aday.equals(newrequest.getBookingDate()))
						{
							for(String time : startingTimes)
							{
								if(time.equals(newrequest.getStartingTime()))
								{
				
								  System.out.println(room+" is already booked for the same Day and Time.");
									return 0;
								}
							}
						}
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
