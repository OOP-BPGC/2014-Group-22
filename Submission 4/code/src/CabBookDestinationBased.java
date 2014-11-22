package src;

import java.util.Scanner;

/**
 * This class implements destination based cab booking system.
 * User details are written into objects of this class and are serialized
 * and written to Destination.db
 * @author nisarg
 */

public class CabBookDestinationBased extends CabBook {
	
	private String initialDestination = "BITS";
	private String finalDestination;

	public String getInitialDestination() {
		return initialDestination;
	}

	public void setInitialDestination(String initialDestination) {
		this.initialDestination = initialDestination;
	}
	public String getFinalDestination() {
		return finalDestination;
	}

	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}

	public CabBookDestinationBased()
	{
		super();
		this.bookingType = "Destination";
		this.setUID(this.generateUID());
	}
	
	/**
	 * Standard method which takes user details for the booking
	 */
	public void generateForm()
	{
		Scanner sc = new Scanner(System.in);

		System.out.println("Currently available destinations are - ");
		for(String dest: destinations)
		{
			System.out.print(dest);
		}

		// CHECK DESTINATION VALIDITY
		
		boolean invalidDestination = true;
		while(invalidDestination) // Check whether the entered destination is present in
									// the destination array.
		{
			System.out.println("\nEnter the final destination: ");
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
				System.out.println("Destination not present in database! Please enter a valid destination");
			}
		}
		
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
			for(int i=0;i<destinations.length;i++)
			{
				if ( destinations[i].equals(this.finalDestination))
				{
					System.out.println("The fare for your ride is : " + destFare[i] + "Rs");
					break;
				}
			}
		}
		else
		{
			System.out.println("No cab free for the given time!");
		}
		return;
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
		this.cab = CabDB.findCab(bookingType, reqdCapacity, this.initialDate, this.finalDate);
		
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
	
	public void cancelRequest(String UID)
	{
		// Nothing here
	}
}
