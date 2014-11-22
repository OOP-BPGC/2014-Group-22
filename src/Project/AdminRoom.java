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
			System.out.println("1.Add a Room.\n2.Remove a Room.\n3.Evaluate Requests.\n4.Display Rooms.");
			System.out.println("5.Previous Menu.\n6.Clear Database.\n7.Exit.\nEnter choice: ");
			choice=inp.nextInt();
			int prevMenu =0;
			switch(choice)
			{
				case 1: RoomDB.addRoom();
					break;
				case 2: RoomDB.removeRoom();
					break;
				case 3: AdminRoom.evaluateRequests();
					break;
				case 4: RoomDB.displayRooms();
					break;
				case 5: prevMenu=1;
					break;	
				case 6: RoomDB.clearDB();
					break;
				case 7: System.exit(0);
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
		ArrayList<Room> RoomsInDB = null;
		int decision=-1;
		char confirm ='y';
		String response="";
		int r =0;
		int atleastOnce=0;
		//for each request in DB do the following
		for(Request areq : RequestsInDB)
		{
			if(areq.getEvaluated())
				continue;
			//display request if valid
			atleastOnce=1;
			if(resolveConflicts(areq))
			{
				System.out.println("\nRequest No.\t:\t\t"+(++r));
				System.out.println("\n\n-------------------------------------");
				areq.displayRequest();
			}
			else
			{
				//dont display request as its invalid.
				areq.setEvaluated(true);
				areq.setBookingStatus("Denied by sofware due to conflicts with existing bookings.");
				RoomBook.updateRoomRequestDB(RequestsInDB);				
				continue;
			}
			//ask admin to pass judement on the Room booking Request also implement Confirmation
			while(true)
			{
				System.out.println("1.APPROVE.\n2.DENY.\n3.EVALUATE LATER.\nEnter Decision:\n");
				decision=inp.nextInt();
				if(decision==1)
				{
					response="Approved";//set it in program as user may make spelling mistake while 					//typing 'approved' or 'denied'
					System.out.println("You have selected to APPROVE the booking request.");
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
			//do the following based on admins decision.
			if(response.equals("Approved"))
			{
				RoomsInDB=RoomDB.getRoomList();
				areq.setEvaluated(true);
				areq.setBookingStatus("Approved");
				for(Room aroom : RoomsInDB)
				{
					if(aroom.getRoomNumber().equals(areq.getRoom()))
					{
						aroom.setStatus("Booked");
						aroom.setBookingDate(areq.getBookingDate());
						aroom.setStartingTime(areq.getStartingTime());
						aroom.setDuration(areq.getDuration());
						aroom.setUID(areq.getRequestUID());
					}
				}
				System.out.println("Writing to RoomDB with updated room information...");
				RoomDB.updateDB(RoomsInDB);
				RoomBook.updateRoomRequestDB(RequestsInDB);				
				System.out.println("Records Updated.");		
			}
			else if(response.equals("Denied"))
			{
				areq.setEvaluated(true);
				areq.setBookingStatus("Denied");
				RoomBook.updateRoomRequestDB(RequestsInDB);				
			}
			else if(response.equals("Pending"))
			{
				areq.setEvaluated(false);
				areq.setBookingStatus("Pending");
				RoomBook.updateRoomRequestDB(RequestsInDB);				
			}
		}
		if(atleastOnce==0){
			System.out.println("\n\n#############################################");
			System.out.println("There are no pending Requests for evaluation.");
			System.out.println("#############################################");
		}
	}
	public static boolean resolveConflicts(Request areq)
	{
		//update RequestsInDB to nullify requests that were not conflicting at time of booking but
		//are now conflicting because the admin approved some request
		String[] dateTemp = areq.getBookingDate().split("/",0);
		int day= Integer.parseInt(dateTemp[0]);//day
		int month= Integer.parseInt(dateTemp[1]);//month
		int year= Integer.parseInt(dateTemp[2]);//year
		String[] timeTemp = areq.getStartingTime().split(":",0);
		
		int hours= Integer.parseInt(timeTemp[0]);//hours
		int minutes= Integer.parseInt(timeTemp[1]);//minutes
		double duration = Double.parseDouble(areq.getDuration());
		int attendance = areq.getAttendanceCount();
		char proj='n';
		if(areq.getProjectorRequired())
			proj='y';
		int result = RoomBook.roomChoiceCheck(areq.getRoom(),day,month,year,hours,minutes,duration,attendance,proj);
		if(result==1)	
			return true;
		else
			return false;
	}
	private static void clearRequestDB()
	{
		ArrayList<Request> allRequests = RoomBook.getRequestsMade();
		ArrayList<Request> pendingRequests = new ArrayList<Request>();
		for(Request areq : allRequests)
		{
			if(areq.getBookingStatus().equals("Pending"))
				pendingRequests.add(areq);
		}
		RoomBook.updateRoomRequestDB(pendingRequests);
	}
}
