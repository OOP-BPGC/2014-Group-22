package Project;
import Project.Room;
import java.util.Scanner;
import java.util.ArrayList;
//for writing objects
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
		//Clear RoomList first to avoid repition of objects while adding multiple rooms in the same login session
		if(RoomList.size()!=0)
			RoomList.clear();				
		while(addRoom=='y'||addRoom=='Y')
		{
			Room newroom = new Room();
			System.out.println("Enter Room Number");
			newroom.setRoomNumber(inp.next());
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
			System.out.println("Caught FileNotFoundException while reading from DB.");
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
}
