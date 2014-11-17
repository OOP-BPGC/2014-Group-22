package Project;

import java.io.Serializable;
import java.util.Scanner;

/**
 * This class implements destination based cab booking system.
 * User details are written into objects of this class and are serialized
 * and written to Destination.db
 * @author nisarg
 */

public class CabBookDestinationBased extends CabBook implements Serializable {
	
	private String initialDestination;
	private String finalDestination;
	private String[] destinations = {"BITS", "Verna", "Zuari", "Panjim", 
									"Vasco"};
	
	public CabBookDestinationBased()
	{
		super();
		this.bookingType = "Destination";
		this.UID = generateUID();
	}
	
	/**
	 * Standard method which takes user details for the booking
	 */
	public void generateForm()
	{
		Scanner sc = new Scanner(System.in);
		boolean bookAnotherCab = true;
		
		while(bookAnotherCab)
		{
			System.out.println("Currently available destinations are - ");
			for(String dest: destinations)
			{
				System.out.print(dest + " ");
			}
			
			boolean invalidDestination = true;
			while(invalidDestination) // Check whether the entered destination is present in
										// the destination array.
			{
				System.out.println("Enter the departure destination: ");
				this.initialDestination = sc.nextLine();
				
				for(String dest: destinations) // Verify entered destination
				{
					if(dest.equals(initialDestination))
					{
						invalidDestination = false;
						break;
					}
				}
				if(invalidDestination)
				{
					System.out.println("Destination not present in database! Please enter a valid destination");
				}
			}
			
			invalidDestination = true;
			while(invalidDestination) // Check whether the entered destination is present in
				// the destination array.
			{
				System.out.println("Enter the departure destination: ");
				this.finalDestination = sc.nextLine();
				
				for(String dest: destinations) // Verify entered destination
				{
					if(dest.equals(finalDestination))
					{
						invalidDestination = false;
						break;
					}
				}
				if(invalidDestination)
				{
					System.out.println("Destination not present in database!");
				}
			}
			
			boolean invalidDate = true;
			while(invalidDate) // Checks whether input Date is valid
			{
				System.out.println("Enter the departure Date (in format dd/mm/yyyy : ");
				this.initialDate = sc.nextLine();
				// TODO: Method to validate date
				if(invalidDate)
				{
					System.out.println("Invalid Date format!");
				}
			}
			
			invalidDate = true;
			while(invalidDate) // Checks whether input Date is valid
			{
				System.out.println("Enter the arrival Date (in format dd/mm/yyyy : ");
				this.finalDate = sc.nextLine();
				// TODO: Method to validate date
				if(invalidDate)
				{
					System.out.println("Invalid Date format!");
				}
			}
			
			boolean invalidTime = true;
			while(invalidTime) // Checks whether input time is valid
			{
				System.out.println("Enter the departure time (in format hh:mm:ss : ");
				this.initialTime = sc.nextLine();
				// TODO: Method to validate time
				if(invalidTime)
				{
					System.out.println("Invalid time format!");
				}
			}
			
			invalidTime = true;
			while(invalidTime) // Checks whether input time is valid
			{
				System.out.println("Enter the arrival time (in format hh:mm:ss : ");
				this.finalTime = sc.nextLine();
				// TODO: Method to validate time
				if(invalidTime)
				{
					System.out.println("Invalid time format!");
				}
			}
			
			System.out.println("Enter the number of people: ");
			int requiredCapacity = sc.nextInt();
			
			CabDB.freeCabs();
			
			boolean cabBookStatus = bookCab(this.bookingType, requiredCapacity); // Get a free cab from cab fleet
			
			if(cabBookStatus) // Only register the booking if free cab available
			{
				System.out.println("Booking successful. Add another booking? (y/n): ");
				bookAnotherCab = (sc.nextLine() == "y")? true: false;
			}
			else
			{
				System.out.println("No cab free for the given time!");
			}
		}	
	}
	
	/**
	 * Method to book cab
	 * @param bookingtype
	 * @param reqdCapacity
	 * @return true if cab booked successfully, else false
	 */
	public boolean bookCab(String bookingtype, int reqdCapacity)
	{
		this.cab = new Cab();
		this.cab = CabDB.findCab(bookingType, reqdCapacity);
		
		if(this.cab == null)
		{
			return false;
		}
		
		CabDB.CabListDest.add(this);
		CabDB.writeToDB(bookingType);
		return true;
	}
	
	// TODO:
	public int calcFare()
	{
		return 1;
	}
	
	// TODO: This method has already been implemented in CabDB. Only here for syntactic reasons
	public void cancelRequest(String UID)
	{
		
	}
}