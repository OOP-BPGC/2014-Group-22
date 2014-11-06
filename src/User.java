<<<<<<< HEAD

=======
//package Project;
>>>>>>> cb495fdd9d204f060ec9b22a56a6b2ec9cc461de
//import java.util.regex.Pattern;
//import java.util.regex.Matcher;
import java.util.Scanner;
public class User
{
	private String name;
	private String ID;
        
        public User(String id, String name)
        {
            this.name = name;
            this.ID = id;
        }

        /*
	public void setname(String nm){
		name = nm;
	}
 
	public boolean setID(String id_inp){
		boolean result;
		if((result=checkID(id_inp))){
			ID=id_inp;
			System.out.println("Valid ID. Accepted.");
		}
		else
			System.out.println("ID not set for "+name+". Invalid ID given. Try Again.");
		return result;
	}
	private boolean checkID(String id){
		Pattern pat = Pattern.compile("^20[0-9]{2}[abAB][0-9](ps|PS)[0-9]{0,4}[Gg]$");
		Matcher match = pat.matcher(id);
		return match.find();
	}
        
        
	public boolean setDetails(){	
		Scanner inp = new Scanner(System.in);
		System.out.println("Enter name:\n");
		setname(inp.nextLine());
		System.out.println("Enter ID:\n");
		return setID(inp.nextLine());
	}
        
	public String getname(){
		return name;
	}
	public String getID(){
		return ID;
	}
}
	ONLY FOR TESTING PURPOSES
class UserTester{
	public static void main(String[] args){
		User student1 = new User();
		student1.setname(args[0]);
		student1.setID(args[1]); 
	}
}*/
