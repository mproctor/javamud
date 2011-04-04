package javamud.player;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javamud.server.PlayerMappingService;
import javamud.util.AbstractXmlFactory;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class DefaultXmlFilePlayerFactory extends AbstractXmlFactory implements PlayerFactory {

	private PlayerMappingService playerMappingService;
	private static final Logger logger = Logger.getLogger(DefaultXmlFilePlayerFactory.class);
	@Override
	public Map<String, Player> loadPlayers(Reader r) {
		try {
			List<Player> players =(List<Player>)digester.parse(r);
			
			Map<String,Player> pMap = new HashMap<String,Player>();
			for(Player p: players) {
				((SimplePlayer)p).setPlayerMappingService(playerMappingService);
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
	
	public void setPlayerMappingService(PlayerMappingService pms) {
		this.playerMappingService = pms;
	}

}
