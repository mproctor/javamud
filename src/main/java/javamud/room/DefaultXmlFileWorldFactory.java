package javamud.room;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javamud.util.AbstractXmlFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class DefaultXmlFileWorldFactory extends AbstractXmlFactory implements WorldFactory {

	private static final Logger logger = Logger.getLogger(DefaultXmlFileWorldFactory.class);
	@Override
	public Map<Integer, Room> loadWorld(Reader r) {
		try {
			List<Room> rooms =(List<Room>)digester.parse(r);
			
			Map<Integer,Room> rMap = new HashMap<Integer,Room>();
			for(Room room: rooms) {
				rMap.put(room.getId(), room);
			}
			
			linkExits(rooms,rMap);
			
			return rMap;
		} catch (SAXException se) {
			logger.error("Error parsing the world file: "+se.getMessage(),se);
		} catch (IOException ie) {
			logger.error("Error parsing the world file: "+ie.getMessage(),ie);
		}
		
		return null;

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
				se.setDestination(rIdMap.get(se.getToRoomId()));				
			}
		}
	}

}
