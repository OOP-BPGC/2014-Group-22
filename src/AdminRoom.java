
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class AdminRoom
{
	public static void main(String[] args)
	{
		char AdminMenu = 'y';
		int choice = 0;
		Scanner inp = new Scanner(System.in);
		while(AdminMenu=='y'||AdminMenu=='Y')
		{
			System.out.println("1.Add Room.\n2.Modify Room Details.\n3.Evaluate Requests.\n4.Display Rooms.\nEnter choice: ");
			choice=inp.nextInt();
			switch(choice)
			{
				case 1: RoomDB.addRoom();
					break;
				case 2: break;
				case 3: AdminRoom.evaluateRequests();
					break;
				case 4: RoomDB.displayRooms();
					break;
				default: System.out.println("Wrong Choice. Please Try Again.");
			}
			System.out.println("Show Admin Menu again?(y/n)");
			AdminMenu=inp.next().charAt(0);
		}
	}
	
	
	private static void evaluateRequests()
	{
		String line = null;
		//to be set by Admin as Approved, Denied.
		String response = null;
		int decision=0;
		char confirm='n';
		RandomAccessFile raf = null;
		Scanner inp = new Scanner(System.in);
		ArrayList<String> ListOfRoomsApproved = new ArrayList<String>();
		String roomRequested = null;
		String bookingDate=null;
		String startingTime=null;
		String duration=null;
		try{
			/*
				For each Record in RequestRoomDB:
				line 1: ID number
				line 2: Request UID
				line 3: Room Number 
				line 4: Reason
				line 5: BookingDate
				line 6: StartingTime
				line 7: Duration
				line 8: ExpectedAttendance
			*/
			raf = new RandomAccessFile("RequestRoomDB","r");
			while((line=raf.readLine())!=null)//reads line1 of record
			{
				System.out.println("ID number of Applicant:\n"+line);
				line=raf.readLine();//line2
				System.out.println("Request UID:\n"+line);

				line=raf.readLine();//line3
				roomRequested=line;
				System.out.println("Room requested:\n"+line);

				line=raf.readLine();//line4
				System.out.println("Reason for request:\n"+line);

				line=raf.readLine();//line5
				bookingDate=line;
				System.out.println("Booking Date:\n"+line);

				line=raf.readLine();//line6
				startingTime=line;
				System.out.println("Starting Time of booking:\n"+line);

				line=raf.readLine();//line7
				duration=line;
				System.out.println("Duration of Booking:\n"+line);

				line=raf.readLine();//line8, last line of this record
				System.out.println("Expected Attendance:\n"+line);
				System.out.println("---------------------------------");
				//possible infinite loop risk
				while(true)
				{
					System.out.println("1.Approve Request\n2.Deny Request\nEnter 1 or 2.\n");
					decision=inp.nextInt();
					if(decision==1)
					{
						response="Approved";//set it in program as user may make spelling mistake while 						//typing 'approved' or 'denied'
						System.out.println("You have selected to APPROVE the booking request.");
					}
					else if(decision==2)
					{
						response="denied";
						System.out.println("You have selected to DENY the booking request.");
					}
					else 
					{
						System.out.println("Wrong digit entered. Please enter 1 for APPROVAL or 2 for DENIAL of room request.");
						continue;
					}
					System.out.println("Confirm Decision?(y/n)");
					confirm=inp.next().charAt(0);
					if(confirm=='y'||confirm=='Y')
						break;
					else
						continue;
				}
				if(response.equals("Approved"))
					ListOfRoomsApproved.add(roomRequested);
				//need to make these this list so that we can easily modify RoomDB AFTER all requests are reviewed
				//so as to minimise the number of times the RoomDB is modified, as there are chances of file corruption
				//due to powerloss etc when a file is being modified.
			}
			System.out.println("You have reviewed all requests.");
			System.out.println("Modifying RoomDB Records to reflect Bookings...");
			//modify RoomDB to reflect changes
			ArrayList<Room> RoomList = new ArrayList<Room>();
			RoomList = RoomDB.getRoomList();
			for(Room aroom : RoomList)
			{
				for(String roomApproved : ListOfRoomsApproved)
				{
					if(aroom.getRoomNumber().equals(roomApproved))	
					{
						aroom.setStatus("Booked");
						aroom.setBookingDate(bookingDate);
						aroom.setBookingTime(startingTime,duration);
					}
				}
			}
			System.out.println("Writing to RoomDB with updated room information...");
			RoomDB.updateDB(RoomList);
			System.out.println("Records Updated.");
			File afile = new File("RequestRoomDB");
			if(afile.exists())
				afile.delete();
		}catch(FileNotFoundException fnf)
		{
			System.out.println("Caught file not found exception while writing to DB");
			System.out.println("This means that there are no requests or the RequetRoomDB file is misplaced.");
		}catch(IOException io)
		{
			System.out.println("Caught file IOException while writing to DB");
		}
	}
}