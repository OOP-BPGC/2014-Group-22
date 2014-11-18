package Project;

import java.io.Serializable;
import java.util.Scanner;

/**
 * This class implements distance based cab booking system.
 * User details are written into objects of this class and are serialized
 * and written to Distance.db
 * @author nisarg
 */
public class CabBookDistanceBased extends CabBook implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int distance;
	
	public CabBookDistanceBased()
	{
		super();
		this.bookingType = "Distance";
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
			
			System.out.println("Enter the distance for which the cab is required: ");
			// TODO: Implement type checking
			this.distance = sc.nextInt();
			
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
			
			// Final date = Initial date + 1 (for simplicity)
			this.finalDate = this.initialDate.substring(0, 1) + String.valueOf(Integer.parseInt(this.initialDate.substring(1, 2))+1) + initialDate.substring(2);
			
			boolean invalidTime = true;
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
			
			// Final time = Initial time (for simplicity)
			this.initialTime = this.finalTime; 
			
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
		
		CabDB.CabListDist.add(this);
		CabDB.writeToDB(bookingType);
		return true;
	}
	
	// TODO: Make this more sophisticated
	public int calcFare()
	{
		return 50*this.distance;
	}
	
	// TODO: This method has already been implemented in CabDB. Only here for syntactic reasons
	public void cancelRequest(String UID)
	{
		
	}
}