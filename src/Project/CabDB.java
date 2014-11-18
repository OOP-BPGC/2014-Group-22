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
	static ArrayList<Cab> cabFleet  = new ArrayList<Cab>();
	
	/**
	 * Given the bookingType, writes the respective ArrayList to corresponding database
	 * @param bookingType
	 */
	public static void writeToDB(String bookingType)
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		File fileCheck = new File(bookingType + ".db");

		//this check ensures system doesn't crash with FNF exception when writing to DB for the first Time	
		if(fileCheck.exists())
			readFromDB(bookingType);

		try{
			
			fos = new FileOutputStream(bookingType + ".db");
			oos = new ObjectOutputStream(fos);
			
			switch(bookingType)
			{
				case "CabFleet":
					oos.writeObject(cabFleet);
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
			
		}catch(IOException e){
			
			System.out.println("Caught IOException while writing to DB.");
			
		}finally{
			
			try{
				if(fos!=null)
					fos.close();
			}catch(IOException e){
				System.out.println("IOEx1");
			}
			
		}
	}
	
	/**
	 * Given the bookingType, populates the respective ArrayList from corresponding database
	 * @param bookingType
	 */
	public static void readFromDB(String bookingType)
	{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			
			fis = new FileInputStream(bookingType + ".db");
			ois = new ObjectInputStream(fis);
			
			switch(bookingType)
			{
				case "CabFleet":
					cabFleet = null; // Empty the ArrayList before reading
					cabFleet = (ArrayList<Cab>)ois.readObject();
					break;
					
				case "Destination":
					CabListDest = null;
					CabListDest = (ArrayList<CabBookDestinationBased>)ois.readObject();
					break;
				
				case "Distance":
					CabListDist = null;
					CabListDist = (ArrayList<CabBookDistanceBased>)ois.readObject();
					break;
					
				case "Time":
					CabListTime = null;
					CabListTime = (ArrayList<CabBookTimeBased>)ois.readObject();
					break;
				default:
					; // Throw some exception if you want
			}
			
		} catch(FileNotFoundException e){
			System.out.println("Caught FileNotFoundException while reading from DB.");
		} catch(ClassNotFoundException e){
			System.out.println("Caught ClassNotFoundException while reading from DB.");
		} catch(IOException e){
			System.out.println("Caught IOException while reading from DB.");
		} finally{
			
			try{
				if(fis!=null)
					fis.close();
			}catch(IOException e)
			{
				System.out.println("Caught IOEx while closing fileinputstream for reading from DB");
			}
		}		
	}
	
	/**
	 * Populate all ArrayLists afte reading from all the four databases
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
	
/*	// TODO: Do something with this
	public static void updateDB(ArrayList<Cab> listOfCabsToWrite)
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		try{
			
			fos = new FileOutputStream(getFileName());
			oos = new ObjectOutputStream(fos);
			oos.writeObject(listOfCabsToWrite);
			
		}catch(IOException e){
			System.out.println("Caught IOException while writing to DB.");
		}finally{
			try{
				if(fos!=null)
					fos.close();
			}catch(IOException e){
				System.out.println("IOEx1");
			}
		}
	}*/
	
	/**
	 * Free expired bookings from the three databases. Called before every booking.
	 */
	public static void freeCabs()
	{
		readAllFromDB();
		
		for(int i = 0; i < CabListDest.size(); i++)
		{
			// Checks whether final date is less than current date
			// Not considered time
			if(Book.compareDate(CabListDest.get(i).getFinalDate(), null) == -1) // Booking expired
			{
				Cab cab = CabListDest.remove(i).cab;
				for(int j = 0; j < cabFleet.size(); j++)
				{
					if(cabFleet.get(j).getLicensePlate() == cab.getLicensePlate())
					{
						cabFleet.get(j).decrementBooked();
					}
				}
			}
		}
		
		// Same as above for distance and time based
		
		for(int i = 0; i < CabListDist.size(); i++)
		{
			// Checks whether final date is less than current date
			// Not considered time
			if(Book.compareDate(CabListDist.get(i).getFinalDate(), null) == -1) // Booking expired
			{
				Cab cab = CabListDist.remove(i).cab;
				for(int j = 0; j < cabFleet.size(); j++)
				{
					if(cabFleet.get(j).getLicensePlate() == cab.getLicensePlate())
					{
						cabFleet.get(j).decrementBooked();
					}
				}
			}
		}
		
		for(int i = 0; i < CabListTime.size(); i++)
		{
			// Checks whether final date is less than current date
			// Not considered time
			if(Book.compareDate(CabListTime.get(i).getFinalDate(), null) == -1) // Booking expired
			{
				Cab cab = CabListTime.remove(i).cab;
				for(int j = 0; j < cabFleet.size(); j++)
				{
					if(cabFleet.get(j).getLicensePlate() == cab.getLicensePlate())
					{
						cabFleet.get(j).decrementBooked();
					}
				}
			}
		}

		// Write to DBs
		writeAllToDB();
	}
	
	/**
	 * Queries all databases and finds an un-booked cab
	 * @param bookingType
	 * @param reqdCapacity
	 * @return Cab if a cab was found, else null
	 */
	public static Cab findCab(String bookingType, int reqdCapacity)
	{
		
		// See whether any un-booked cab is there in DB
		readAllFromDB();
		
		// This loop searches for a cab with 0 current bookings from CabFleet.db
		for(int i = 0; i < cabFleet.size(); i++)
		{
			if(cabFleet.get(i).getBooked() == 0 && cabFleet.get(i).getCapacity() >= reqdCapacity)
			{
				cabFleet.get(i).incrementBooked();
				return cabFleet.get(i);
			}
		}
		
		// If no un-booked cab present, check other three DBs
		/* Use hash map to check whether a particular cab (identified by license plate number)
		 * is available or not. If it is unavailable in any one of the databases,
		 * its value in the hash map is set to -1.
		 * At last, the keys in the hash map which have values 1 can be used for booking  
		 */
		
		HashMap availableCabs = new HashMap();
		
		for(int i = 0; i < cabFleet.size(); i++) // Initialize hash map
		{
			availableCabs.put(new String(cabFleet.get(i).getLicensePlate()), new Integer(1));
		}
		for(int i = 0; i < CabListDest.size(); i++) // Search Destination.db
		{
			if(!(Book.compareDate(CabListDest.get(i).getInitialDate(), null) == 1 &&
					CabListDest.get(i).cab.getCapacity() >= reqdCapacity))
			{
				availableCabs.put(new String(CabListDest.get(i).cab.getLicensePlate()), new Integer(-1));
			}
		}
		for(int i = 0; i < CabListDist.size(); i++) // Search Distance.db
		{
			if(!(Book.compareDate(CabListDist.get(i).getInitialDate(), null) == 1 &&
					CabListDist.get(i).cab.getCapacity() >= reqdCapacity))
			{
				availableCabs.put(new String(CabListDist.get(i).cab.getLicensePlate()), new Integer(-1));
			}
		}
		for(int i = 0; i < CabListTime.size(); i++) // Search Time.db
		{
			if(!(Book.compareDate(CabListTime.get(i).getInitialDate(), null) == 1 &&
					CabListTime.get(i).cab.getCapacity() >= reqdCapacity))
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
			System.out.println("No free cab found!");
			return null;
		}
		
		for(int i = 0; i < cabFleet.size(); i++)
		{
			if(cabFleet.get(i).getLicensePlate().equalsIgnoreCase(lpn))
			{
				cabFleet.get(i).incrementBooked();
				return cabFleet.get(i);
			}
		}
		
		return null; // Sanity check. Not needed really
	}
	
	/**
	 * Queries the databases for the given UID and removes the corresponding booking
	 * @param UID
	 */
	public void cancelRequest(String UID)
	{
		readAllFromDB();
		
		for(int i = 0; i < CabListDest.size(); i++)
		{
			if(CabListDest.get(i).getUID().equals(UID))
			{
				CabListDest.remove(i);
				return;
			}
		}
		for(int i = 0; i < CabListDist.size(); i++)
		{
			if(CabListDist.get(i).getUID().equals(UID))
			{
				CabListDist.remove(i);
				return;
			}
		}
		for(int i = 0; i < CabListTime.size(); i++)
		{
			if(CabListTime.get(i).getUID().equals(UID))
			{
				CabListTime.remove(i);
				return;
			}
		}
		
		writeAllToDB(); // Write changes to DB
	}
}