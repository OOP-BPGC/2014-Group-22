package Project;
//to addrooms, display rooms
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
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
public class AdminRoom
{
	private static ArrayList<String> responseAndUID = new ArrayList<String>();
	private static ArrayList<String> recoveredReponseAndUID = new ArrayList<String>();
	public static void main(String[] args)
	{
		runAdminSession();	
	}
	public static void runAdminSession()
	{
		char AdminMenu = 'y';
		int choice = 0;
		Scanner inp = new Scanner(System.in);
		while(AdminMenu=='y'||AdminMenu=='Y')
		{
			System.out.println("ADMIN MENU");
			System.out.println("-----------------------------");
			System.out.println("1.Add Room.\n2.Modify Room Details.\n3.Evaluate Requests.\n4.Display Rooms.");
			System.out.println("5.Previous Menu.\n6.Exit\nEnter choice: ");
			choice=inp.nextInt();
			int prevMenu =0;
			switch(choice)
			{
				case 1: RoomDB.addRoom();
					break;
				case 2: System.out.println("UNDER CONSTRUCTION :)");
					break;
				case 3: AdminRoom.evaluateRequests();
					break;
				case 4: RoomDB.displayRooms();
					break;
				case 5: prevMenu=1;
					break;
				case 6: System.exit(0);
					break;
				default: System.out.println("Wrong Choice. Please Try Again.");
			}
			if(prevMenu==0){
				System.out.println("Show Admin Menu again?(y/n)");
				AdminMenu=inp.next().charAt(0);
			}
			else
				AdminMenu='n';
		}
	}
	private static void evaluateRequests()
	{
		Scanner inp = new Scanner(System.in);
		//read from RoomRequestDB
		ArrayList<Request> RequestsInDB = RoomBook.getRequestsMade();
		//read from RoomDB
		ArrayList<Room> RoomsInDB = RoomDB.getRoomList();
		ArrayList<String> bookingDates= null; 
		ArrayList<String> startingTimes= null;
		ArrayList<String> durations= null;
		ArrayList<Request> requestsPending = new ArrayList<Request>();
		int decision=-1;
		char confirm ='y';
		String response="";
		int r =0;
		boolean fatalConflict = false;

		//First read in responses from admin on requests he didnt clear in any previous sessions
		//this is to later avoid a case where we end up with the entries "UID1 Pending" and "UID1 Approved"
		//In above case "UID1 Pending" must be replaced by "UID1 Approved" or "UID1 Denied" as decided by admin
		readFromResponseUIDDB();

		//for each request in DB do the following
		for(Request areq : RequestsInDB)
		{
			System.out.println("\nRequest No.\t:\t\t"+(++r));
			System.out.println("-------------------------------------");
			//display request
			areq.displayRequest();
	
			//After Displaying a request for a particular room, display all current bookings for that room
			//so that the admin can prevent clashes.
			System.out.println("\nThe Room Requested already has the following Bookings.");
			System.out.println("NOTE TO ADMIN: IF THERE IS A CLASH PLEASE DO NOT APPROVE THE BOOKING!");
			System.out.println("--------------------------------------------------------------------------------");
			for(Room aroom : RoomsInDB)
			{
				//1.Room number in request == room in DB
				//2.Status of requested room == Booked
				//3.Booking Dates arraylist of that room -> Contains the Booking date given in Request
				//THEN do the following
				if(aroom.getRoomNumber().equals(areq.getRoom()) && aroom.getStatus().equals("Booked") && aroom.getBookingDate().contains(areq.getBookingDate()))
				{
					//for possible clashing room, get the following details and print them
					bookingDates=aroom.getBookingDate();
					startingTimes=aroom.getStartingTime();
					durations=aroom.getDuration();
					int noOfBookings = bookingDates.size();
					//print them
					for(int j=0; j<noOfBookings; j++)
					{
						System.out.println("Booking no.\t\t:\t"+(j));
						System.out.println("Booking Date\t\t:\t"+bookingDates.get(j));
						System.out.println("Starting Time\t\t:\t"+startingTimes.get(j));
						System.out.println("Duration\t\t:\t"+durations.get(j));
					}
					int index = bookingDates.indexOf(areq.getBookingDate());
					fatalConflict = startingTimes.get(index).equals(areq.getStartingTime());
					if(fatalConflict){
						System.out.println("THIS BOOKING HAS A FATAL CONFLICT.");
						System.out.println("On the requested day and time there is already a booking.");
					}
				}
			}
			
			//ask admin to pass judement on the Room booking Request also implement Confirmation
			while(true)
			{
				System.out.println("1.APPROVE.\n2.DENY.\n3.EVALUATE LATER.\nEnter Decision:\n");
				decision=inp.nextInt();
				if(decision==1)
				{
					if(fatalConflict){
						System.out.println("Fatal Conflict, Cannot approve request. Please Deny the request.");
						continue;
					}
					else{
						response="Approved";//set it in program as user may make spelling mistake while 						//typing 'approved' or 'denied'
						System.out.println("You have selected to APPROVE the booking request.");
					}
				}
				else if(decision==2)
				{
					response="Denied";
					System.out.println("You have selected to DENY the booking request.");
				}
				else if(decision==3)
				{
					response="Pending";
					System.out.println("You have selected to EVALUATE LATER.");
				}
				else 
				{
					System.out.println("Wrong digit entered. Please Try Again.");
					continue;					
				}
				System.out.println("Confirm Decision?(y/n)");
				confirm=inp.next().charAt(0);
				if(confirm=='y'||confirm=='Y')
					break;
				else
					continue;
			}
			//Admin has passed judgement, update responseUID list
			//But first check if the ResponseUIDDB already has an entry for this request (will happen if it were left..
			//...as pending by the admin in some previous session) in that case remove it and add new entry.
			int index=-1;
			String[] temp = new String[2];
			for(String rplusUID : responseAndUID)
			{
				temp = rplusUID.split("-",0); 
				if(temp[0].equals(areq.getRequestUID()))
				{
					index = responseAndUID.indexOf(rplusUID);
					break;
				}
			}
			if(index>=0)
				responseAndUID.remove(index);
			responseAndUID.add(areq.getRequestUID()+"-"+response);
			//update ResponseUIDDB
			writeToResponseUIDDB();
			
			//If respons is "Approved" then update DB to reflect confirmation, so that if any further requests clash 
			//with this one the admin will come to know via the clash prevention mechanism implemented above
			if(response.equals("Approved"))
			{
				for(Room aroom : RoomsInDB)
				{
					if(aroom.getRoomNumber().equals(areq.getRoom()))
					{
						aroom.setStatus("Booked");
						aroom.setBookingDate(areq.getBookingDate());
						aroom.setBookingTime(areq.getStartingTime(),areq.getDuration());
					}
				}
			}
			System.out.println("Writing to RoomDB with updated room information...");
			RoomDB.updateDB(RoomsInDB);
			System.out.println("Records Updated.");		
			//incase the admin chooses to evaluate later, keep that request in database, delete the others
			if(response.equals("Pending"))
				requestsPending.add(areq);
		}
		//update requests DB AFTER all requests are Evaluated to keep only pending requests and DELETE the others.
		RoomBook.updateRoomRequestDB(requestsPending);
	}
	
