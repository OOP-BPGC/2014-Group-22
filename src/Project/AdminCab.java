package Project;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminCab {
	
	/**
	 * Form which takes in cab details for adding the cab to cab fleet 
	 */
	public static void generateForm()
	{
		Scanner sc = new Scanner(System.in);
		CabDB.readFromDB("CabFleet");
		
		System.out.println("Welcome to the administrator portal for cab booking system!");
		System.out.println("Press\n1 - Add a cab to the inventory\n2 - Remove a cab from the inventory");
		String op = sc.nextLine();
		
		String licensePlateNumber = "";
		boolean invalidLicensePlateNo = true;
		while(invalidLicensePlateNo)
		{
			System.out.println("Enter cab license plate number: ");
			licensePlateNumber = sc.nextLine();
			Pattern pat = Pattern.compile("^([A-Za-z][A-Za-z])([0-9][0-9])([A-Za-z][A-Za-z])[0-9][0-9][0-9][0-9]$");
			Matcher match = pat.matcher(licensePlateNumber);
			if(match.find(0))
			{
				invalidLicensePlateNo = false;
			}
			else
			{
				System.out.println("Failed to set license plate number. Invalid format!");
			}
		}
		
		switch(op)
		{
			case "1":
				Cab newCab = new Cab();
				newCab.setLicensePlate(licensePlateNumber);
				System.out.println("Enter cab capacity: ");
				int capacity = sc.nextInt(); // TODO: Implement type checking here
				newCab.setCapacity(capacity);
						
				sc.nextLine();
				System.out.println("Enter driver name: ");
				String driverName = sc.nextLine();
				newCab.setDriver(driverName);
				
				System.out.println("Driver phone number: ");
				String driverPhone = null;
				boolean invalidDriverPhone = true;
				while(invalidDriverPhone)
				{
					driverPhone = sc.nextLine();
					Pattern pat = Pattern.compile("^\\d{10}$");
					Matcher match = pat.matcher(driverPhone);
					if(match.find(0))
					{
						invalidDriverPhone = false;
					}
					else
					{
						System.out.println("Failed to set Phone Number. Enter a valid 10 digit phone number.");
					}
				}
				newCab.setDriverPhone(driverPhone);
				addCabToFleet(newCab);
				break;

			case "2":
				removeCabFromFleet(licensePlateNumber);
				break;
				
			default:
				System.out.println("Please enter a valid choce (1 or 2)!");
		}
	}
	
	/**
	 * This method takes a cab and writes it to CabFleet.db
	 * @param cab
	 */	
	public static void addCabToFleet(Cab cab)
	{
		// Reading already done in generateForm
		CabDB.CabListFleet.add(cab);
		CabDB.writeToDB("CabFleet");
	}
	
	/**
	 * Takes a license plate number and removes the cab corresponding to that number
	 * from the database
	 * @param licensePlateNumber
	 */
	public static void removeCabFromFleet(String licensePlateNumber)
	{
		for(int i = 0; i < CabDB.CabListFleet.size(); i++)
		{
			if(CabDB.CabListFleet.get(i).getLicensePlate().equalsIgnoreCase(licensePlateNumber))
			{
				CabDB.CabListFleet.remove(i);
				System.out.println("Cab with license plate number " + licensePlateNumber + " successfully removed!");
				return;
			}
		}
		System.out.println("No cab found in the inventory with the given license plate number");
	}
}
