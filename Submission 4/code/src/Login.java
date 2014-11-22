package src;
import java.io.Console;
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
	
	private static String id;
	private static String name;
	
    public static String getId() {
		return Login.id;
	}

	public static void setId(String id) {
		Login.id = id;
	}

	public static String getName() {
		return Login.name;
	}

	public static void setName(String name) {
		Login.name = name;
	}

	private static String bodyHtml = "null";
    
    /**
     * This is the main method which will be called from Driver.java to get
     * user details.
     * @return true if authentication successful, false otherwise
     */
    public static boolean authenticateUser()
    {
        // TODO: Implement password hiding in console. To be done after project
    	// runs successfully in console.
        
        id = "null";
        String password = "null";
        Console console = System.console();
	if (console == null) {
	        System.out.println("Couldn't get Console instance");
	        System.exit(0);
	}
	
        System.out.println("Enter your Moodle login credentials:");
        Scanner sc = new Scanner(System.in);
        while(!connect(id, password))
        {
            if(!id.equals("null"))
            {
                System.out.println("Authentication failed! Try again!");
            }
            
            System.out.println("Enter id: ");
            id = sc.nextLine();
	    char passwordArray[] = console.readPassword("Enter password: ");
            password = new String(passwordArray);
        }
        name = extractName();
        return true;
    }
    
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
     * Core method which connects to Moodle server and authenticates
     * the user's credentials.
     * @param id The moodle username
     * @param password Moodle password
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
	        Console console = System.console();
		if (console == null) {
		        System.out.println("Couldn't get Console instance");
		        System.exit(0);
		}
		while(true)
		{
			char passwordArray[] = console.readPassword("Enter password: ");
			String temp = new String(passwordArray);
			if(temp.equals("ADMIN"))
			{
				return true;
			}
			System.out.println("\nIncorrect password");
		}
	}

/* Debug method. Do not remove this.
    public static void main(String[] args)
    {
    	System.out.println(connect("f2013694", "Qwerty1234-"));
    }*/
}
