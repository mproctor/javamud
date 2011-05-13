package javamud.room;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javamud.player.AutomatedPlayerService;

import org.apache.log4j.Logger;

public class DefaultFileWorldService implements WorldService {
	public void setWorldFactory(WorldFactory worldFactory) {
		this.worldFactory = worldFactory;
	}

	private static final Logger logger = Logger.getLogger(DefaultFileWorldService.class);

	private WorldFactory worldFactory;
	private String worldFileName;
	

	private Map<Integer,Zone> worldMappings = new HashMap<Integer,Zone>();
	public void setWorldFileName(String worldFileName) {
		this.worldFileName = worldFileName;
	}
	@Override
	public Zone lookupZone(int zId) {
		return worldMappings.get(zId);
	}
	@Override
	public Room lookupRoom(int zId,int rId) {
		Zone z = worldMappings.get(zId);
		if (z== null) {
			return getLimboRoom();
		}
		
		Room r = z.lookupRoom(rId);
		
		if (r == null) {
			return getLimboRoom(); 
		}
		
		return r;
	}
	
	public Room getLimboRoom() {
		return worldMappings.get(-1).lookupRoom(-1);
	}
	
	public void init() {
		FileReader fr = null;
		try {
			fr = new FileReader(worldFileName);
			BufferedReader br = new BufferedReader(fr);
			
			worldMappings = worldFactory.loadWorld(br);
			
		} catch(IOException ie) {
			logger.error("Unable to read world file: "+ie.getMessage(),ie);
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("problem while closing world file: "+e.getMessage(),e);
				}
			}
		}
	}
	@Override
	public Set<Integer> loadZone(String zoneFileName) {
		FileReader fr = null;
		Map<Integer,Zone> newZones = null;
		try {
			fr = new FileReader(zoneFileName);
			BufferedReader br = new BufferedReader(fr);
			newZones = worldFactory.loadWorld(br);
			
			for(Zone z: newZones.values()) {
				linkExits(((SimpleZone)z).getRooms(),((SimpleZone)z).getRMap());
			}
			
			worldMappings.putAll(newZones);
			
			logger.info("Loaded from zone file: "+zoneFileName);
			
		} catch(IOException ie) {
			logger.error("Unable to read zone file "+zoneFileName+": "+ie.getMessage(),ie);
			newZones=null;
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("problem while closing zone file: "+e.getMessage(),e);
				}
			}
		}
		return newZones==null?null:newZones.keySet();
	}
	@Override
	public void dropZone(int zId) {		
		// TODO: look for people in the zone, and do something about it
		// TODO: abort if people exist?
		// TODO: relink rooms from other zones that used to link into here
		logger.info("Dropping zone: "+worldMappings.get(zId).getName()+" ("+zId+")");
		worldMappings.remove(zId);

	}
	@Override
	public boolean isZoneLoaded(int currentZoneId) {
		return worldMappings.containsKey(currentZoneId);
	}
	
	/**
	 * the xml file only sets the room Id, this will link the
	 * rooms together to save having to do a lookup all the time
	 * @param rooms
	 * @param rIdMap
	 */
	public void linkExits(List<Room> rooms,Map<Integer,Room> rIdMap) {
		for(Room r: rooms) {
			for (Exit e: r.getExits()) {
				SimpleExit se = (SimpleExit)e;
				
				if (se.getToZoneId() == Integer.MIN_VALUE) {
					se.setDestination(rIdMap.get(se.getToRoomId()));
				} else {
					// we need to lookup the room in a different Zone
					SimpleZone remoteZone = (SimpleZone) worldMappings.get(se.getToZoneId());
					if (remoteZone == null) {
						logger.error("Non-existant Zone specified as destination for room: "+r);
					} else {
						se.setDestination(remoteZone.getRMap().get(se.getToRoomId()));
					}
					
				}
			}
		}
	}


}
