package Project;

import java.util.Scanner;

/**
 * This class implements time based cab booking system.
 * User details are written into objects of this class and are serialized
 * and written to Time.db
 * @author nisarg
 */
public class CabBookTimeBased extends CabBook {
	
	public CabBookTimeBased()
	{
		super();
		this.bookingType = "Time";
		this.setUID(this.generateUID());
	}
	
	/**
	 * Standard method which takes user details for the booking
	 */
	public void generateForm()
	{
		Scanner sc = new Scanner(System.in);
		
		// CHECK DATE VALIDITY
		
		this.inputInitialDate();
		this.inputFinalDate();
		
		// CHECK TIME VALIDITY
		
		this.inputInitialTime();
		this.inputFinalTime();
		
		System.out.println("Enter the number of people: ");
		int requiredCapacity = sc.nextInt();
		
		CabDB.freeCabs();
		
		boolean cabBookStatus = this.bookCab(this.bookingType, requiredCapacity); // Get a free cab from cab fleet
		
		if(cabBookStatus) // Only register the booking if free cab available
		{
			System.out.println("Booking successful. Your request id is " + this.getUID());
	//		Not able to calculate timeDiff. If this is done, you can uncomment below comment.
	//		System.out.println("The fare for your ride is : " + CabBook.calcTimeFare(this.timeDiff, requiredCapacity) + "Rs");
		}
		else
		{
			System.out.println("No cab free for the given time!");
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
		cab = CabDB.findCab(bookingType, reqdCapacity, this.initialDate, this.finalDate);
		
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
	
	public void cancelRequest(String UID)
	{
		// Nothing here
	}
}
