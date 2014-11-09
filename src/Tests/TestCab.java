package Tests;

import Project.*;


public class TestCab {
	
	private Cab cab = new Cab();
	
	public void testCabCabNumber()
	{
		cab.setCabNumber("RJ25SB7676");
		assertEquals("RJ25SB7676", cab.getCabNumber());
	}
	
	public void testCabStatus()
	{
		cab.setStatus("booked");
		assertEquals("booked", cab.getStatus());
	}
	
	public void testCabBookingTime()
	{
		cab.setBookingTime("12:04", "17.06");
		assertEquals("12:04", cab.getBookingTime());
	}
	
	public void testCabBookingDate()
	{
		cab.setBookingDate("10/15/2016");
		assertEquals("10/15/2016", cab.getBookingDate());
	}
	
	public void testCabCapacity()
	{
		cab.setCapacity(5);
		assertEquals(5, cab.getCapacity());
	}
	
	public void testCabDB() {
		Cab cab = new Cab();
		cab.setCabNumber("RJ25SB7676");
		cab.setStatus("booked");
		cab.setBookingType("time-based");
		cab.setBookingTime("12:04", "17.06");
		cab.setBookingDate("10/15/2016");
		cab.setCapacity(5);
		CabDB cabDB = new CabDB();
		cabDB.addSingleCab(cab);
		String[] str = cabDB.displayCab("RJ25SB7676");
		assertEquals("RJ25SB7676", str[0]);
		assertEquals("booked", str[1]);
		assertEquals("26/12/1256", str[2]);
		assertEquals("12:04", str[3]);
		assertEquals("17:06", str[4]);
		assertEquals("5", str[5]);
	}
	
	public void testCabBookTimeBased() {
		
		CabBookTimeBased book2 = new CabBookTimeBased();
		String UID = book2.generateUID();
		book2.bookCab(5, "RJ25SB7676", UID, "26/10/2016", "12:04", "17:06");
		assertEquals("RJ25SB7676", book2.getCab());
		assertEquals(5, book2.getAttendanceCount());
		assertEquals(500, book2.calcFare());
		assertEquals("26/10/2016", book2.getBookingDay());
		assertEquals("12:04", book2.getStartingTime());
		assertEquals("17:06", book2.getDuration());
		
	}
	
	public void testCabBookDistanceBased() {
		CabBookDistanceBased book2 = new CabBookDistanceBased();
		String UID = book2.generateUID();
		book2.bookCab(5, "RJ25SB7676", UID, "26/10/2016", "12:04", 56);
		assertEquals("RJ25SB7676", book2.getRoom());
		assertEquals(5, book2.getAttendanceCount());
		assertEquals(500, book2.calcFare());
		assertEquals("26/10/2016", book2.getBookingDay());
		assertEquals("12:04", book2.getStartingTime());
		assertEquals(56, book2.getDistance());
		try{
			book2.set_distance(-1);
		fail("Negative values not accepted");
		}
		catch(Exception  ex)
		{
			assertTrue(true);
		}
		
	}
	
	public void testCabBookDestinationBased() {
		CabBookDestinationBased book2 = new CabBookDestinationBased();
		String UID = book2.generateUID();
		book2.bookCab(5, "RJ25SB7676", UID, "26/10/2016", "12:04", "Margaon", "Vasco");
		assertEquals("RJ25SB7676", book2.getCab());
		assertEquals(5, book2.getAttendanceCount());
		assertEquals(500, book2.calcFare());
		assertEquals("26/10/2016", book2.getBookingDay());
		assertEquals("12:04", book2.getStartingTime());
		assertEquals("Margaon", book2.getInitialDestination());
		assertEquals("Vasco", book2.getFinalDestination());
		try
		{
			book2.set_destination("wagamama");
			fail("Invalid destination passed.");
		}catch(Exception ex)
		{
			assertTrue(true);
		}
		String[] d ={"Margaon","Vasco"};
		String[] g = new String[CabBookDestinationBased.nextdest];
		for(int i=0;i<CabBookDestinationBased.nextdest;i++)
			g[i]=book2.get_destinations()[i];
			assertArrayEquals(g,d);
		
	}

}
