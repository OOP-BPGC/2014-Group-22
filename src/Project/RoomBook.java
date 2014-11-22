package Project;

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
		System.out.println("\n##############################");
		System.out.println("\nROOM LIST WITH THEIR BOOKINGS");
		RoomDB.displayRooms();
		System.out.println("\n######################################################################################");
		System.out.println("Please scroll through the list of available rooms and choose a room which meets your");
		System.out.println("requirements. The bookings are also listed to help you avoid conflicting choices...");
		System.out.println("######################################################################################");

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
		char projector='y';

		System.out.println("\n\n#################################");
		System.out.println("FILL IN THE FOLLOWING FIELDS");
		System.out.println("#################################");
		//take projector request
		do{
		System.out.println("\n############################################");
		System.out.println("\nIs projector Required?(y/n)");
		projector=inp.next().charAt(0);
		}while(!newrequest.setProjectorRequired(projector));


		//take booking day input
		do{
		System.out.println("\n############################################");
		System.out.println("Room is to be booked for which day?");
		System.out.println("Enter a digit for day and press enter");
		System.out.println("then enter a digit for month (1-12) and press enter");
		System.out.println("then enter year (>2014) and press enter");
		day=inp.nextInt();
		month=inp.nextInt();
		year=inp.nextInt();
		}while(!newrequest.setBookingDate(day,month,year));

		//take starting time of booking
		do{
		System.out.println("\n############################################");
		System.out.println("\nStarting time of booking?");
		System.out.println("Enter time in 24 hour format,");
		System.out.println("Enter digit hours press enter,");
		System.out.println("then enter minutes (0-60) and press enter.");
		hour=inp.nextInt();		
		minutes=inp.nextInt();
		}while(!newrequest.setStartingTime(hour,minutes));
		
		//take 'duration' of booking input
		do{
		System.out.println("\n############################################");
		System.out.println("\nRoom is to be booked for how long?");
		System.out.println("Enter value in hours eg : 1.5, 2.5, 3 etc.");
		duration=inp.nextDouble();
		}while(!newrequest.setDuration(duration));
		
		//take attendance count	
		do{
		System.out.println("\n############################################");		
		System.out.println("Room is to be booked for how many persons?");
		attendance=inp.nextInt();
		}while(!newrequest.setAttendanceCount(attendance));

		//take reason for booking	
		inp.nextLine();
		System.out.println("\nEnter Reason for requesting room. Use only 1 Line.");	
		line=inp.nextLine();
		newrequest.setReason(line);


		while(confirmRoom==2)
		{
			System.out.println("----------------------------------------------");
			System.out.println("Enter choice of room:");
			System.out.println("(Do not change capital letters to small))");
			room=inp.next();
			int result =roomChoiceCheck(room,day,month,year,hour,minutes,duration,attendance,projector);
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
			else if(result==5)
			{
				System.out.println("#################################");
				System.out.println(room+" does not have a working projector.");
				System.out.println("Please choose a different room.");
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
	 *	 5 : Projector requested but the room doesnt have a working projector.
	 */
	public static int roomChoiceCheck(String room, int day,int month,int year, int hourRT1,int minRT1, double duration,int attendance,char projector)
	{
		ArrayList<Room> roomList = new ArrayList<Room>();
		roomList=RoomDB.getRoomList();
		String date = day+"/"+month+"/"+year;
		String time = hourRT1+":"+minRT1;
		for(Room aroom : roomList)
		{
			if(aroom.getRoomNumber().equals(room))
			{
				if(aroom.getCapacity()<attendance)
					return 2;
				else if((!aroom.getProjectorStatus())&&(projector=='y'||projector=='Y'))
					return 5;
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
								//T1
								int hourT1 = Integer.parseInt(booked[0]);
								int minT1 = Integer.parseInt(booked[1]);
								double durationBooked = Double.parseDouble(durations.get(j));
								//T2
								int hourT2=0, minT2=0;
								if(Math.floor(durationBooked)==durationBooked)
								{
									hourT2=hourT1+(int)durationBooked;
									minT2=minT1;
								}
								else
								{
									if(minT1+30>60)
									{
										minT2=minT1+30-60;
										hourT2=1+hourT1+(int)durationBooked;
									}	
									else
									{
										hourT2=hourT1+(int)durationBooked;
										minT2=minT1+30;
									}
								}
								//RT2
								int hourRT2=0, minRT2=0;
								if(Math.floor(duration)==duration)
								{
									hourRT2=(int)duration+hourRT1;
									minRT2=minRT1;
								}
								else
								{
									if(minRT1+30>60)
									{
										minRT2=minRT1+30-60;
										hourRT2=1+hourRT1+(int)duration;
									}
									else
									{
										hourRT2=hourRT1+(int)duration;
										minRT2=minRT1+30;
									}
								}/*
								System.out.println("##############");
								System.out.println(hourT1+":"+minT1+"-"+hourT2+":"+minT2);
								System.out.println(hourRT1+":"+minRT1+"-"+hourRT2+":"+minRT2);
								System.out.println("##############");*/
								if(hourRT1==hourT1&&minRT1==minT1)
									return 3;
								else if((hourRT2<hourT1)||(hourRT2==hourT1&&minRT2<=minT1))
									return 1;
								else if((hourRT1>hourT2)||(hourRT1==hourT2&&minRT2>=minT2))
									return 1;
								else return 4;
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
		int found=0;
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
					found=1;
					break;
				}
			}

		}
		else
			System.out.println("\n\n--------There are no requests in the database.");
		if(found==0)
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
		//read from RoomDB
		ArrayList<Room> recoveredRoomList = RoomDB.getRoomList();
		//search for request with supplied UID
		int remove=0;
		char confirm='n';
		Scanner inp = new Scanner(System.in);
		for(Request areq : requestsInDB)
		{
			if(areq.getRequestUID().equals(UID))
			{
				System.out.println("Found Request in DB. Removing request...");
				index=requestsInDB.indexOf(areq);
				//also modify Rooms in DataBase to nullify booking IF its an approved Request
				if(areq.getBookingStatus().equals("Approved"))
				{
					System.out.println("\n\n###########################################################################");
					System.out.println("Your request has been approved and "+areq.getRoom()+" has been booked for you.");
					System.out.println("Are you sure you want to cancel your request and hence cancel the Booking?(y/n)");
					System.out.println("###############################################################################");
					confirm=inp.next().charAt(0);
					if(confirm=='y'||confirm=='Y')
					{
						for(Room aroom : recoveredRoomList)
						{
							if(aroom.getRoomNumber().equals(areq.getRoom()))
							{
								ArrayList<String> ids = aroom.getUID();
								int index2 = ids.indexOf(areq.getRequestUID());
								aroom.removeBooking(index2);
								remove=1;
							}
						}
					}
					else
						System.out.println("\n\nAborting...");
				}
				break;
			}
		}
		if(index>=0)
			requestsInDB.remove(index);
		else
			System.out.println("No request found matching the supplied UID.");
		updateRoomRequestDB(requestsInDB);
		if(remove==1)
			RoomDB.updateDB(recoveredRoomList);
	}
	private void writeRequestToDB()
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		File fileCheck = new File("RequestRoomDB");
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
			System.out.println("##########################################");
			System.out.println("RoomReuqest Database has not been created.");
			System.out.println("##########################################");
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
