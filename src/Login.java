
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
    
    private static String bodyHtml = "null";
    
    /**
     * This method extracts the user's name from the HTML obtained from
     * authenticating to Moodle.
     * 
     * @return Name of the user
     */
    public static String getName()
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
     * @return A string array of the form [userid, name]
     */
    public static String[] authenticateUser()
    {
        // TODO: Implement password hiding in console
        
        String username = "null";
        String password = "null";
        
        System.out.println("Enter your Moodle login credentials:");
        while(!connect(username, password))
        {
            if(!username.equals("null"))
            {
                System.out.println("Authentication failed! Try again!");
            }
            
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter username: ");
            username = sc.nextLine();
            System.out.println("Enter password: ");
            password = sc.nextLine();
        }
        
        String name = getName();
        return new String[]{username, name};
    }
    
    
    
    /**
     * Core method which connects to Moodle server and authenticates
     * the user's credentials.
     * @param usrname
     * @param pwd
     * @return True if login successful else False.
     */
    
    public static boolean connect(String usrname, String pwd) {
        
        String username = usrname;
        String password = pwd;
        boolean status = false;
        
        try {
            
            Connection.Response loginForm = Jsoup
                .connect("http://10.1.1.242/moodle/login/index.php")
                .method(Connection.Method.GET)
                .execute();

            Document document = Jsoup
                .connect("http://10.1.1.242/moodle/login/index.php")
                .data("username", username)
                .data("password", password)
                .cookies(loginForm.cookies())
                .post();
            
            bodyHtml = document.body().html();
            status = bodyHtml.contains("You are logged in as");

        } catch (IOException e) {
            
            System.out.println("IO exception!");
            
        } finally {
           
            return status;
            
        }
    }
}
