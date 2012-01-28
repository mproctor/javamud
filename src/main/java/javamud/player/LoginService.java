package javamud.player;

public interface LoginService {

	boolean verifyPassword(String pName, String s);

	boolean playerExists(String s);

	void addNewPlayer(String name, String pword);	
}