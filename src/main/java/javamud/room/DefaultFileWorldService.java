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
	
	private Map<Integer,Room> worldMappings = new HashMap<Integer,Room>();
	public void setWorldFileName(String worldFileName) {
		this.worldFileName = worldFileName;
	}
	@Override
	public Room lookupRoom(int rId) {
		return worldMappings.get(rId);
	}
	
	public void init() {
		FileReader fr = null;
		try {
			fr = new FileReader(worldFileName);
			BufferedReader br = new BufferedReader(fr);
			
			worldMappings = worldFactory.loadWorld(br);
			
		} catch(IOException ie) {
			logger.error("Unable to read player login file: "+ie.getMessage(),ie);
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("problem while closing player file: "+e.getMessage(),e);
				}
			}
		}
	}


}
