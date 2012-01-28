package javamud.room;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javamud.util.AbstractXmlFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class DefaultXmlFileWorldFactory extends AbstractXmlFactory implements
		WorldFactory {

	private static final Logger logger = Logger
			.getLogger(DefaultXmlFileWorldFactory.class);

	@Override
	public Map<Integer, Zone> loadWorld(Reader r) {
		try {
			Set<Zone> zones = (Set<Zone>) digester.parse(r);

			Map<Integer, Zone> zMap = new HashMap<Integer, Zone>();
			for (Zone zone : zones) {
				zMap.put(zone.getId(), zone);
				
				List<Room> rooms = ((SimpleZone)zone).getRooms();
				Map<Integer,Room> rMap = new HashMap<Integer,Room>();
				for(Room room: rooms) {
					rMap.put(room.getId(), room);
					((SimpleRoom)room).setZone(zone);
				}
				
				((SimpleZone)zone).setRMap(rMap);
				
			}

			linkExits(zones, zMap);

			return zMap;
		} catch (SAXException se) {
			logger.error("Error parsing the world file: " + se.getMessage(), se);
		} catch (IOException ie) {
			logger.error("Error parsing the world file: " + ie.getMessage(), ie);
		}

		return null;

	}

	/**
	 * the xml file only sets the room Id, this will link the rooms together to
	 * save having to do a lookup all the time
	 * 
	 * @param rooms
	 * @param rIdMap
	 */
	public void linkExits(Set<Zone> zones, Map<Integer, Zone> zIdMap) {
		for (Zone zone : zones) {
			for (Room r : ((SimpleZone) zone).getRooms()) {
				for (Exit e : r.getExits()) {
					SimpleExit se = (SimpleExit) e;
					
					// if zoneid is not set, its the current zone
					Zone z = se.getToZoneId()==Zone.ZONEID_NULL?zone:zIdMap.get(se.getToZoneId());
					se.setDestination(z.lookupRoom(se.getToRoomId()));
				}
			}
		}
	}

}
