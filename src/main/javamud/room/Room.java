package javamud.room;

import java.util.List;
import java.util.Set;

import javamud.item.Item;
import javamud.player.Player;

public interface Room {
	public String getTitle();
	public String getDescription();
	public int getId();
	
	/**
	 * players are in order of entering the room, so the first in
	 * the list has been in the room the longest
	 * @return the players in this room
	 */
	public List<Player> getPlayers();
	public List<Item> getItems();
	
	/**
	 * there are 0 or more exits to a room
	 * @return the exits from this room
	 */
	public Set<Exit> getExits();
	public void addExit(Exit e);
	
}
