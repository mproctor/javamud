package javamud.player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javamud.player.LoginService;

import org.apache.log4j.Logger;

public class DefaultFilePlayerService implements PlayerService,LoginService {
	private static final Logger logger = Logger.getLogger(DefaultFilePlayerService.class);

	private PlayerFactory playerFactory;

	private Map<String,Player> playerDetails = new HashMap<String,Player>();

	private String playerFileName;

	public void setPlayerFileName(String playerFileName) {
		this.playerFileName = playerFileName;
	}

	@Override
	public Player loadPlayer(String pName) {
		return playerDetails.get(pName);
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
	public boolean verifyPassword(String pName, String s) {
		Player p = playerDetails.get(pName);
		if (p != null) {
			return p.getPassword().equals(s);
		}
		
		return false;
	}

	@Override
	public boolean playerExists(String s) {
		return playerDetails.containsKey(s);
	}

	@Override
	public void addUser(String name, String pword) {
		logger.info("adding new user "+name);
//		loginDetails.put(name, pword);	
		
		// flush new user to file
	}

	public void setPlayerFactory(PlayerFactory playerFactory) {
		this.playerFactory = playerFactory;
	}
}
