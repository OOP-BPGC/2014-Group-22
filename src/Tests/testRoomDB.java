package Tests;

import java.util.ArrayList;

import Project.Room;
import Project.RoomDB;
import junit.framework.TestCase;

/**
 * 
 * @author f2013049
 *
 */
public class testRoomDB extends TestCase {
	public testRoomDB(String str) {
		super(str);
		
		db = new RoomDB();
		roomList = db.getRoomList();
		room = new Room();
		room.setRoomNumber("A749");
		room.setProjectorStatus(false);
		room.setStatus("booked");
		room.setStartingTime("12:04");
		room.setDuration("05:02");
		room.setBookingDate("10/15/2016");
		room.setCapacity(50);
		roomList.add(room);
	}
	
	public void testUpdateDB() {
		roomList.add(room);
		db.updateDB(roomList);
		roomList = db.getRoomList();
		newRoom = roomList.get(roomList.size() - 1);
		assertEquals("A749", newRoom.getRoomNumber());
		assertFalse("Error", newRoom.getProjectorStatus());
		assertEquals("booked", newRoom.getStatus());
		assertEquals(50, newRoom.getCapacity());
		assertEquals("12:04", (newRoom.getStartingTime()).get(0));
		assertEquals("05:02", (newRoom.getDuration()).get(0));
		assertEquals("10/15/2016", (newRoom.getBookingDate()).get(0));
	}
	
	private static Room room;
	private static Room newRoom;
	private static ArrayList<Room>roomList;
	private static RoomDB db;
}
