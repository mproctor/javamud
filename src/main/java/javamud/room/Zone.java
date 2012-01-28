package javamud.room;

public interface Zone {
	public static int ZONEID_NULL=Integer.MIN_VALUE;
	int getId();
	String getName();
	
	Room lookupRoom(int rId);

}
