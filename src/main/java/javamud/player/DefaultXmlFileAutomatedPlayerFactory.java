package javamud.player;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javamud.room.Room;
import javamud.room.WorldService;
import javamud.util.AbstractXmlFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.support.SimpleAutowireCandidateResolver;
import org.xml.sax.SAXException;

public class DefaultXmlFileAutomatedPlayerFactory extends AbstractXmlFactory implements PlayerFactory<AutomatedPlayer> {
	private WorldService worldService;

	private PlayerService playerService;
	private static final Logger logger = Logger.getLogger(DefaultXmlFileAutomatedPlayerFactory.class);
	
	
	public void setPlayerService(PlayerService playerService) {
		this.playerService = playerService;
	}

	public void setWorldService(WorldService worldService) {
		this.worldService = worldService;
	}

	@Override
	public Map<String, AutomatedPlayer> loadPlayers(Reader r) {
		try {
			List<AutomatedPlayer> players =(List<AutomatedPlayer>)digester.parse(r);
			
			Map<String,AutomatedPlayer> pMap = new HashMap<String,AutomatedPlayer>();
			for(AutomatedPlayer p: players) {
				SimpleAutomatedPlayer sp = (SimpleAutomatedPlayer)p;
				sp.setPlayerService(playerService);
				
				if (worldService.isZoneLoaded(sp.getCurrentZoneId())) {
					Room room = worldService.lookupRoom(sp.getCurrentZoneId(),sp.getCurrentRoomId());
					sp.setCurrentRoom(room);
					room.addPlayer(sp);
				} else {
					logger.warn("Attempt to load automated player "+sp.getId()+" when zone "+sp.getCurrentZoneId()+" is not loaded");
				}
				pMap.put(p.getName(), p);
			}
			
			return pMap;
		} catch (SAXException se) {
			logger.error("Error parsing the player file: "+se.getMessage(),se);
		} catch (IOException ie) {
			logger.error("Error parsing the player file: "+ie.getMessage(),ie);
		}
		
		return null;
	}

	@Override
	public void resetCurrentRoom(Player p) {
		SimpleAutomatedPlayer sp = (SimpleAutomatedPlayer)p;
		Room r =worldService.lookupRoom(sp.getCurrentZoneId(),sp.getCurrentRoomId());
		sp.setCurrentRoom(r);
		r.addPlayer(sp);
	}
}
