package javamud.room;

import java.util.List;
import java.util.Set;

import javamud.item.Item;
import javamud.player.Player;
import javamud.room.Exit.Direction;

public interface Room {
	public static int ROOMID_NULL=Integer.MIN_VALUE;
	public String getTitle();
	public String getDescription();
	public int getId();
	
	/**
	 * players are in order of entering the room, so the first in
	 * the list has been in the room the longest
	 * @return the players in this room
	 */
	public List<Player> getPlayers();
	public void addPlayer(Player p);
	
	/**
	 * returns the same player or null if not present
	 * @param player to remove
	 * @return the player removed or null if player was not in room
	 */
	public Player removePlayer(Player p);
	
	/**
	 * does not remove player from room
	 * @return the player who has been in room longest
	 */
	public Player getFirstPlayer();
	
	/**
	 * does not remove player from room
	 * @return the player who has been in room the shortest
	 */
	public Player getLastPlayer();
	
	/**
	 * TODO: does this need to be in the interface?
	 * returns the position in the room (0 is first, -1 if not present)
	 * @param player to find in room
	 * @return the position in the room from 0, or -1 if player not in room
	 */
	public int getPositionInRoom(Player p);
	
	public int numberPlayers();
	public List<Item> getItems();
	
	/**
	 * there are 0 or more exits to a room
	 * @return the exits from this room
	 */
	public Set<Exit> getExits();
	public void addExit(Exit e);
	public Exit getExit(Direction dir);
	
}
