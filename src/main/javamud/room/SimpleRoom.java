package javamud.room;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javamud.item.Item;
import javamud.player.Player;
import javamud.room.SimpleExit.Direction;

public class SimpleRoom implements Room {
	
	private Set<Exit> exits = new HashSet<Exit>();

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Player> getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Exit> getExits() {
		// TODO Auto-generated method stub
		return null;
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
}
