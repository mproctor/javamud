package javamud.room;

import static org.junit.Assert.*;

import org.junit.*;

import javamud.player.AbstractPlayer;
import javamud.player.Player;


public class RoomPlayerTest {

	private SimpleRoom r1;
	private AbstractPlayer p1,p2,p3;
	
	@Before
	public void setUp() {
		r1 = new SimpleRoom();
		p1 = new AbstractPlayer(){

			@Override
			public void hear(Player p, String s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void seeEvent(Player p, String s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void sendResponse(String string) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean hasKeyword(String name) {
				// TODO Auto-generated method stub
				return false;
			}};
		p2 = new AbstractPlayer(){

			@Override
			public void hear(Player p, String s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void seeEvent(Player p, String s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void sendResponse(String string) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean hasKeyword(String name) {
				// TODO Auto-generated method stub
				return false;
			}};
		p3 = new AbstractPlayer(){

			@Override
			public void hear(Player p, String s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void seeEvent(Player p, String s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void sendResponse(String string) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean hasKeyword(String name) {
				// TODO Auto-generated method stub
				return false;
			}};
	}
	
	@Test
	public void checkAdd() {
		assertEquals(0,r1.numberPlayers());
		r1.addPlayer(p1);
		assertEquals(1,r1.numberPlayers());
		r1.addPlayer(p2);
		assertEquals(2,r1.numberPlayers());
		r1.addPlayer(p3);
		assertEquals(3,r1.numberPlayers());
	}
	
	@Test
	public void checkNoDupes() {
		assertEquals(0,r1.numberPlayers());
		r1.addPlayer(p1);
		assertEquals(1,r1.numberPlayers());
		r1.addPlayer(p1);
		assertEquals(1,r1.numberPlayers());
	}
	
	@Test
	public void checkRemove(){
		assertEquals(0,r1.numberPlayers());
		r1.addPlayer(p1);
		r1.addPlayer(p2);
		r1.addPlayer(p3);
		assertEquals(3,r1.numberPlayers());
		r1.removePlayer(p2);
		assertEquals(2, r1.numberPlayers());
	}
	
	@Test
	public void checkFirst() {
		assertEquals(0,r1.numberPlayers());
		r1.addPlayer(p1);
		r1.addPlayer(p2);
		r1.addPlayer(p3);
		assertEquals(3,r1.numberPlayers());
		assertEquals(p1, r1.getFirstPlayer());
	}
	
	@Test
	public void checkNewFirst() {
		assertEquals(0,r1.numberPlayers());
		r1.addPlayer(p1);
		r1.addPlayer(p2);
		r1.addPlayer(p3);
		assertEquals(3,r1.numberPlayers());
		assertEquals(p1, r1.getFirstPlayer());
		r1.removePlayer(p1);
		assertEquals(p2, r1.getFirstPlayer());

	}
	
	@Test
	public void checkLast() {
		assertEquals(0,r1.numberPlayers());
		r1.addPlayer(p1);
		r1.addPlayer(p2);
		r1.addPlayer(p3);
		assertEquals(3,r1.numberPlayers());
		assertEquals(p3, r1.getLastPlayer());
	}
	@Test
	public void checkNewLast() {
		assertEquals(0,r1.numberPlayers());
		r1.addPlayer(p1);
		r1.addPlayer(p2);
		r1.addPlayer(p3);
		assertEquals(3,r1.numberPlayers());
		assertEquals(p1, r1.getFirstPlayer());
		r1.removePlayer(p3);
		assertEquals(p2, r1.getLastPlayer());
	}

	@Test
	public void checkReAddFirst() {
		assertEquals(0,r1.numberPlayers());
		r1.addPlayer(p1);
		r1.addPlayer(p2);
		r1.addPlayer(p3);
		assertEquals(3,r1.numberPlayers());
		assertEquals(p1, r1.getFirstPlayer());
		r1.removePlayer(p1);
		assertEquals(p2, r1.getFirstPlayer());
		r1.addPlayer(p1);
		assertEquals(p2, r1.getFirstPlayer());
	}
	@Test
	public void checkReAddLast() {
		assertEquals(0,r1.numberPlayers());
		r1.addPlayer(p1);
		r1.addPlayer(p2);
		r1.addPlayer(p3);
		assertEquals(3,r1.numberPlayers());
		assertEquals(p3, r1.getLastPlayer());
		r1.removePlayer(p3);
		assertEquals(p2, r1.getLastPlayer());
		r1.addPlayer(p3);
		assertEquals(p3, r1.getLastPlayer());
	}
	@Test
	public void checkMoveLast() {
		assertEquals(0,r1.numberPlayers());
		r1.addPlayer(p1);
		r1.addPlayer(p2);
		r1.addPlayer(p3);
		assertEquals(3,r1.numberPlayers());
		assertEquals(p3, r1.getLastPlayer());
		r1.removePlayer(p2);
		assertEquals(p3, r1.getLastPlayer());
		r1.addPlayer(p2);
		assertEquals(p2, r1.getLastPlayer());
	}
}
