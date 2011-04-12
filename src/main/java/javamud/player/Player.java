package javamud.player;

import javamud.room.Room;

public interface Player {

	public String getName();
	public String getDescription();
	public int getId();
	public Room getCurrentRoom();
	
	public void hear(Player p,String s);
	public void seeEvent(Player p,String s);
	public String getPassword();
	public void sendResponse(String string);
	public void forceCommand(String string);
}
