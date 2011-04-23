package javamud.room;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
	
	private Room getLimboRoom() {
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
	public void loadZone(String zoneFileName) {
		FileReader fr = null;
		try {
			fr = new FileReader(zoneFileName);
			BufferedReader br = new BufferedReader(fr);
			worldMappings.putAll(worldFactory.loadWorld(br));
			logger.info("Loaded from zone file: "+zoneFileName);
		} catch(IOException ie) {
			logger.error("Unable to read zone file "+zoneFileName+": "+ie.getMessage(),ie);
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
	
	


}
