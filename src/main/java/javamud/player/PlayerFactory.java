package javamud.player;

import java.io.Reader;
import java.util.Map;

public interface PlayerFactory<T> {
	
	Map<String,T> loadPlayers(Reader r);
	
	T createNewPlayer();

	void resetCurrentRoom(Player p);

}
