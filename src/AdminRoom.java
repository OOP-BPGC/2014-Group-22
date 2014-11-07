package Project;
import Project.RoomDB;
import java.util.Scanner;
import java.util.ArrayList;
public class AdminRoom
{
	public static void main(String[] args)
	{
		char AdminMenu = 'y';
		int choice = 0;
		Scanner inp = new Scanner(System.in);
		while(AdminMenu=='y'||AdminMenu=='Y')
		{
			System.out.println("1.Add Room.\n2.Modify Room Details.\n3.Evaluate Requests.\n4.Display Rooms.\nEnter choice: ");
			choice=inp.nextInt();
			switch(choice)
			{
				case 1: RoomDB.addRoom();
					break;
				case 2: 
					break;
				case 3: 
					break;
				case 4: RoomDB.displayRooms();
					break;
				default: System.out.println("Wrong Choice. Please Try Again.");
			}
			System.out.println("Show Admin Menu again?(y/n)");
			AdminMenu=inp.next().charAt(0);
		}
	}
}

