package Project;

import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Abstract class which implements Book class and has
 * CabBookDestinationBased, CabBookDistanceBased and CabBookTimeBased
 * as its subclasses
 * @author nisarg
 */
public abstract class CabBook extends Book {

	// All common variables to the three sub classes
	protected String initialDate;
	protected String finalDate;
	protected String initialTime;
	protected String finalTime;
	protected Cab cab;
	protected String bookingType;
	protected String bookedBy; // Stores the id of user who booked it
	
	public CabBook()
	{
		super();
		cab = null;
		bookedBy = Login.getId();
	}
	
	public String getInitialTime() {
		return initialTime;
	}

	public void setInitialTime(String initialTime) {
		this.initialTime = initialTime;
	}

	public String getFinalTime() {
		return finalTime;
	}

	public void setFinalTime(String finalTime) {
		this.finalTime = finalTime;
	}

	public Cab getCab() {
		return this.cab;
	}

	public void setCab(Cab cab) {
		this.cab = cab;
	}

	public String getBookingType() {
		return this.bookingType;
	}

	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

	public String getBookedBy() {
		return this.bookedBy;
	}

	public void setBookedBy(String bookedBy) {
		this.bookedBy = bookedBy;
	}

	public void setInitialDate(String initialDate) {
		this.initialDate = initialDate;
	}

	public void setFinalDate(String finalDate) {
		this.finalDate = finalDate;
	}
	
	public String getInitialDate()
	{
		return this.initialDate;
	}
	
	public String getFinalDate()
	{
		return this.finalDate;
	}
	
	public abstract long calcFare(int reqdCapacity);
	
	/**
	 * This method is a part of segregation of common aspects of generateForm method
	 * of the three sub classes
	 */
	public void inputInitialDate()
	{
		Scanner sc = new Scanner(System.in);
		boolean invalidDate = true;
		while(invalidDate) // Checks whether input Date is valid
		{
			System.out.println("Enter the departure Date (in format dd/mm/yyyy) : ");
			this.initialDate = sc.nextLine();
			if(Book.isValidDateFormat(this.initialDate))
			{
				if(Book.compareDate(this.initialDate, null) == -1) // if initialDate before current date
				{
					System.out.println("The date given is in the past! We do not YET support travelling through time. Check again later.");
				}
				else
				{
					// This snippet checks if the initial date of booking is later than 1 week of current date
					// Not considering the exact time difference as it would bring in much complexity
					DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
					DateTime date1 = formatter.parseDateTime(this.initialDate);
					DateTime currDate = new DateTime();
					int daysInBetween = Days.daysBetween(currDate.toLocalDate(), date1.toLocalDate()).getDays(); // Days between current date and entered initial date
					if(daysInBetween > 7)
					{
						System.out.println("Sorry! Advanced booking later than 7 days is not allowed!");
						continue;
					}
					invalidDate = false;
				}
			}
			else
			{
				System.out.println("Invalid date format! Use 'dd/mm/yyyy'");
			}
		}
	}
	
	/**
	 * This method is a part of segregation of common aspects of generateForm method
	 * of the three sub classes
	 */
	public void inputFinalDate()
	{
		Scanner sc = new Scanner(System.in);
		boolean invalidDate = true;
		while(invalidDate) // Checks whether input Date is valid
		{
			System.out.println("Enter the return date (in format dd/mm/yyyy) : ");
			this.finalDate = sc.nextLine();
			if(Book.isValidDateFormat(this.finalDate))
			{
				if(Book.compareDate(this.finalDate, this.initialDate) == -1) // finalDate comes before initialDate 
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
		System.out.println("Date set = " + this.finalDate);
	}
	
	/**
	 * This method is a part of segregation of common aspects of generateForm method
	 * of the three sub classes
	 */
	public void inputInitialTime()
	{
		Scanner sc = new Scanner(System.in);
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
				System.out.println("Invalid time format! Use format hh:mm");
			}
		}
	}
	
	/**
	 * This method is a part of segregation of common aspects of generateForm method
	 * of the three sub classes
	 */
	public void inputFinalTime()
	{
		Scanner sc = new Scanner(System.in);
		boolean invalidTime = true;
		while(invalidTime) // Checks whether input time is valid
		{
			System.out.println("Enter the return time (in format hh:mm) : ");
			this.finalTime = sc.nextLine();
			if(Book.isValidTimeFormat(this.finalTime))
			{
				// System.out.println(initialDate + " " + finalDate + " " + Book.compareDate(finalDate, initialDate)); // Debug statement
				if(Book.compareDate(this.finalDate, this.initialDate) == 1 || 
						Book.compareTime(this.finalTime, this.initialTime))
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
				System.out.println("Invalid time format! Use format hh:mm");
			}
		}
	}
	
	// TODO: Decide whether we need this method
	public void displayStatus(String Uid)
	{
		CabDB.readAllFromDB();
		
		for(int i = 0; i < CabDB.CabListDest.size(); i++)
		{
			if(CabDB.CabListDest.get(i).getUID().equals(Uid))
			{
				CabDB.CabListDest.get(i);
				return;
			}
		}
		for(int i = 0; i < CabDB.CabListDist.size(); i++)
		{
			if(CabDB.CabListDist.get(i).getUID().equals(Uid))
			{
				CabDB.CabListDist.get(i);
				return;
			}
		}
		for(int i = 0; i < CabDB.CabListTime.size(); i++)
		{
			if(CabDB.CabListTime.get(i).getUID().equals(Uid))
			{
				CabDB.CabListTime.get(i);
				return;
			}
		}
	}
	
	public static void getFares()
	{
		System.out.println("###################################################################");
		System.out.println();
		System.out.println("If you want to book a cab based on common destinations, you might want to see this list.");
		System.out.println("The fares for fixed destinations are as follows : ");
		System.out.println();

		for(int i = 0; i < CabBookDestinationBased.destFare.length; i++)
		{
			System.out.println(CabBookDestinationBased.destinations[i] + "\nFare : Rs." + CabBookDestinationBased.destFare[i]);
			System.out.println();
		}
		System.out.println();
		System.out.println("###################################################################");
		System.out.println();
		System.out.println("If you want to book a cab based on distance, you might want to see this portion of the form.");
		System.out.println();
		System.out.println("For <=5 persons,");
		System.out.println("Price for 1 KM   ------------------------------  15Rs ");
		System.out.println();
		System.out.println("For >5 persons and <=10 persons,");
		System.out.println("Price for 1 KM   ------------------------------  20Rs ");
		System.out.println();
		System.out.println("For >10 persons and <=20 persons,");
		System.out.println("Price for 1 KM   ------------------------------  25Rs ");
		System.out.println();
		System.out.println("For >20 persons and <=40 persons,");
		System.out.println("Price for 1 KM   ------------------------------  35Rs ");
		System.out.println();
		System.out.println("###################################################################");
		System.out.println();
		System.out.println("If you want to book a cab based on time, you might want to see this portion of the form.");
		System.out.println();
		System.out.println("For <=5 persons,");
		System.out.println("Price for 1 hr   ------------------------------  120Rs ");
		System.out.println();
		System.out.println("For >5 persons and <=10 persons,");
		System.out.println("Price for 1 hr   ------------------------------  140Rs ");
		System.out.println();
		System.out.println("For >10 persons and <=20 persons,");
		System.out.println("Price for 1 hr   ------------------------------  160Rs ");
		System.out.println();
		System.out.println("For >20 persons and <=40 persons,");
		System.out.println("Price for 1 hr   ------------------------------  200Rs ");
		System.out.println();
		System.out.println("###################################################################");
	}
}

