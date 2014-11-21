package Tests;

import Project.Cab;
import junit.framework.TestCase;

public class TestCab extends TestCase {
	public TestCab() {
		cab = new Cab();
		cab.setBooked(1);
		cab.setCapacity(5);
		cab.setDriver("Ramesh");
		cab.setDriverPhone("7976794395");
		cab.setLicensePlate("GA09PD4454");
	}
	
	private Cab cab;
	
	public void testGetBooked() {
		assertEquals(1, cab.getBooked());
	}
	
	public void testGetCapacity() {
		assertEquals(5, cab.getCapacity());
	}
	
	public void testGetDriver() {
		assertEquals("Ramesh", cab.getDriver());
	}
	
	public void testGetDriverPhone() {
		assertEquals("7976794395", cab.getDriverPhone());
	}
	
	public void testGetLicencePlate() {
		assertEquals("GA09PD4454", cab.getLicensePlate());
	}
}
