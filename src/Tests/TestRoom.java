package Tests;

import Project.*;
import junit.framework.*;

/**
 * This class contains test modules for Room class.
 * Download Room/java from src/Project/ directory to run these tests.
 * @author f2013049
 *
 */

public class TestRoom extends TestCase
{
	public TestRoom(String str)
	{
		super(str);
		room.setRoomNumber("A749");
		room.setProjectorStatus(false);
		room.setStatus("booked");
		room.setStartingTime("12:04");
		room.setDuration("04:56");
		room.setBookingDate("10/15/2016");
		room.setCapacity(50);
	}
	
	private static Room room = new Room();
	
	public void testGetRoomNumber()
	{
		assertEquals("A749", room.getRoomNumber());
	}
	public void testGetProjectorStatus()
	{
		assertFalse("Error", room.getProjectorStatus());
	}
	public void testGetStatus()
	{
		assertEquals("booked", room.getStatus());
	}
	public void testGetCapacity()
	{
		assertEquals(50, room.getCapacity());
	}
	public void testGetStartingTime()
	{
		assertEquals("12:04", (room.getStartingTime()).get(0));
	}
	public void testGetDuration()
	{
		assertEquals("04:56", (room.getDuration()).get(0));
	}
	public void testGetBookingDate()
	{
		assertEquals("10/15/2016", (room.getBookingDate()).get(0));
	}
	
	
}