package Project;

import java.io.IOException;
import java.util.Scanner;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * This class authenticates the user to Moodle server and extracts id and name.
 * 
 * @author nisarg
 */

public class Login {
	
	public static String id;
	public static String name;
    private static String bodyHtml = "null";
    
    
    /**
     * This method extracts the user's name from the HTML obtained from
     * authenticating to Moodle.
     * 
     * @return Name of the user
     */
    public static String extractName()
    {
        String name = "John Doe";
        int endIndex = bodyHtml.indexOf(" .</a> ");
        int startIndex = bodyHtml.indexOf("You are logged in as");
        for(int i = startIndex; i < endIndex; i++)
        {
            if(bodyHtml.charAt(i) == '>')
            {
                name = bodyHtml.substring(i+1, endIndex);
                break;
            }
        }
        return name;
    }
    
    
    /**
     * This is the main method which will be called from Driver.java to get
     * user details.
     */
    public static boolean authenticateUser()
    {
        // TODO: Implement password hiding in console. To be done after project
    	// runs successfully in console.
        
        id = "null";
        String password = "null";
        
        System.out.println("Enter your Moodle login credentials:");
        while(!connect(id, password))
        {
            if(!id.equals("null"))
            {
                System.out.println("Authentication failed! Try again!");
            }
            
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter id: ");
            id = sc.nextLine();
            System.out.println("Enter password: ");
            password = sc.nextLine();
        }
        
        name = extractName();
        return true;
    }    
    
    
    /**
     * Core method which connects to Moodle server and authenticates
     * the user's credentials.
     * @param id
     * @param pwd
     * @return True if login successful else False.
     */
    
    public static boolean connect(String id, String password) {
    	
    	boolean status = false;
    	
    	try {
    		
	    	Connection.Response loginForm = Jsoup.connect("http://10.1.1.242/moodle/login/index.php")
		    	.method(Connection.Method.GET)
		    	.execute();
	    	Document document = Jsoup
		    	.connect("http://10.1.1.242/moodle/login/index.php")
		    	.data("username", id)
		    	.data("password", password)
		    	.cookies(loginForm.cookies())
		    	.post();
	    	
	    	bodyHtml = document.body().html();
	    	// System.out.println(bodyHtml); // Debug statement
	    	
	    	status = bodyHtml.contains("You are logged in as");
	    	
    	} catch (IOException e) {
    		
    		System.out.println("IO exception!");
    		
    	} finally {
    		
    		return status;
    		
    	}
    }
    	
    	public static boolean isAdmin()
    	{
    		while(true)
    		{
    			System.out.println("\nEnter admin password");
    			Scanner in=new Scanner(System.in);
    			if(in.nextLine().equals("ADMIN"))
    			{
    				return true;
    			}
    			System.out.println("\nIncorrect password");
    		}	
    	}
    }

/* Debug method. Do not remove this.
    public static void main(String[] args)
    {
    	System.out.println(connect("f2013694", "Qwerty1234-"));
    }*/

