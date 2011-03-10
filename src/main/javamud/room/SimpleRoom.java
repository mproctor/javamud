package javamud.room;

import java.util.LinkedList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javamud.item.Item;
import javamud.player.Player;
import javamud.room.SimpleExit.Direction;

public class SimpleRoom implements Room {
	
	private Set<Exit> exits = new HashSet<Exit>();
	private LinkedList<Player> players = new LinkedList<Player>();
	private int roomId = -1;
	private String title,description;
	
	public SimpleRoom(int i) {
		this.roomId = i; 
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getId() {
		return roomId;
	}

	@Override
	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public List<Item> getItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Exit> getExits() {
		return exits;
	}

	public void addExit(Exit e) {
		this.exits.add(e);
	}
	
	public Room getDestination(Direction dir) {
		for (Exit e: exits) {
			if (e.getDirection() == dir) {
				return e.getDestination();
			}
		}
		return null;
	}
	
	public Exit getExit(Direction dir) {
		for (Exit e: exits) {
			if (e.getDirection() == dir) {
				return e;
			}
		}
		return null;
	}

	@Override
	public void addPlayer(Player p) {
		synchronized(players) {
			if (!players.contains(p)) {
				players.add(p);
			}
		}
	}

	@Override
	public Player removePlayer(Player p) {
		boolean wasThere = false;
		synchronized(players) {
			wasThere = players.remove(p);
		}
		
		return (wasThere?p:null);
	}

	@Override
	public Player getFirstPlayer() {
		return players.peekFirst();
	}

	@Override
	public Player getLastPlayer() {
		return players.peekLast();
	}

	@Override
	public int getPositionInRoom(Player p) {
		return players.indexOf(p);
	}
	
	@Override
	public int numberPlayers() {
		return players.size();
	}
}
