package javamud.player;

public interface PlayerService {

	Player loadPlayer(String pName);
	
	void runCommand(Player p,String s);

}
