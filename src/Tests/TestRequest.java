package Tests;

import Project.Request;
import junit.framework.TestCase;

/**
 * 
 * @author f2013049
 *
 */
public class TestRequest extends TestCase {
	public TestRequest() {
		req.setAttendanceCount(50);
		req.setBookingDate("12/12/2024");
		req.setBookingStatus("Approved");
		req.setDuration("02:04");
		req.setReason("Testing reasons");
		req.setRequestUID("Something");
		req.setRoom("A749");
		req.setStartingTime("17:56");
	}
	
	private Request req = new Request();
	
	public void testGetBookingDate() {
		assertEquals("12/12/2024", req.getBookingDate());
	}
	
	public void testGetDuration() {
		assertEquals("02:04", req.getDuration());
	}

	public void testGetBookingStatus() {
		assertEquals("Approved", req.getBookingStatus());
	}
	
	public void testGetReason() {
		assertEquals("Testing reasons", req.getReason());
	}
	
	public void testGetUID() {
		assertEquals("Something", req.getRequestUID());
	}
	
	public void testGetRoom() {
		assertEquals("A749", req.getRoom());
	}
	
	public void testGetStartingTime() {
		assertEquals("17:56", req.getStartingTime());
	}
}
