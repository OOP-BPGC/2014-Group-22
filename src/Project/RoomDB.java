package src;
import java.util.Scanner;
import java.util.ArrayList;
//for writing objects
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
//for reading objects
import java.io.FileInputStream;
import java.io.ObjectInputStream;
//exceptions
import java.io.IOException;
import java.io.FileNotFoundException;
/*
 *@author AbhishekTiwari
 */
public class RoomDB 
{
	private static ArrayList<Room> RoomList = new ArrayList<Room>();
	private static ArrayList<Room> recoveredRoomList = new ArrayList<Room>();
	public static void addRoom()
	{
		char addRoom = 'y';
		char projector = 'n';
		Scanner inp = new Scanner(System.in);
		String roomCheck="";
		int flag=0;
		//Clear RoomList first to avoid repetition of objects while adding multiple rooms in the same login session
		if(RoomList.size()!=0)
			RoomList.clear();
		readFromDB();				
		while(addRoom=='y'||addRoom=='Y')
		{
			flag=0;
			Room newroom = new Room();
			System.out.println("Enter Room Number");
			roomCheck=inp.next();
			for(Room temp : recoveredRoomList)
			{
				if(temp.getRoomNumber().equals(roomCheck))
				{
					flag=1;
					break;
				}
			}
			for(Room temp : RoomList)
			{
				if(temp.getRoomNumber().equals(roomCheck))
				{
					flag=1;
					break;
				}
			}
			if(flag==1)
			{
				System.out.println(roomCheck+" already exists in Database!!\n\n");
				System.out.println("Add more rooms?(y/n)");
				addRoom=inp.next().charAt(0);
				continue;
			}
			newroom.setRoomNumber(roomCheck);
			System.out.println("Enter Room's Projector status(y/n)");
			projector = inp.next().charAt(0);
			if(projector=='y'||projector=='Y')
				newroom.setProjectorStatus(true);
			else
				newroom.setProjectorStatus(false);			
			newroom.setStatus("Available");
			System.out.println("Enter Upper Limit of Room's Capacity (eg: 50, 100, 300 etc.)");
			newroom.setCapacity(inp.nextInt());
			RoomList.add(newroom);
			System.out.println(newroom.getRoomNumber()+" added to Rooms List. Status is set to Available by default.");
			System.out.println("Add more rooms?(y/n)");
			addRoom=inp.next().charAt(0);
		}
		//as soon as we've taken all inputs into RoomList object, write it to DB
		//as opposed to this we can write each object to DB as it is taken, DISCUSS which is better.
		writeToDB();
	}
	public static void removeRoom()
	{
		char again='y', confirm='n';
		String room="";
		Scanner inp = new Scanner(System.in);
		int found=0;
		int index=0;
		int flag=0;
		while(again=='y'||again=='Y')
		{
			found=0;
			//read in list of rooms
			readFromDB();
			if(recoveredRoomList.size()==0)
			{
				System.out.println("\n\n!!!! No rooms in Database. Aborting...");
				break;
			}
			System.out.println("Enter name of room to be removed.");
			room=inp.next();
			for(Room temp : recoveredRoomList)
			{
				if(temp.getRoomNumber().equals(room))
				{
					found=1;
					if(temp.getStatus().equals("Booked"))
						System.out.println("\n\n"+room+" already has bookings. Deleting room will remove all bookings!");
					System.out.println("\n\n-------------Are you sure you want to remove "+room);
					confirm=inp.next().charAt(0);
					if(confirm=='y'||confirm=='Y')
					{
						flag=1;
						index=recoveredRoomList.indexOf(temp);
						break;
					}
					else break;					
				}
			}
			if(flag==1)	
			{
				recoveredRoomList.remove(index);
				updateDB(recoveredRoomList);
				System.out.println("\n"+room+" removed from Database.");
			}
			if(found==0)
				System.out.println("\n\n!! "+room+" not found in Database.");
			System.out.println("\n\nRemove more rooms?(y/n)");
			again=inp.next().charAt(0);
		}

	}
	public static void writeToDB()
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		File fileCheck = new File("RoomDB");
		//this check ensures system doesnt crash with FNF exception when writing to DB for the first Time	
		if(fileCheck.exists())
			readFromDB();
		recoveredRoomList.addAll(RoomList);
		try{
			fos = new FileOutputStream("RoomDB");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(recoveredRoomList);
		}catch(IOException e){
			System.out.println("Caught IOException while writing to DB.");
		}finally{
			try{
				if(fos!=null)
					fos.close();
			}catch(IOException e){
				System.out.println("IOEx1");
			}
		}
	}
	public static void readFromDB()
	{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			fis = new FileInputStream("RoomDB");
			ois = new ObjectInputStream(fis);
			recoveredRoomList=(ArrayList<Room>)ois.readObject();
		}catch(FileNotFoundException e){
			System.out.println("####################################");
			System.out.println("Room Database has not been created.");
			System.out.println("####################################");
		}catch(ClassNotFoundException e){
			System.out.println("Caught ClassNotFoundException while reading from DB.");
		}catch(IOException e){
			System.out.println("Caught IOException while reading from DB.");
		}finally{
			try{
				if(fis!=null)
					fis.close();
			}catch(IOException e)
			{	System.out.println("Caught IOEx while closing fileinputstream for reading from DB");
			}
		}
	}
	public static void updateDB(ArrayList<Room> listOfRoomsToWrite)
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try{
			fos = new FileOutputStream("RoomDB");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(listOfRoomsToWrite);
		}catch(IOException e){
			System.out.println("Caught IOException while writing to DB.");
		}finally{
			try{
				if(fos!=null)
					fos.close();
			}catch(IOException e){
				System.out.println("IOEx1");
			}
		}
	}
	public static void displayRooms()
	{	
		readFromDB();
		for(Room aroom : recoveredRoomList)
			aroom.displayRoom();
	}
	public static ArrayList<Room> getRoomList()
	{
		readFromDB();
		return recoveredRoomList;
	}
	public static void clearDB()
	{
		readFromDB();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ArrayList<String> bookingdates =null;
		int count=0;
		for(Room aroom : recoveredRoomList)
		{
			bookingdates= aroom.getBookingDate();
			int size = bookingdates.size();
			for(int j=0; j<size; j++)
			{
				if(Book.compareDate(bookingdates.get(j),null)==-1)
				{
					count++;
					aroom.removeBooking(j);
				}
			}
		}
		updateDB(recoveredRoomList);
		System.out.println("###########################");
		System.out.println("Records Updated. "+count+" booking(s) removed.");
		
	}
}
