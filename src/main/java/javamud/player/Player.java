package javamud.player;


public interface Player {

	public String getName();
	public String getDescription();
	public int getId();
	public int getCurrentRoomId();
	
	public void hear(Player p,String s);
	public String getPassword();
}
