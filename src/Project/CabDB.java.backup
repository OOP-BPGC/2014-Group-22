package Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Serves as a database management class for cab booking system. Maintains four databases -
 * 1. CabFleet.db - Inventory of all cabs. Stores 'Cab' objects.
 * 2. CabDestinationBased.db - Stores destination based bookings (CabBookDestinationBased objects)
 * 3. CabDistanceBased.db - Stores distance based bookings (CabBookDistanceBased objects)
 * 4. CabBookTimeBased.db - Stores time based bookings (CabBookTimeBased objects)
 * 
 * @author nisarg
 */

public class CabDB {
	
	// Keeping below three ArrayLists package-wide
	static ArrayList<CabBookDestinationBased> CabListDest = new ArrayList<CabBookDestinationBased>();
	static ArrayList<CabBookDistanceBased> CabListDist = new ArrayList<CabBookDistanceBased>();
	static ArrayList<CabBookTimeBased> CabListTime = new ArrayList<CabBookTimeBased>();
	static ArrayList<Cab> CabListFleet  = new ArrayList<Cab>();
	
	/**
	 * Given the bookingType, writes the respective ArrayList to corresponding database
	 * @param bookingType
	 */
	public static void writeToDB(String bookingType)
	{
		try {
			FileOutputStream fos = new FileOutputStream(bookingType + ".db");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			switch(bookingType)
			{
				case "CabFleet":
					oos.writeObject(CabListFleet);
					break;
				case "Destination":
					oos.writeObject(CabListDest);
					break;
				case "Distance":
					oos.writeObject(CabListDist);
					break;
				case "Time":
					oos.writeObject(CabListTime);
					break;
				default:
					; // Throw some exception if you want
			}
			
			oos.close();
			fos.close();
			
		}catch(IOException e) {
			
			// System.out.println("Caught IOException while writing to DB.");
			
		}
	}
	
	/**
	 * Given the bookingType, populates the respective ArrayList from corresponding database
	 * @param bookingType
	 */
	public static void readFromDB(String bookingType)
	{
		try{
			
			FileInputStream fis = new FileInputStream(bookingType + ".db");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			switch(bookingType)
			{
				case "CabFleet":
					CabListFleet.clear(); // Empty the ArrayList before reading
					CabListFleet = (ArrayList)ois.readObject();
					break;
					
				case "Destination":
					CabListDest.clear();
					CabListDest = (ArrayList)ois.readObject();
					break;
				
				case "Distance":
					CabListDist.clear();
					CabListDist = (ArrayList)ois.readObject();
					break;
					
				case "Time":
					CabListTime.clear();
					CabListTime = (ArrayList)ois.readObject();
					break;
				default:
					; // Throw some exception if you want
			}
			
			ois.close();
			fis.close();
			
		} catch(IOException e){
			// System.out.println("Caught IOException while reading from DB.");
			// e.printStackTrace();
		} catch(ClassNotFoundException e){
			// System.out.println("Caught ClassNotFoundException while reading from DB.");
			// e.printStackTrace();
		} 
	}
	
	/**
	 * Populate all ArrayLists after reading from all the four databases
	 */
	public static void readAllFromDB()
	{
		readFromDB("CabFleet");
		readFromDB("Destination");
		readFromDB("Distance");
		readFromDB("Time");
	}
	
	public static void writeAllToDB()
	{
		writeToDB("CabFleet");
		writeToDB("Destination");
		writeToDB("Distance");
		writeToDB("Time");
	}
	
