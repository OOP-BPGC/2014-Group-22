import java.io.BufferedReader;
import java.io.IOException ;
import java.io.*;
import java.util.Scanner;

public class CalciFare {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader("Destinations.txt"));
		}
		catch(FileNotFoundException e)
		{
			
		}	
		String line;
		while((line = in.readLine()) != null)
		{
			System.out.println(line);
		}
		in.close();
	
		System.out.println("These destinations are available for booking. Enter the number of the destination you want to select.");
		Scanner inp = new Scanner(System.in);
		int number = inp.nextInt();
		System.out.println("");
		String check = "Number - " + number;
		in = new BufferedReader(new FileReader("Destinations.txt"));
		while((line = in.readLine()) != null)
		{
			if(line.contains(check))
			{
				for(int i=0;i<4;i++) {
					System.out.println(line);
					line = in.readLine();
				}
				break;
			}	
		}
		
		in.close();
	}
}	