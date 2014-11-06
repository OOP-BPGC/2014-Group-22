import java.util.Scanner;

public class CabBookDistanceBased {

	static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) {
		int value = 0,press = 0;
		// Scanner in = new Scanner(System.in);
		while(value < 1){
			System.out.println("Enter the Distance ");
			value = in.nextInt();
		}	
	    int DistanceFare  =  DistanceFareCalculation(value);
		System.out.println("The Fare for the given distance is " + DistanceFare);
		while(press==0) {
			String showdown = GetUserDetails();
			System.out.println("Match the User Details " + showdown);
			System.out.println("If the Details are Correct Press 1 or press 0 to Re-enter");
			press = in.nextInt();
		}
		// SaveCabDB(); Have to write this class to save to the Database	
	}	
	
	public static int DistanceFareCalculation(int val) {
		int charge = 18 + 13*(val-1);
		return charge;
	}
	
	public static String GetUserDetails() {
		System.out.println("Enter Your Mobile Number");
		String Mob = in.next();
		System.out.println("Enter Your Hostel Number");
		String hostel = in.next();
		String Details = Mob + "  " + hostel ;
		return Details;
	}	
}	
		