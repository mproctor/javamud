package javamud.player;

import javamud.room.Room;

public interface Player {

	public String getName();
	public String getDescription();
	public int getId();
	public int getCurrentRoomId();
	
	public void hear(String s);
	public String getPassword();
}
