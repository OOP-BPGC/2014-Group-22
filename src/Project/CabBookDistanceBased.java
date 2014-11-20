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
		String bookAnotherCab = "y";
		
		while(bookAnotherCab.equals("y"))
		{
			
			System.out.println("Enter the distance for which the cab is required: ");
			// TODO: Implement type checking
			sc.nextLine();
			this.distance = sc.nextInt();
			
			// CHECK DATE VALIDITY
			
			boolean invalidDate = true;
			while(invalidDate) // Checks whether input Date is valid
			{
				System.out.println("Enter the departure Date (in format dd/mm/yyyy : ");
				this.initialDate = sc.nextLine();
				if(Book.isValidDateFormat(this.initialDate))
				{
					if(Book.compareDate(this.initialDate, null) == -1) // if initialDate before current date
					{
						System.out.println("The date given is in the past! We do not YET support travelling through time. Check again later.");
					}
					else
					{
						invalidDate = false;
					}
				}
				else
				{
					System.out.println("Invalid date format! Use 'dd/mm/yyyy'");
				}
			}
			
			// Final date = Initial date + 1 (for simplicity)
			this.finalDate = this.initialDate.substring(0, 1) + String.valueOf(Integer.parseInt(this.initialDate.substring(1, 2))+1) + initialDate.substring(2);
			
			boolean invalidTime = true;
			while(invalidTime) // Checks whether input time is valid
			{
				System.out.println("Enter the departure time (in format hh:mm) : ");
				this.initialTime = sc.nextLine();
				if(Book.isValidTimeFormat(this.initialTime))
				{
					if(Book.compareDate(this.initialDate, null) == 1 || 
							Book.compareTime(this.initialTime, null))
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