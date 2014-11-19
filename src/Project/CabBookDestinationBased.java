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
	
	private static final long serialVersionUID = 1L; // Done to avoid warnings
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
		String bookAnotherCab = "y";
		
		while(bookAnotherCab.equals("y"))
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
				System.out.println("\nEnter the initial destination: ");
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
				System.out.println("Enter the final destination: ");
				this.finalDestination = sc.nextLine();
				
				if(finalDestination.equalsIgnoreCase(initialDestination))
				{
					System.out.println("Final destination cannot be the same as initial destination!");
					continue;
				}
				
				for(String dest: destinations) // Verify entered destination
				{
					if(dest.equalsIgnoreCase(finalDestination))
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
				if(Book.isValidDateFormat(initialDate))
				{
					if(Book.compareDate(initialDate, null) == -1)
					{
						System.out.println("Invalid date!");
					}
					else
					{
						invalidDate = false;
					}
				}
				else
				{
					System.out.println("Invalid date format!");
				}
			}
			
			// TODO: Check if finalDate >= initialDate. Copy to all other classes.
			invalidDate = true;
			while(invalidDate) // Checks whether input Date is valid
			{
				System.out.println("Enter the arrival Date (in format dd/mm/yyyy : ");
				this.finalDate = sc.nextLine();
				if(Book.isValidDateFormat(finalDate))
				{
					if(Book.compareDate(finalDate, initialDate) == -1) // finalDate < initialDate 
					{
						System.out.println("Invalid date!");
					}
					else
					{
						invalidDate = false;
					}
				}
				else
				{
					System.out.println("Invalid date format!");
				}
			}
			
			boolean invalidTime = true;
			while(invalidTime) // Checks whether input time is valid
			{
				System.out.println("Enter the departure time (in format hh:mm : ");
				this.initialTime = sc.nextLine();
				if(Book.isValidTimeFormat(initialTime))
				{
					if(Book.compareDate(initialDate, null) == 1 || 
							(Book.compareDate(initialDate, null) == 0 && Book.compareTime(initialTime, null)))
					{
						invalidTime = false;
					}
					else
					{
						System.out.println("Invalid time!");
					}
				}
				else
				{
					System.out.println("Invalid time format!");
				}
			}
			
			// TODO: Check if finalTime >= initialTime if finalDate == initialDate. Copy to all other classes.
			invalidTime = true;
			while(invalidTime) // Checks whether input time is valid
			{
				System.out.println("Enter the arrival time (in format hh:mm : ");
				this.finalTime = sc.nextLine();
				if(Book.isValidTimeFormat(finalTime))
				{
					// System.out.println(initialDate + " " + finalDate + " " + Book.compareDate(finalDate, initialDate)); // Debug statement
					if(Book.compareDate(finalDate, initialDate) == 1 || 
							(Book.compareDate(finalDate, initialDate) == 0 && Book.compareTime(finalTime, initialTime)))
					{
						invalidTime = false;
					}
					else
					{
						System.out.println("Invalid time!");
					}
				}
				else
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
				generateUID();
				System.out.println("Booking successful. Your request id is " + getUID());
			}
			else
			{
				System.out.println("No cab free for the given time!");
			}
			System.out.println("Book again? (y/n): ");
			bookAnotherCab = "z";
			while(!bookAnotherCab.equals("y") && !bookAnotherCab.equals("n"))
			{
				bookAnotherCab = sc.nextLine();
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