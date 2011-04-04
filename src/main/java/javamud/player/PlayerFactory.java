package javamud.player;

import java.io.Reader;
import java.util.Map;

public interface PlayerFactory {
	
	Map<String,Player> loadPlayers(Reader r);

}
