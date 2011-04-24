package javamud.player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class DefaultFileAutomatedPlayerService implements AutomatedPlayerService {
	private static final Logger logger = Logger.getLogger(DefaultFileAutomatedPlayerService.class);

	private PlayerFactory<AutomatedPlayer> playerFactory;
	private Map<String,AutomatedPlayer> playerDetails = new HashMap<String,AutomatedPlayer>();

	private String playerFileName;

	public void setPlayerFactory(PlayerFactory<AutomatedPlayer> playerFactory) {
		this.playerFactory = playerFactory;
	}
	public void setPlayerFileName(String playerFileName) {
		this.playerFileName = playerFileName;
	}

	public void init() {
		FileReader fr = null;
		try {
			fr = new FileReader(playerFileName);
			BufferedReader br = new BufferedReader(fr);
			
			playerDetails = playerFactory.loadPlayers(br);		
			
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
	
	@Override
	public AutomatedPlayer spawnNew(int plyrId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * when a zone is loaded, check that all the players
	 * for that zone have their rooms loaded
	 */
	@Override
	public void reloadAutomatedPlayersForZone(Integer zId) {
		for(Player p: playerDetails.values()) {
			if (p.getCurrentRoom() == null) {
				SimpleAutomatedPlayer ap = (SimpleAutomatedPlayer)p;
				if (zId.equals(ap.getCurrentZoneId())) {
					playerFactory.resetCurrentRoom(ap);
				}
			}
		}
	}

}
