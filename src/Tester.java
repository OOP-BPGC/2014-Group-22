import junit.framework.TestCase;

public class Tester extends TestCase {
	public void testUser() {
		User use = new User();
		use.setID("2013A7PS049G");
		use.setname("Shubham");
		
		assertEquals("2013A7PS049G", use.getID());
		assertEquals("Shubham", use.getname());
	}
	
	public void testRoom() {
		Room room = new Room();
		room.setRoomNumber("A604");
		room.setProjectorStatus(false);
		room.setStatus("booked");
		room.setBookingTime("12:04", "17.06");
		room.setBookingDate("10/15/2016");
		room.setCapacity(50);
		
		assertEquals("A604", room.getRoomNumber());
		assertFalse("Error", room.getProjectorStatus());
		assertEquals("booked", room.getStatus());
		assertEquals(50, room.getCapacity());
		assertEquals("12:04", room.getStartingTime());
		assertEquals("17.06", room.getDuration());
		assertEquals("10/15/2016", room.getBookingDate());
	}
	
	public void testBookRoom() {
		RoomBook book2 = new RoomBook();
		String UID = book2.generateUID();
		book2.bookRoom(50, "A604", UID, "26/10/2016", "12:04", "17:06");
		assertEquals("A604", book2.getRoom());
		assertEquals(50, book2.getAttendanceCount());
		assertEquals("26/10/2016", book2.getBookingDay());
		assertEquals("12:04", book2.getStartingTime());
		assertEquals("17:06", book2.getDuration());
	}

	public void testRoomDB() {
		RoomDB roomDB= new RoomDB();
		Room room = new Room();
		room.setBookingDate("26/12/1256");
		room.setBookingTime("12:04", "17:06");
		room.setCapacity(56);
		room.setStatus("booked");
		room.setRoomNumber("A609");
		room.setProjectorStatus(false);
		roomDB.addSingleRoom(room);
		String[] str = roomDB.displayRoom("A609");
		assertEquals("A609", str[0]);
		assertEquals("false", str[1]);
		assertEquals("booked", str[2]);
		assertEquals("26/12/1256", str[3]);
		assertEquals("12:04", str[4]);
		assertEquals("17:06", str[5]);
		assertEquals("56", str[6]);
/*		roomDB.modifyRoom();
 * I have just added this. CAll modifyRoom such that it modifies below two things of room A609
 *Set projector status as true
 *set status as available
		str = roomDB.displayRoom("A609");
		assertEquals("A609", str[0]);
		assertEquals("true", str[1]);
		assertEquals("available", str[2]);
		assertEquals("26/12/1256", str[3]);
		assertEquals("12:04", str[4]);
		assertEquals("17:06", str[5]);
		assertEquals("56", str[6]);
*/
	}
	
	public void testCab() {
		Cab cab = new Cab();
		cab.setCabNumber("RJ25SB7676");
		cab.setStatus("booked");
		cab.setBookingTime("12:04", "17.06");
		cab.setBookingDate("10/15/2016");
		cab.setCapacity(5);
		
		assertEquals("RJ25SB7676", cab.getCabNumber());
		assertEquals("booked", cab.getStatus());
		assertEquals(5, cab.getCapacity());
		assertEquals("12:04", cab.getStartingTime());
		assertEquals("17.06", cab.getDuration());
		assertEquals("10/15/2016", cab.getBookingDate());
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
