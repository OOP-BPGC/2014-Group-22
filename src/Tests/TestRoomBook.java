package Tests;

import java.util.ArrayList;

import Project.Request;
import Project.RoomBook;
import junit.framework.TestCase;

/**
 * 
 * @author f2013049
 *
 */
public class TestRoomBook extends TestCase {
	@SuppressWarnings("static-access")
	public TestRoomBook(String str) {
		super(str);
		// Initialization of attributes for testing purposes
		book = new RoomBook();
		reqs = book.getRequestsMade();
		fakereq = new Request();
		fakereq.setAttendanceCount(50);
		fakereq.setBookingDate("12/12/2024");
		fakereq.setBookingStatus("Approved");
		fakereq.setDuration("02:04");
		fakereq.setReason("Testing reasons");
		fakereq.setRequestUID("b2c3");
		fakereq.setRoom("A749");
		fakereq.setStartingTime("17:56");
	//	reqs.add(fakereq);
	//	book.updateRoomRequestDB(reqs);
	}
	
	@SuppressWarnings("static-access")
	public void testUpdateRoomRequestDB() {
	//	book.updateRoomRequestDB(reqs);
		reqs.add(fakereq);
		book.updateRoomRequestDB(reqs);
		reqs = book.getRequestsMade();
		int n = reqs.size();
		Request req = reqs.get(n - 1);
		assertEquals("12/12/2024", req.getBookingDate());
		assertEquals("02:04", req.getDuration());
		assertEquals("Approved", req.getBookingStatus());
		assertEquals("Testing reasons", req.getReason());
		assertEquals("b2c3", req.getRequestUID());
		assertEquals("A749", req.getRoom());
		assertEquals("17:56", req.getStartingTime());
		book.cancelRequest("b2c3");
	}

	@SuppressWarnings("static-access")
	public void testCancelRequest() {
		reqs.add(fakereq);
		book.updateRoomRequestDB(reqs);
		book.cancelRequest("b2c3");
		// The next line is useful only when the RequestRoomDB file is empty.
		book.cancelRequest("b2c3");
		reqs = book.getRequestsMade();
		int n = reqs.size();
		for (int i = 0; i < n; i++)
			assertFalse(fakereq.getRoom().equals(reqs.get(i).getRoom()));
	}

	private ArrayList<Request> reqs;
	private RoomBook book;
	private Request fakereq;
}
