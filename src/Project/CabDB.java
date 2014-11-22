package Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
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
		
		PrintWriter out = null;
		try {
			
			out = new PrintWriter(bookingType + ".db");
			
			switch(bookingType)
			{
				case "CabFleet":
					for(int i = 0; i < CabListFleet.size(); i++)
					{
						Cab cab = CabListFleet.get(i);
						out.println(cab.getBooked());
						out.println(cab.getCapacity());
						out.println(cab.getDriver());
						out.println(cab.getDriverPhone());
						out.println(cab.getLicensePlate());
					}
					break;
				case "Destination":
					for(int i = 0; i < CabListDest.size(); i++)
					{
						CabBookDestinationBased cabDest = CabListDest.get(i);
						Cab cab = cabDest.getCab();						
						out.println(cab.getBooked());
						out.println(cab.getCapacity());
						out.println(cab.getDriver());
						out.println(cab.getDriverPhone());
						out.println(cab.getLicensePlate());
						out.println(cabDest.getInitialDestination());
						out.println(cabDest.getFinalDestination());
						out.println(cabDest.getInitialDate());
						out.println(cabDest.getFinalDate());
						out.println(cabDest.getInitialTime());
						out.println(cabDest.getFinalTime());
						out.println(cabDest.getBookingType());
						out.println(cabDest.getBookedBy());
						out.println(cabDest.getUID());
					}
					break;
				case "Distance":
					for(int i = 0; i < CabListDist.size(); i++)
					{
						CabBookDistanceBased cabDist = CabListDist.get(i);
						Cab cab = cabDist.getCab();						
						out.println(cab.getBooked());
						out.println(cab.getCapacity());
						out.println(cab.getDriver());
						out.println(cab.getDriverPhone());
						out.println(cab.getLicensePlate());
						out.println(cabDist.getDistance());
						out.println(cabDist.getInitialDate());
						out.println(cabDist.getFinalDate());
						out.println(cabDist.getInitialTime());
						out.println(cabDist.getFinalTime());
						out.println(cabDist.getBookingType());
						out.println(cabDist.getBookedBy());
						out.println(cabDist.getUID());
					}
					break;
				case "Time": 
					for(int i = 0; i < CabListTime.size(); i++)
					{
						CabBookTimeBased cabTime = CabListTime.get(i);
						Cab cab = cabTime.getCab();						
						out.println(cab.getBooked());
						out.println(cab.getCapacity());
						out.println(cab.getDriver());
						out.println(cab.getDriverPhone());
						out.println(cab.getLicensePlate());
						out.println(cabTime.getInitialDate());
						out.println(cabTime.getFinalDate());
						out.println(cabTime.getInitialTime());
						out.println(cabTime.getFinalTime());
						out.println(cabTime.getBookingType());
						out.println(cabTime.getBookedBy());
						out.println(cabTime.getUID());
					}
					break;
				default:
					; // Throw some exception if you want
			}
			out.flush();
		}catch(Exception e) {
			
			// System.out.println("Caught IOException while writing to DB.");
			
		}
		finally {
			out.close();
		}
	}
	
	/**
	 * Given the bookingType, populates the respective ArrayList from corresponding database
	 * @param bookingType
	 */
	public static void readFromDB(String bookingType)
	{
		try{
			
			File inFile = new File(bookingType + ".db");
			BufferedReader br = new BufferedReader(new FileReader(bookingType + ".db"));     
			if (br.readLine() == null)
			{
			    //System.out.println("File empty!");
			    br.close();
			    return;
			}
			Scanner in = new Scanner(inFile);
			
			switch(bookingType)
			{
				case "CabFleet":
					CabListFleet.clear(); // Empty the ArrayList before reading
					while(in.hasNextLine())
					{
						Cab cab = new Cab();
						int booked = Integer.parseInt(in.nextLine());
						cab.setBooked(booked);
						int capacity = Integer.parseInt(in.nextLine());
						cab.setCapacity(capacity);
						String driver = in.nextLine();
						cab.setDriver(driver);
						String driverPhone = in.nextLine();
						cab.setDriverPhone(driverPhone);
						String licensePlateNo = in.nextLine();
						cab.setLicensePlate(licensePlateNo);
						CabListFleet.add(cab);
					}
					break;
					
				case "Destination":
					CabListDest.clear();
					while(in.hasNextLine())
					{
						Cab cab = new Cab();
						int booked = Integer.parseInt(in.nextLine());
						cab.setBooked(booked);
						int capacity = Integer.parseInt(in.nextLine());
						cab.setCapacity(capacity);
						String driver = in.nextLine();
						cab.setDriver(driver);
						String driverPhone = in.nextLine();
						cab.setDriverPhone(driverPhone);
						String licensePlateNo = in.nextLine();
						cab.setLicensePlate(licensePlateNo);
						CabBookDestinationBased cabDest = new CabBookDestinationBased();
						cabDest.setCab(cab);
						String initialDest = in.nextLine();
						cabDest.setInitialDestination(initialDest);
						String finalDest = in.nextLine();
						cabDest.setFinalDestination(finalDest);
						String initialDt = in.nextLine();
						cabDest.setInitialDate(initialDt);
						String finalDt = in.nextLine();
						cabDest.setFinalDate(finalDt);
						String initialTime = in.nextLine();
						cabDest.setInitialTime(initialTime);
						String finalTime = in.nextLine();
						cabDest.setFinalTime(finalTime);
						String bookingT = in.nextLine();
						cabDest.setBookingType(bookingT);
						String bookedBy = in.nextLine();
						cabDest.setBookedBy(bookedBy);
						String uid = in.nextLine();
						cabDest.setUID(uid);
						CabListDest.add(cabDest);
					}
					break;
				
				case "Distance":
					CabListDist.clear();
					int counter = 0;
					while(in.hasNextLine())
					{
						counter++;
						// System.out.println("counter = " + counter);
						Cab cab = new Cab();
						int booked = Integer.parseInt(in.nextLine());
						cab.setBooked(booked);
						int capacity = Integer.parseInt(in.nextLine());
						cab.setCapacity(capacity);
						String driver = in.nextLine();
						cab.setDriver(driver);
						String driverPhone = in.nextLine();
						cab.setDriverPhone(driverPhone);
						String licensePlateNo = in.nextLine();
						cab.setLicensePlate(licensePlateNo);
						CabBookDistanceBased cabDist = new CabBookDistanceBased();
						cabDist.setCab(cab);
						int dist = Integer.parseInt(in.nextLine());
						cabDist.setDistance(dist);
						String initialDt = in.nextLine();
						cabDist.setInitialDate(initialDt);
						String finalDt = in.nextLine();
						cabDist.setFinalDate(finalDt);
						String initialTime = in.nextLine();
						cabDist.setInitialTime(initialTime);
						String finalTime = in.nextLine();
						cabDist.setFinalTime(finalTime);
						String bookingT = in.nextLine();
						cabDist.setBookingType(bookingT);
						String bookedBy = in.nextLine();
						cabDist.setBookedBy(bookedBy);
						String uid = in.nextLine();
						cabDist.setUID(uid);
						CabListDist.add(cabDist);
					}
					break;
					
				case "Time":
					CabListTime.clear();
					while(in.hasNextLine())
					{
						Cab cab = new Cab();
						int booked = in.nextInt();
						cab.setBooked(booked);
						int capacity = in.nextInt();
						cab.setCapacity(capacity);
						String driver = in.nextLine();
						cab.setDriver(driver);
						String driverPhone = in.nextLine();
						cab.setDriverPhone(driverPhone);
						String licensePlateNo = in.nextLine();
						cab.setLicensePlate(licensePlateNo);
						CabBookTimeBased cabTime = new CabBookTimeBased();
						cabTime.setCab(cab);
						String initialDt = in.nextLine();
						cabTime.setInitialDate(initialDt);
						String finalDt = in.nextLine();
						cabTime.setFinalDate(finalDt);
						String initialTime = in.nextLine();
						cabTime.setInitialTime(initialTime);
						String finalTime = in.nextLine();
						cabTime.setFinalTime(finalTime);
						String bookingT = in.nextLine();
						cabTime.setBookingType(bookingT);
						String bookedBy = in.nextLine();
						cabTime.setBookedBy(bookedBy);
						String uid = in.nextLine();
						cabTime.setUID(uid);
						CabListTime.add(cabTime);
					}
					break;
				default:
					; // Throw some exception if you want
			}
			
		} catch(IOException e){
			// System.out.println("Caught IOException while reading from DB.");
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
		
		//System.out.println("CabDB.freeCabs > CabListDest.size() = " + CabListDest.size()); // Debug statement
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
		//System.out.println("CabDB.freeCabs > CabListDist.size() = " + CabListDist.size()); // Debug statement
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
		
		/*if(CabListFleet.size() == 0)
		{
			System.out.println("No cab present in cab database. Maybe you need to add some to your inventory?");
			return null;
		}*/
		
		// This loop searches for a cab with 0 current bookings from CabFleet.db
		// System.out.println("CabDB.findCab > CabFleet.size() = " + CabListFleet.size()); // Debug statement
		for(int i = 0; i < CabListFleet.size(); i++)
		{
			System.out.println("cab size = " + CabListFleet.get(i).getCapacity());
			if(CabListFleet.get(i).getBooked() == 0 && CabListFleet.get(i).getCapacity() >= reqdCapacity)
			{
				CabListFleet.get(i).incrementBooked();
				// System.out.println("HURRAH! capacity = " + CabListFleet.get(i).getCapacity());
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
					Book.compareDate(initialDate, CabListDest.get(i).getInitialDate()) == 1 && Book.compareDate(finalDate, CabListDest.get(i).getFinalDate()) == 1 ||
					CabListDest.get(i).getInitialDate().equals(initialDate)
					)
			{
				availableCabs.put(new String(CabListDist.get(i).cab.getLicensePlate()), new Integer(-1));
			}
		}
		for(int i = 0; i < CabListDist.size(); i++) // Search Distance.db
		{
			if(CabListDist.get(i).cab.getCapacity() < reqdCapacity ||
					Book.compareDate(CabListDist.get(i).getInitialDate(), initialDate) == 1 && Book.compareDate(CabListDist.get(i).getFinalDate(), finalDate) == 1 ||
					Book.compareDate(initialDate, CabListDist.get(i).getInitialDate()) == 1 && Book.compareDate(finalDate, CabListDist.get(i).getFinalDate()) == 1 ||
					CabListDest.get(i).getInitialDate().equals(initialDate)
					)
			{
				availableCabs.put(new String(CabListDist.get(i).cab.getLicensePlate()), new Integer(-1));
			}
		}
		for(int i = 0; i < CabListTime.size(); i++) // Search Time.db
		{
			if(CabListTime.get(i).cab.getCapacity() < reqdCapacity ||
					Book.compareDate(CabListTime.get(i).getInitialDate(), initialDate) == 1 && Book.compareDate(CabListTime.get(i).getFinalDate(), finalDate) == 1 ||
					Book.compareDate(initialDate, CabListTime.get(i).getInitialDate()) == 1 && Book.compareDate(finalDate, CabListTime.get(i).getFinalDate()) == 1 ||
					CabListDest.get(i).getInitialDate().equals(initialDate)
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
				System.out.println("Cancellation successful!");
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
				System.out.println("Cancellation successful!");
				return;
			}
		}
		for(int i = 0; i < CabDB.CabListTime.size(); i++)
		{
			if(CabDB.CabListTime.get(i).getUID().equals(UID))
			{
				CabDB.CabListTime.remove(i);
				CabDB.writeToDB("Time");
				System.out.println("Cancellation successful! Have a nice day :)");
				return;
			}
		}
		System.out.println("No booking found for the given UID!");
	}
}