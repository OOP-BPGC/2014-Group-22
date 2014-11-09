package Tests;

import Project.*;
import junit.framework.TestCase;

public class TestLogin extends TestCase {

	public void testLoginConnect()
	{
		assertEquals(false, Login.connect("f2013694", "qwerty")); // Not using real password.
	}

}