	/**
	 * Free expired bookings from the three databases. Called before every booking.
	 */
	public static void freeCabs()
	{
		readAllFromDB();
		//System.out.println("Cabs in cab fleet = " + CabDB.CabListFleet.size());
		
		System.out.println("CabDB.freeCabs > CabListDest.size() = " + CabListDest.size()); // Debug statement
		for(int i = 0; i < CabListDest.size(); i++)
		{
			// Checks whether final date is less than current date
			// Not considered time
			//System.out.println(CabListDest.get(i).getFinalDate()); // Debug statement
			if(Book.compareDate(CabListDest.get(i).getFinalDate(), null) == -1) // Booking expired
			{
				Cab cab = CabListDest.remove(i).cab;
				for(int j = 0; j < CabListFleet.size(); j++)
				{
					if(CabListFleet.get(j).getLicensePlate() == cab.getLicensePlate())
					{
						CabListFleet.get(j).decrementBooked();
					}
				}
			}
		}
		
		// Same as above for distance and time based
		System.out.println("CabDB.freeCabs > CabListDist.size() = " + CabListDist.size()); // Debug statement
		for(int i = 0; i < CabListDist.size(); i++)
		{
			// Checks whether final date is less than current date
			// Not considered time
			//System.out.println("lolol");
			//System.out.println("Date = " + CabListDist.get(i).getFinalDate());
			if(Book.compareDate(CabListDist.get(i).getFinalDate(), null) == -1) // Booking expired
			{
				Cab cab = CabListDist.remove(i).cab;
				for(int j = 0; j < CabListFleet.size(); j++)
				{
					if(CabListFleet.get(j).getLicensePlate() == cab.getLicensePlate())
					{
						CabListFleet.get(j).decrementBooked();
					}
				}
			}
		}
		
		//System.out.println("CabDB.freeCabs > CabListTime.size() = " + CabListTime.size()); // Debug statement
		for(int i = 0; i < CabListTime.size(); i++)
		{
			// Checks whether final date is less than current date
			// Not considered time
			if(Book.compareDate(CabListTime.get(i).getFinalDate(), null) == -1) // Booking expired
			{
				Cab cab = CabListTime.remove(i).cab;
				for(int j = 0; j < CabListFleet.size(); j++)
				{
					if(CabListFleet.get(j).getLicensePlate() == cab.getLicensePlate())
					{
						CabListFleet.get(j).decrementBooked();
					}
				}
			}
		}

		// Write to DBs
		writeAllToDB();
	}
	
