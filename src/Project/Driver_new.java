package Project;


// Varsheeth and me working on this.

import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * In the whole program, while loops are used for checking the validity of inputs i.e., if
 * it is an invalid input, while loop keeps continuing until user enters valid input.
 */
public class Driver_new
{
	static Scanner in = new Scanner(System.in);
	
	public static int mainMenu()
	{
		int inp = 3;
		int inpFromLoginMenu = -1;
		boolean flag=false;
		while(!flag)
	    {
			System.out.println("\nEnter the no. to select the respective option");
		    System.out.println("1.Class Room Booking.");
		    System.out.println("2.Cab Booking.");
		    System.out.println("3.Exit");
		    try{
		    	// Scanner in = new Scanner(System.in);
			    inp = in.nextInt();
		    }catch(InputMismatchException e){
		    	System.out.println("\nInvalid option. Select again.");
		    	continue;
		    }
		    // NISARG'S MOD
		    switch(inp)
		    {
			    case 1:
			    	inpFromLoginMenu = loginMenu(inp);
			    	break;
			    case 2:
			    	inpFromLoginMenu = loginMenu(inp);
			    	break;
			    case 3:
			    	return inp;
		    }
		    
		    switch(inpFromLoginMenu)
		    {
		    	case 3:
		    		continue;
		    	case 4:
		    		return 3;
		    }
		    // END OF NISARG'S MOD
		    
		    // VARSHEETH'S CODE
		    /*if(inp==3)
		    {
		    	return inp;
		    }
		    inpFromLoginMenu = loginMenu(inp);
		    if(inpFromLoginMenu==4)
		    {
		    	return 3;
		    	//Returning '3' because '4' in loginMenu() is 'exit' which is '3' here.
		    }
		    if(inpFromLoginMenu==3)
		    {
		    	//Continuing because '3' in loginMenu() is 'mainMenu' which means we have to keep running the loop here.
		    	continue;
		    }*/
		    break;
	    }	
		return inp;
	}
	/*
	 * loginMenu() has parameter so that mainMenu() passes the cab or room option into login module.
	 * Based on this, we can select user in 'case 1'.
	 * If this wasn't there, after login was authenticated the program would forget whether user wanted cab or room booking.
	 */
	
	public static int loginMenu(int inpFromMainMenu)
	{
		int inp = 4;
		boolean flag = false;
		while(!flag)
	    {
			System.out.println("\n1.User Login");
	        System.out.println("2.Admin Login");
	        System.out.println("3.Return to Main Menu");
	        System.out.println("4.Exit.");
	        try{
		    	//Scanner in = new Scanner(System.in);
			    inp = in.nextInt();
		    }catch(InputMismatchException ime){
		    	System.out.println("\nInvalid option. Select again.");
		    	continue;
		    }
		    switch(inp)
	        {
	            case 1: 
	            	if(inpFromMainMenu==1)
            		{
            			int inpFromUserLoginForRoom=userLoginForRoom();
            			if(inpFromUserLoginForRoom==2)
            			{
            				return 3;
            			}else if(inpFromUserLoginForRoom==3){
            				return 4;
            			}
            		}
            		else if(inpFromMainMenu==2)
            		{	            		
            			int inpFromUserLoginForCab=userLoginForCab();
            			if(inpFromUserLoginForCab==5)
            			{
            				return 3;
            			}else if(inpFromUserLoginForCab==6){
            				return 4;
            			}
            		}            	
            		flag=true; 
            		break;
            		
	            case 2:
	            	adminLogin();
	            	flag=true;
	            	break;
	            	
	            case 3:
	            	return inp;
	            	
	            case 4:
	            	return inp;
	            	
	            default : System.out.println("\nInvalid option. Select again.");
	        }
	    }
		return inp;
	}
	//Created two user logins to distinguish cab booking from room booking.
	public static int userLoginForRoom()
	{
	    Login.authenticateUser();
	    System.out.println("\nYour username is " + Login.id + " and your name is " + Login.name);
	    return roomMenu();
	}
	public static int userLoginForCab()
	{
		Login.authenticateUser();
	    System.out.println("\nYour username is " + Login.id + " and your name is " + Login.name);
	    return cabMenu();
	}
	
	public static int adminLogin()
	{
		while(true)
		{
			System.out.println("\nEnter admin password");
			//Scanner in=new Scanner(System.in);
			if(in.nextLine().equals("ADMIN"))
			{
				return 1;
			}
			System.out.println("\nIncorrect password");
		}	
	}
	public static int roomMenu()
	{
		int inp=3;
		while(true)
		{
			 System.out.println("\n1.Request Room.");
	         System.out.println("2.Main Menu.");
	         System.out.println("3.Exit.");
	         try{
	        	 //Scanner in = new Scanner(System.in);
				 inp = in.nextInt();
			 }catch(InputMismatchException ime){
			     System.out.println("\nInvalid option. Select again.");
			     continue;
			 }
		     return inp;
		}	 
	}
	public static int cabMenu()
	{
		int inp=6;
		while(true)
		{
			System.out.println("\n1.Check Fares.");
	        System.out.println("2.Book a Cab based on Distance.");
	        System.out.println("3.Book a Cab based on Time.");
	        System.out.println("4.Book a Cab based on Destination.");
	        System.out.println("5.Main Menu.");
	        System.out.println("6.Exit.");
	        try{
		    	//Scanner in = new Scanner(System.in);
			    inp = in.nextInt();
		    }catch(InputMismatchException ime){
		    	System.out.println("\nInvalid option. Select again.");
		    	continue;
		    }
	        return inp;
		}	
	}
	
	public static void main(String[] args)
	{
		int inpFromMainMenu = mainMenu();
		if (inpFromMainMenu==3)
		{
			return;	
		}		
	}
	
}