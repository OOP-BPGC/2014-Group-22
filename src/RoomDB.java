package Project;
import Project.Room;
import java.util.Scanner;
import java.util.ArrayList;
//for writing objects
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
//for reading objects
import java.io.FileInputStream;
import java.io.ObjectInputStream;
//exceptions
import java.io.IOException;
import java.io.FileNotFoundException;

public class RoomDB 
{
	private static ArrayList<Room> RoomList = new ArrayList<Room>();
	private static ArrayList<Room> recoveredRoomList = new ArrayList<Room>();
	public static void addRoom()
	{
		char addRoom = 'y';
		char projector = 'n';
		Scanner inp = new Scanner(System.in);
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
	/*
	private static void copyappend(String src, String dst)
	{
		//append file1 to file2, both are required to be binary files holding object data
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try{
			fis = new FileInputStream(src);
			fos=new FileOutputStream(dst,true);	//append mode
			byte[] buffer = new byte[1024];
			int noOfBytes=0;
			while((noOfBytes=fis.read(buffer))!=-1)
				fos.write(buffer);
		}catch(FileNotFoundException e){
			System.out.println("Caught FileNotFoundException while copying RoomDBtemp to RoomDB.");
		}catch(IOException e){
			System.out.println("Caught IOException while copying RoomDBtemp to RoomDB.");
		}finally{
			try{
				if(fis!=null)
					fis.close();
				if(fos!=null)
					fos.close();
			}catch(IOException e){
				System.out.println("Caught IOException while closing input output streams when copying RoomDBtemp to RoomDB.");
			}
		}
	}*/

	/*
	private static void writeToDB()
	{
		//serialize the RoomList
		try{
			File file1 = new File("RoomDB");
			int flag=0;
			FileOutputStream file = null;
			ObjectOutputStream output = null;
			if(file1.exists())
			{
				file = new FileOutputStream("RoomDBtemp");
				flag=1;
			}
			else
				file = new FileOutputStream("RoomDB");
			output = new ObjectOutputStream(file);
			output.writeObject(RoomList);
			output.close();
			if(flag==1)
			{
				flag=0;
				copyappend("RoomDBtemp","RoomDB");
			}
		}catch(IOException e){
			System.out.println("Caught an IOException1 while writing to DB. Printing StackTrace");
			e.printStackTrace();
		}
	}
	*/
