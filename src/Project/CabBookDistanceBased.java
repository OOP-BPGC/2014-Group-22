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
			
			this.inputInitialDate();
			
			// Final date = Initial date + 1 (for simplicity)
			this.finalDate = this.initialDate.substring(0, 1) + String.valueOf(Integer.parseInt(this.initialDate.substring(1, 2))+1) + initialDate.substring(2);
			
			// CHECK TIME VALIDITY
			
			this.inputInitialTime();
			
			// Final time = Initial time (for simplicity)
			this.initialTime = this.finalTime; 
			
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
}