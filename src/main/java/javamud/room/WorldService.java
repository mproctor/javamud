package javamud.room;

public interface WorldService {

	Room lookupRoom(int zId,int rId);
	Zone lookupZone(int zId);

}
