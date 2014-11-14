package Tests;

import Project.*;
import junit.framework.*;

/**
 * 
 * @author f2013049
 *
 */
public class TestUser extends TestCase
{
	static User user = new User();
	
	public TestUser(String str)
	{
		super(str);
		user.setID("2013A7PS049G");
		user.setname("Shubham");
	}
	
	public void testGetId()
	{
		assertEquals("2013A7PS049G", user.getID());
	}
	public void testGetName()
	{
		assertEquals("Shubham", user.getname());
	}	
	
}