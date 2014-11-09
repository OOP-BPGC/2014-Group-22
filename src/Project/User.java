package Project;

public class User {
	
	private static String name;
	private static String id;
	
	public static void setId(String id)
	{
		User.id = id; 
	}
	
	public static void setName(String name)
	{	
		User.name = name;
	}
	
	public static String getId()
	{
		return User.id;
	}
	
	public static String getName()
	{
		return User.name;
	}

}
