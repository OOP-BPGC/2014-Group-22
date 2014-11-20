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
			
			boolean invalidDate = true;
			while(invalidDate)
			{
				System.out.println("Enter the departure Date (in format dd/mm/yyyy) : ");
				this.initialDate = sc.nextLine();
				if(Book.isValidDateFormat(this.initialDate))
				{
					if(Book.compareDate(this.initialDate, null) == -1)
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
			
			invalidDate = true;
			while(invalidDate) // Checks whether input Date is valid
			{
				System.out.println("Enter the arrival date (in format dd/mm/yyyy) : ");
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
			
			// CHECK TIME VALIDITY
			
			boolean invalidTime = true;;
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
			
			invalidTime = true;
			while(invalidTime) // Checks whether input time is valid
			{
				System.out.println("Enter the arrival time (in format hh:mm) : ");
				this.finalTime = sc.nextLine();
				if(Book.isValidTimeFormat(this.finalTime))
				{
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
					System.out.println("Invalid time format!");
				}
			}
			
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