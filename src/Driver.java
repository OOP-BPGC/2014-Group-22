package project;
import java.util.Scanner;

public class Driver
{
	public static void main(String[] args)
	{
		char menu1='y';
		int inp;
		Scanner in = new Scanner(System.in);
		while(menu1=='y'||menu1=='Y')
		{
			System.out.println("1.Class Room Booking.");
			System.out.println("2.Cab Booking.");
			inp = in.nextInt();
			switch(inp)
			{
				case 1 : 			
					int choice2;
					char menu2='y';
					boolean flag=false;
					while(menu2=='y')
					{
						System.out.println("1.Request Room.");
						System.out.println("2.Admin Login.");
						System.out.println("3.Main Menu.");
						//System.out.println("4.Exit.");
						
						choice2=in.nextInt();
						switch(choice2)
						{
						case 1: 
							break;
						case 2:
							break;
						case 3: flag=true;
							break;
						default : System.out.println("Wrong choice, choose again.");
						}	
						if(flag)	
							break;
						System.out.println("Display Room Booking Menu again?(y/n)");
						menu2=in.next().charAt(0);
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
							default : System.out.println("Wrong choice, choose again.");
						}	
						if(flag2)
							break;
						System.out.println("Display Cab Booking Menu again?(y/n)");
						menu3=in.next().charAt(0);
					}
					break;
				default :System.out.println("Wrong choice, choose again");
			}
			System.out.println("Go Back to Main Menu?(y/n)");
			menu1=in.next().charAt(0);
		}
	}
}
