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
		Cab newCab = new Cab();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter cab license plate number: ");
		String licensePlateNumber = sc.nextLine(); // TODO: Maybe implement regex for this too?
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
	}
	
	/**
	 * This method takes a cab and writes it to CabFleet.db
	 * @param cab
	 */
	public static void addCabToFleet(Cab cab)
	{
		CabDB.readFromDB("CabFleet");
		CabDB.CabFleet.add(cab);
		CabDB.writeToDB("CabFleet");
	}
}
