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
		String bookAnotherCab = "y";
		
		while(bookAnotherCab.equalsIgnoreCase("y"))
		{
			
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