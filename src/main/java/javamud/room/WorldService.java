package javamud.room;

import java.util.Set;

public interface WorldService {

	Room lookupRoom(int zId,int rId);
	Zone lookupZone(int zId);
	Set<Integer> loadZone(String zoneFileName);
	void dropZone(int zId);
	boolean isZoneLoaded(int currentZoneId);
	public Room getLimboRoom();
}
