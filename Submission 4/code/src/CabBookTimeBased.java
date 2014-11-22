package src;

import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;

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
		
		CabDB.CabListTime.add(this);
		CabDB.writeToDB(bookingType);
		return true;
	}
	
	// TODO:
	public long calcFare(int requiredCapacity)
	{
		DateTime dt1 = DateTime.parse(this.initialDate + "T" + this.initialTime, DateTimeFormat.forPattern("dd/MM/yyyy'T'HH:mm"));
		DateTime dt2 = DateTime.parse(this.finalDate + "T" + this.finalTime, DateTimeFormat.forPattern("dd/MM/yyyy'T'HH:mm"));
		Duration duration = new Duration(dt2, dt1);
		long timeDiff = duration.getStandardHours();
		
		if(requiredCapacity <= 4)
		{
			return timeDiff*120;
		}
		else if(requiredCapacity > 4 && requiredCapacity<=10)
		{
			return timeDiff*140;
		}
		else if(requiredCapacity > 10 && requiredCapacity <= 20)
		{
			return timeDiff*160;
		}
		else if(requiredCapacity > 20 && requiredCapacity <= 40)
		{
			return timeDiff*200;
		}
		else
		{
			return timeDiff*(((requiredCapacity-1)/20)*20+180);
		}
	}
	
	public void cancelRequest(String UID)
	{
		// Nothing here
	}
}
