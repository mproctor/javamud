package javamud.room;

import java.util.List;
import java.util.Map;

public class SimpleZone implements Zone {
	
	private int id=Zone.ZONEID_NULL;
	private String name;
	private List<Room> rooms;
	private Map<Integer,Room> rMap;

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	
	public void setRMap(Map<Integer,Room> rm) {
		rMap = rm;
	}
	
	public Map<Integer,Room> getRMap() {
		return rMap;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Room lookupRoom(int rId) {
		return rMap.get(rId);
	}

}
