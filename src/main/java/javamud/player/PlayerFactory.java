package javamud.player;

import java.io.Reader;
import java.util.List;

public interface PlayerFactory<T> {

	List<T> loadPlayers(Reader r);

}
