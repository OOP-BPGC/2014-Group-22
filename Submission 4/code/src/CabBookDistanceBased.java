package src;

import java.util.Scanner;

/**
 * This class implements distance based cab booking system.
 * User details are written into objects of this class and are serialized
 * and written to Distance.db
 * @author nisarg
 */
public class CabBookDistanceBased extends CabBook {
	
	private int distance;
	
	public CabBookDistanceBased()
	{
		super();
		this.bookingType = "Distance";
		this.setUID(this.generateUID());
	}
	
	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	/**
	 * Standard method which takes user details for the booking
	 */
	public void generateForm()
	{
		Scanner sc = new Scanner(System.in);
					
		System.out.println("Enter the distance (in kms) for which the cab is required: ");
		// TODO: Implement type checking
		this.distance = sc.nextInt();
		
		// System.out.println("Passed check");
		// CHECK DATE VALIDITY
		
		this.inputInitialDate();
		
		// Final date = Initial date + 1 (for simplicity)
		this.finalDate = this.initialDate.substring(0, 1) + String.valueOf(Integer.parseInt(this.initialDate.substring(1, 2))+1) + initialDate.substring(2);
		
		// CHECK TIME VALIDITY
		
		this.inputInitialTime();
		
		// Final time = Initial time (for simplicity)
		this.finalTime = this.initialTime; 
		
		System.out.println("Enter the number of people: ");
		int requiredCapacity = sc.nextInt();
		
		CabDB.freeCabs();
		
		boolean cabBookStatus = this.bookCab(this.bookingType, requiredCapacity); // Get a free cab from cab fleet
		
		if(cabBookStatus) // Only register the booking if free cab available
		{
			System.out.println("Booking successful. Your request id is " + this.getUID()); // generateUID called in the constructor
			System.out.println("The fare for your ride is : Rs." + this.calcFare(requiredCapacity));
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

		CabDB.CabListDist.add(this);
		CabDB.writeToDB(bookingType);
		return true;
	}
	
	// TODO: Make this more sophisticated
	public long calcFare(int requiredCapacity)
	{
		if(requiredCapacity <= 5)
		{
			return this.distance*15;
		}
		else if(requiredCapacity > 5 && requiredCapacity<=10)
		{
			return this.distance*20;
		}
		else if(requiredCapacity > 10 && requiredCapacity <= 20)
		{
			return this.distance*25;
		}
		else if(requiredCapacity > 20 && requiredCapacity <= 40)
		{
			return this.distance*35;
		}
		else
		{
			return this.distance*(((requiredCapacity-1)/20)*5+30);
		}
	}
	
	public void cancelRequest(String UID)
	{
		// Nothing here
	}
}
