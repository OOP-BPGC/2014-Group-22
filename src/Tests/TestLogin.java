package Tests;

import Project.*;
import junit.framework.TestCase;

public class TestLogin extends TestCase {

	public void testLoginConnect()
	{
		/*
		Not used real password due to privacy reasons.
		Replace "f2013694" and "qwerty" with appropriate
		userid and password to see it glow!
		*/
		assertEquals(false, Login.connect("f2013694", "qwerty"));
	}

}