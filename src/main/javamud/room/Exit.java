package javamud.room;

import javamud.item.Item;
import javamud.room.SimpleExit.Direction;

public interface Exit {
	public  boolean isKeyForExit(Item key);

	public  String getTitle();

	public  String getDescription();

	public  boolean isOpen();

	public  boolean isClosed();

	public  boolean isLocked();

	public  boolean isLockable();

	public  boolean isPickable();

	public Room getDestination();
	public Direction getDirection();

}
