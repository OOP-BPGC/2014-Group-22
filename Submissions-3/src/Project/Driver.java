package Project;
import java.util.Scanner;
/*
 *@author AbhishekTiwari
 */
public class Driver
{
	public static void main(String[] args)
	{
		char menu1='y';
		int inp;
		String UID = null;
		Scanner in = new Scanner(System.in);
		RoomBook bookaroom = new RoomBook();
		while(menu1=='y'||menu1=='Y')
		{
			System.out.println("MAIN MENU");
			System.out.println("-----------------------------");
			System.out.println("1.Class Room Booking.\n2.Cab Booking.\n3.Exit");
			inp = in.nextInt();
			switch(inp)
			{
				case 1 : 			
					int choice2;
					char menu2='y';
					boolean flag=false;
					while(menu2=='y')
					{
						System.out.println("ROOM BOOKING MENU");
						System.out.println("-----------------------------");
						System.out.println("1.Request Room.\n2.Check Request Status.\n3.Cancel Request.");
						System.out.println("4.Admin Login.\n5.Previous Menu.\n6.Exit.\nEnter Choice:\n");	
						choice2=in.nextInt();
						int prevMenu = 0;
						switch(choice2)
						{
						case 1: 
							//if(Login.authenticateUser())
								bookaroom.generateForm();
							break;
						case 2:	
							System.out.println("\nEnter your request UID :\n");	
							UID=in.next();
							bookaroom.displayStatus(UID);
							break;
						case 3: 
							System.out.println("\nEnter your request UID :\n");	
							UID=in.next();
							bookaroom.cancelRequest(UID);
							break;
						case 4: //somehow run AdminSession From Here!
							//if(Login.isAdmin())
								AdminRoom.runAdminSession();
							break;
						case 5: prevMenu = 1;	
							break;
						case 6: System.exit(0);
							break;
						default : System.out.println("Wrong choice, choose again.");
						}	
						if(prevMenu==0){
							System.out.println("Display Room Booking Menu again?(y/n)");
						menu2=in.next().charAt(0);
						}
						else
							menu2='n';
					}
					break;				
				case 2 :
					int choice3;
					char menu3='y';
					boolean flag2=false;
					while(menu3=='y')
					{
						System.out.println("1.Check Fares.");
						System.out.println("2.Book a Cab based on Distance.");
						System.out.println("3.Book a Cab based on Time.");
						System.out.println("4.Book a Cab based on Destination.");
						System.out.println("5.Admin Login.");
						System.out.println("6.Main Menu.");
						System.out.println("7.Exit.");
						choice3=in.nextInt();
						switch(choice3)
						{
							case 1: 
								break;
							case 2:
								break;
							case 3:
								break;
							case 4:
								break;
							case 5: break;
							case 6 : 
								flag2=true;
								break;
							case 7 : System.exit(0);
								break;
							default : System.out.println("Wrong choice, choose again.");
						}	
						if(flag2)
							break;
						System.out.println("Display Cab Booking Menu again?(y/n)");
						menu3=in.next().charAt(0);
					}
					break;
				case 3: System.exit(0);
					break;
				default :System.out.println("Wrong choice, choose again");
			}
			System.out.println("Go Back to Main Menu?(y/n)");
			menu1=in.next().charAt(0);
		}
	}
}