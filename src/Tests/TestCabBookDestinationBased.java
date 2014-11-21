package Tests;

import Project.Cab;
import Project.CabBookDestinationBased;
import junit.framework.TestCase;

public class TestCabBookDestinationBased extends TestCase {
	public TestCabBookDestinationBased() {
		cdst = new CabBookDestinationBased();
		cdst.generateUID();
		cdst.setBookedBy("Shubham Jain");
		cdst.setFinalDate("25/12/2014");
		cdst.setFinalTime("17:05");
		cdst.setInitialDate("24/12/2014");
		cdst.setInitialTime("02:06");
		cab = new Cab();
		cab.setBooked(1);
		cab.setCapacity(5);
		cab.setDriver("Ramesh");
		cab.setDriverPhone("7976794395");
		cab.setLicensePlate("GA09PD4454");
		cdst.setCab(cab);
	}
	
	private Cab cab;
	private CabBookDestinationBased cdst;
	
	public void testGetCab() {
		assertSame(cab, cdst.getCab());
	}
	
	public void testGetBookedBy() {
		assertEquals("Shubham Jain", cdst.getBookedBy());
	}
	
	public void testGetInitialTime() {
		assertEquals("02:06", cdst.getInitialTime());
	}
	
	public void testGetFinalTime() {
		assertEquals("17:05", cdst.getFinalTime());
	}
	
	public void testGetInitialDate() {
		assertEquals("24/12/2014", cdst.getInitialDate());
	}
	
	public void testGetFinalDate() {
		assertEquals("25/12/2014", cdst.getFinalDate());
	}
	
	public void testGetBookingType() {
		assertEquals("Destination", cdst.getBookingType());
	}
}