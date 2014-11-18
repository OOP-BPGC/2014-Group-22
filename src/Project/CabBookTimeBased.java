package Project;

import java.io.Serializable;
import java.util.Scanner;

/**
 * This class implements time based cab booking system.
 * User details are written into objects of this class and are serialized
 * and written to Time.db
 * @author nisarg
 */
public class CabBookTimeBased extends CabBook implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public CabBookTimeBased()
	{
		super();
		this.bookingType = "Time";
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
			
			invalidDate = true;
			while(invalidDate) // Checks whether input Date is valid
			{
				System.out.println("Enter the departure Date (in format dd/mm/yyyy : ");
				this.finalDate = sc.nextLine();
				if(Book.isValidDateFormat(finalDate))
				{
					if(Book.compareDate(finalDate, initialDate) == -1)
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
			
			boolean invalidTime = true;;
			while(invalidTime) // Checks whether input time is valid
			{
				System.out.println("Enter the departure time (in format hh:mm:ss : ");
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
			
			invalidTime = true;
			while(invalidTime) // Checks whether input time is valid
			{
				System.out.println("Enter the arrival time (in format hh:mm:ss : ");
				this.finalTime = sc.nextLine();
				if(Book.isValidTimeFormat(finalTime))
				{
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
				System.out.println("Booking successful. Add another booking? (y/n): ");
				bookAnotherCab = (sc.nextLine() == "y")? true: false;
			}
			else
			{
				System.out.println("No cab free for the given time!");
			}
		}
		sc.close();
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
		cab = CabDB.findCab(bookingType, reqdCapacity);
		
		if(cab == null)
		{
			return false;
		}
		
		CabDB.CabListTime.add(this);
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