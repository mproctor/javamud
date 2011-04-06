package javamud.room;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class LinkRoomTest {
	SimpleRoom r1,r2;
	
	@Before
	public void createRooms() {
		r1 = new SimpleRoom();
		r2 = new SimpleRoom();
	}
	
	@Test
	public void roomNorthSouth() {
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.North, null, null);
		
		assertEquals(r2,r1.getDestination(SimpleExit.Direction.North));
		assertEquals(r1,r2.getDestination(SimpleExit.Direction.South));
	}
	@Test
	public void roomSouthNorth() {
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.South, null, null);
		
		assertEquals(r2,r1.getDestination(SimpleExit.Direction.South));
		assertEquals(r1,r2.getDestination(SimpleExit.Direction.North));
	}
	@Test
	public void roomEastWest() {
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.East, null, null);
		
		assertEquals(r2,r1.getDestination(SimpleExit.Direction.East));
		assertEquals(r1,r2.getDestination(SimpleExit.Direction.West));
	}
	@Test
	public void roomWestEast() {
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.West, null, null);
		
		assertEquals(r2,r1.getDestination(SimpleExit.Direction.West));
		assertEquals(r1,r2.getDestination(SimpleExit.Direction.East));
	}
	@Test
	public void roomUpDown() {
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.Up, null, null);
		
		assertEquals(r2,r1.getDestination(SimpleExit.Direction.Up));
		assertEquals(r1,r2.getDestination(SimpleExit.Direction.Down));
	}
	@Test
	public void roomDownUp() {
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.Down, null, null);
		
		assertEquals(r2,r1.getDestination(SimpleExit.Direction.Down));
		assertEquals(r1,r2.getDestination(SimpleExit.Direction.Up));
	}
	@Test
	public void defaultOpenBothSides() {
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.North, null, null);
		
		assertEquals(r1.getExit(SimpleExit.Direction.North).isOpen(), true);
		assertEquals(r2.getExit(SimpleExit.Direction.South).isOpen(), true);
	}
	@Test
	public void openBothSides() {
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.North, SimpleExit.ExitStatus.Open, null);
		
		assertEquals(r1.getExit(SimpleExit.Direction.North).isOpen(), true);
		assertEquals(r2.getExit(SimpleExit.Direction.South).isOpen(), true);
	}
	@Test
	public void closedBothSides() {
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.North, SimpleExit.ExitStatus.Closed, null);
		
		assertEquals(r1.getExit(SimpleExit.Direction.North).isClosed(), true);
		assertEquals(r2.getExit(SimpleExit.Direction.South).isClosed(), true);
	}
	@Test
	public void lockedBothSides() {
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.North, SimpleExit.ExitStatus.Locked, null);
		
		assertEquals(r1.getExit(SimpleExit.Direction.North).isLocked(), true);
		assertEquals(r2.getExit(SimpleExit.Direction.South).isLocked(), true);
	}
}
