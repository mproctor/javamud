package javamud.room;

import java.io.Reader;
import java.util.Map;

public interface WorldFactory {

	Map<Integer, Zone> loadWorld(Reader r);

}
