package javamud.room;

public interface Zone {
	int getId();
	String getName();
	
	Room lookupRoom(int rId);

}
