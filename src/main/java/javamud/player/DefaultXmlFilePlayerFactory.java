package javamud.player;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javamud.room.WorldService;
import javamud.server.PlayerMappingService;
import javamud.util.AbstractXmlFactory;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class DefaultXmlFilePlayerFactory extends AbstractXmlFactory implements PlayerFactory<RemotePlayer> {
	private WorldService worldService;

	private PlayerMappingService playerMappingService;
	private PlayerService playerService;
	private static final Logger logger = Logger.getLogger(DefaultXmlFilePlayerFactory.class);
	
	
	public void setPlayerService(PlayerService playerService) {
		this.playerService = playerService;
	}

	public void setWorldService(WorldService worldService) {
		this.worldService = worldService;
	}

	@Override
	public Map<String, RemotePlayer> loadPlayers(Reader r) {
		try {
			List<RemotePlayer> players =(List<RemotePlayer>)digester.parse(r);
			
			Map<String,RemotePlayer> pMap = new HashMap<String,RemotePlayer>();
			for(RemotePlayer p: players) {
				SimpleRemotePlayer sp = (SimpleRemotePlayer)p;
				sp.setPlayerMappingService(playerMappingService);
				sp.setPlayerService(playerService);
				
				if (worldService.isZoneLoaded(sp.getCurrentZoneId())) {
					sp.setCurrentRoom(worldService.lookupRoom(sp.getCurrentZoneId(),sp.getCurrentRoomId()));
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
	
	/**
	 * creates an empty player and sets the mapping service
	 * caller will need to set any other fields (name/password/description)
	 */
	@Override
	public RemotePlayer createNewPlayer() {
		SimpleRemotePlayer p = new SimpleRemotePlayer();
		p.setPlayerMappingService(playerMappingService);
		
		return p;
	}
	
	public void setPlayerMappingService(PlayerMappingService pms) {
		this.playerMappingService = pms;
	}

	@Override
	public void resetCurrentRoom(Player p) {
		SimpleRemotePlayer sp = (SimpleRemotePlayer)p;
		sp.setCurrentRoom(worldService.lookupRoom(sp.getCurrentZoneId(),sp.getCurrentRoomId()));
	}

}
