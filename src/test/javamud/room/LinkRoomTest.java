package javamud.room;

import static org.junit.Assert.*;

import org.junit.Test;


public class LinkRoomTest {
	
	@Test
	public void roomNorthSouth() {
		SimpleRoom r1 = new SimpleRoom();
		SimpleRoom r2 = new SimpleRoom();
		
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.North, null, null);
		
		assertEquals(r2,r1.getDestination(SimpleExit.Direction.North));
		assertEquals(r1,r2.getDestination(SimpleExit.Direction.South));
	}
	@Test
	public void roomSouthNorth() {
		SimpleRoom r1 = new SimpleRoom();
		SimpleRoom r2 = new SimpleRoom();
		
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.South, null, null);
		
		assertEquals(r2,r1.getDestination(SimpleExit.Direction.South));
		assertEquals(r1,r2.getDestination(SimpleExit.Direction.North));
	}
	@Test
	public void roomEastWest() {
		SimpleRoom r1 = new SimpleRoom();
		SimpleRoom r2 = new SimpleRoom();
		
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.East, null, null);
		
		assertEquals(r2,r1.getDestination(SimpleExit.Direction.East));
		assertEquals(r1,r2.getDestination(SimpleExit.Direction.West));
	}
	@Test
	public void roomWestEast() {
		SimpleRoom r1 = new SimpleRoom();
		SimpleRoom r2 = new SimpleRoom();
		
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.West, null, null);
		
		assertEquals(r2,r1.getDestination(SimpleExit.Direction.West));
		assertEquals(r1,r2.getDestination(SimpleExit.Direction.East));
	}
	@Test
	public void roomUpDown() {
		SimpleRoom r1 = new SimpleRoom();
		SimpleRoom r2 = new SimpleRoom();
		
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.Up, null, null);
		
		assertEquals(r2,r1.getDestination(SimpleExit.Direction.Up));
		assertEquals(r1,r2.getDestination(SimpleExit.Direction.Down));
	}
	@Test
	public void roomDownUp() {
		SimpleRoom r1 = new SimpleRoom();
		SimpleRoom r2 = new SimpleRoom();
		
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.Down, null, null);
		
		assertEquals(r2,r1.getDestination(SimpleExit.Direction.Down));
		assertEquals(r1,r2.getDestination(SimpleExit.Direction.Up));
	}
	@Test
	public void defaultOpenBothSides() {
		SimpleRoom r1 = new SimpleRoom();
		SimpleRoom r2 = new SimpleRoom();
		
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.North, null, null);
		
		assertEquals(r1.getExit(SimpleExit.Direction.North).isOpen(), true);
		assertEquals(r2.getExit(SimpleExit.Direction.South).isOpen(), true);
	}
	@Test
	public void openBothSides() {
		SimpleRoom r1 = new SimpleRoom();
		SimpleRoom r2 = new SimpleRoom();
		
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.North, SimpleExit.ExitStatus.Open, null);
		
		assertEquals(r1.getExit(SimpleExit.Direction.North).isOpen(), true);
		assertEquals(r2.getExit(SimpleExit.Direction.South).isOpen(), true);
	}
	@Test
	public void closedBothSides() {
		SimpleRoom r1 = new SimpleRoom();
		SimpleRoom r2 = new SimpleRoom();
		
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.North, SimpleExit.ExitStatus.Closed, null);
		
		assertEquals(r1.getExit(SimpleExit.Direction.North).isClosed(), true);
		assertEquals(r2.getExit(SimpleExit.Direction.South).isClosed(), true);
	}
	@Test
	public void lockedBothSides() {
		SimpleRoom r1 = new SimpleRoom();
		SimpleRoom r2 = new SimpleRoom();
		
		RoomLinker.makeSymmetricLink(r1, r2, SimpleExit.Direction.North, SimpleExit.ExitStatus.Locked, null);
		
		assertEquals(r1.getExit(SimpleExit.Direction.North).isLocked(), true);
		assertEquals(r2.getExit(SimpleExit.Direction.South).isLocked(), true);
	}
}