	// TODO: Verify the logic once
	/**
	 * Queries all databases and finds an un-booked cab
	 * @param bookingType
	 * @param reqdCapacity
	 * @return Cab if a cab was found, else null
	 */
	public static Cab findCab(String bookingType, int reqdCapacity, String initialDate, String finalDate)
	{
		
		// See whether any un-booked cab is there in DB
		readAllFromDB();
		
		// This loop searches for a cab with 0 current bookings from CabFleet.db
		System.out.println("CabDB.findCab > CabFleet.size() = " + CabListFleet.size()); // Debug statement
		for(int i = 0; i < CabListFleet.size(); i++)
		{
			System.out.println("cab size = " + CabListFleet.get(i).getCapacity());
			if(CabListFleet.get(i).getBooked() == 0 && CabListFleet.get(i).getCapacity() >= reqdCapacity)
			{
				CabListFleet.get(i).incrementBooked();
				System.out.println("HURRAH! capacity = " + CabListFleet.get(i).getCapacity());
				return CabListFleet.get(i);
			}
		}
		
		// If no un-booked cab present, check other three DBs
		/* Use hash map to check whether a particular cab (identified by license plate number)
		 * is available or not. If it is unavailable in any one of the databases,
		 * its value in the hash map is set to -1.
		 * At last, the keys in the hash map which have values 1 can be used for booking  
		 */
		
		HashMap availableCabs = new HashMap();
		
		for(int i = 0; i < CabListFleet.size(); i++) // Initialize hash map
		{
			if(CabListFleet.get(i).getCapacity() >= reqdCapacity)
				availableCabs.put(new String(CabListFleet.get(i).getLicensePlate()), new Integer(1)); // '1' denotes that the cab is available
			else
				availableCabs.put(new String(CabListFleet.get(i).getLicensePlate()), new Integer(-1)); // '-1' denotes that the cab is unavailable
		}
		
		for(int i = 0; i < CabListDest.size(); i++) // Search Destination.db
		{
			if(CabListDest.get(i).cab.getCapacity() < reqdCapacity ||
					Book.compareDate(CabListDest.get(i).getInitialDate(), initialDate) == 1 && Book.compareDate(CabListDest.get(i).getFinalDate(), finalDate) == 1 ||
					Book.compareDate(initialDate, CabListDest.get(i).getInitialDate()) == 1 && Book.compareDate(finalDate, CabListDest.get(i).getFinalDate()) == 1
					)
			{
				availableCabs.put(new String(CabListDist.get(i).cab.getLicensePlate()), new Integer(-1));
			}
		}
		for(int i = 0; i < CabListDist.size(); i++) // Search Distance.db
		{
			if(CabListDist.get(i).cab.getCapacity() < reqdCapacity ||
					Book.compareDate(CabListDist.get(i).getInitialDate(), initialDate) == 1 && Book.compareDate(CabListDist.get(i).getFinalDate(), finalDate) == 1 ||
					Book.compareDate(initialDate, CabListDist.get(i).getInitialDate()) == 1 && Book.compareDate(finalDate, CabListDist.get(i).getFinalDate()) == 1
					)
			{
				availableCabs.put(new String(CabListDist.get(i).cab.getLicensePlate()), new Integer(-1));
			}
		}
		for(int i = 0; i < CabListTime.size(); i++) // Search Time.db
		{
			if(CabListTime.get(i).cab.getCapacity() < reqdCapacity ||
					Book.compareDate(CabListTime.get(i).getInitialDate(), initialDate) == 1 && Book.compareDate(CabListTime.get(i).getFinalDate(), finalDate) == 1 ||
					Book.compareDate(initialDate, CabListTime.get(i).getInitialDate()) == 1 && Book.compareDate(finalDate, CabListTime.get(i).getFinalDate()) == 1
					)
			{
				availableCabs.put(new String(CabListTime.get(i).cab.getLicensePlate()), new Integer(-1));
			}
		}
		
		Set set = availableCabs.entrySet();
		Iterator j = set.iterator();
		String lpn = null;
		
		while(j.hasNext())
		{
			Map.Entry m = (Map.Entry)j.next();
			if((Integer)m.getValue() == 1)
			{
				lpn = (String)m.getKey();
				System.out.println("Cab found with license plate number " + lpn);
				break;
			}
		}
		
		/* increment the 'booked' variable of the cab  */
		if(lpn == null)
		{
			return null;
		}
		
		for(int i = 0; i < CabListFleet.size(); i++)
		{
			if(CabListFleet.get(i).getLicensePlate().equalsIgnoreCase(lpn))
			{
				CabListFleet.get(i).incrementBooked();
				return CabListFleet.get(i);
			}
		}
		
		return null; // Sanity check. Not needed really
	}
	
	/**
	 * Queries the databases for the given UID and removes the corresponding booking
	 * @param UID
	 */
	public static void cancelRequest(String UID)
	{
		System.out.println("Passed UID: " + UID);
		CabDB.readAllFromDB();
		
		for(int i = 0; i < CabDB.CabListDest.size(); i++)
		{
			if(CabDB.CabListDest.get(i).getUID().equals(UID))
			{
				CabDB.CabListDest.remove(i);
				CabDB.writeToDB("Destination");
				System.out.println("Cancellatin successful!");
				return;
			}
		}
		for(int i = 0; i < CabDB.CabListDist.size(); i++)
		{
			System.out.println("List uid = " + CabListDist.get(i).getUID());
			if(CabDB.CabListDist.get(i).getUID().equals(UID))
			{
				CabDB.CabListDist.remove(i);
				CabDB.writeToDB("Distance");
				System.out.println("Cancellatin successful!");
				return;
			}
		}
		for(int i = 0; i < CabDB.CabListTime.size(); i++)
		{
			if(CabDB.CabListTime.get(i).getUID().equals(UID))
			{
				CabDB.CabListTime.remove(i);
				CabDB.writeToDB("Time");
				System.out.println("Cancellatin successful!");
				return;
			}
		}
	}
}