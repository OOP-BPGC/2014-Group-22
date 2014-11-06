
import java.util.Scanner;
import java.util.ArrayList;
import java.io.RandomAccessFile;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;

public class RoomDB
{
	private static ArrayList<Room> RoomList = new ArrayList<Room>();
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
        
	private static void writeToDB()
	{
		PrintWriter out = null;
		for(Room aroom : RoomList)
		{
			try{
				out = new PrintWriter(new BufferedWriter(new FileWriter("RoomDB.txt",true)));
				//'true' flag in above tells FileWriter to append rather than clear.
				out.println(aroom.getRoomNumber());
				out.println(aroom.getProjectorStatus());
				out.println(aroom.getStatus());
				out.println(aroom.getBookingDate());
				out.println(aroom.getStartingTime());
				out.println(aroom.getDuration());
				out.println(aroom.getCapacity());
				out.println("*");			
			}catch(IOException e1){
				System.out.println("Caught IOException while writing to DB.");
			}finally{
				if(out!=null)
				out.close();
			}
		}
	}
	public static void displayRooms()
	{
		String line = null;
		try{
			RandomAccessFile raf = new RandomAccessFile("RoomDB.txt","r");
			while((line=raf.readLine())!=null)	//firstline reads Room Number as that is first entry of DB
			{
				while(!line.equals("*"))
				{
					System.out.println("Room Number :\t"+line);
					line=raf.readLine();
					System.out.println("Projector Available: \t"+line);
					line=raf.readLine();
					System.out.println("Room Status: \t"+line);
					line=raf.readLine();
					System.out.println("Booking Date: \t"+line);
					line=raf.readLine();
					System.out.println("Booking Time: \t"+line);
					line=raf.readLine();
					System.out.println("Booking Duration: \t"+line);
					line=raf.readLine();
					System.out.println("Room Capacity: \t"+line);
					System.out.println("---------------------------------");
					//next readLine() will pick up "*" and break out of this loop
					line=raf.readLine();
					//furthermore, the readLine() of outer loop will read in the Room Number of the next Room. 					
				}
			}
		}catch(FileNotFoundException fnf)
		{
			System.out.println("Caught file not found exception while writing to DB");
		}
		catch(IOException io)
		{
			System.out.println("Caught file IOException while writing to DB");
		}	
	}
}