	private static void writeToResponseUIDDB()
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try{
			fos = new FileOutputStream("ResponseUIDDB");
			oos = new ObjectOutputStream(fos);
			//note here we are not recovering first and then overwriting (as we do in RoomDB and RoomBook)
			//because in any scenario the readFromResponseUIDDB() is called first and then only this write fn is called!
			//see lines 63-66
			//see lines 102 in RoomBook 
			//the Above two use cases only will lead to fn call to this method
			oos.writeObject(responseAndUID);			
		}catch(IOException e)
		{
			System.out.println("Caught IOException while writing response and UID to file. Error 31");
		}finally{	
			try{
				if(fos!=null)
					fos.close();
			}catch(IOException e)
			{
				System.out.println("Caught IOException while writing response and UID to file. Error 32");
			}
		}
	}
	private static void readFromResponseUIDDB()
	{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		File fileCheck = new File("ResponseUIDDB");
		if(fileCheck.exists())
		{
			try{
				fis = new FileInputStream("ResponseUIDDB");
				ois = new ObjectInputStream(fis);
				responseAndUID=(ArrayList<String>)ois.readObject();
			}catch(FileNotFoundException e){
				System.out.println("Caught FileNotFoundException while reading from ResponseUIDDB. Error 33");
			}catch(ClassNotFoundException e){
				System.out.println("Caught ClassNotFoundException while reading from ResponseUIDDB. Error 34");
			}catch(IOException e){
				System.out.println("Caught IOException while reading from ResponseUIDDB. Error 35");
			}finally{
				try{
					if(fis!=null)
						fis.close();
				}catch(IOException e)
				{	System.out.println("Caught IOEx while closing fileinputstream for reading from ResponseUIDDB. Error 36");
				}
			}
		}
	}
	public static ArrayList<String> getResponseAndUID()
	{
		readFromResponseUIDDB();
		return responseAndUID;
	}
	public static void updateResponseUIDDB(ArrayList<String> responses)
	{	
		responseAndUID.addAll(responses);
		writeToResponseUIDDB();
	}

}
