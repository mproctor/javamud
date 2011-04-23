package javamud.player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javamud.command.CommandExecutor;

import org.apache.log4j.Logger;

public class DefaultFileRemotePlayerService implements PlayerService,LoginService {
	private static final Logger logger = Logger.getLogger(DefaultFileRemotePlayerService.class);

	private CommandExecutor commandExecutor;

	private PlayerFactory<RemotePlayer> playerFactory;
	private Map<String,RemotePlayer> playerDetails = new HashMap<String,RemotePlayer>();

	private String playerFileName;

	public void setCommandExecutor(CommandExecutor commandExecutor) {
		this.commandExecutor = commandExecutor;
	}

	public void setPlayerFactory(PlayerFactory<RemotePlayer> playerFactory) {
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
	public Player loadPlayer(String pName) {
		Player p= playerDetails.get(pName);
		if (p.getCurrentRoom() == null ) {
			playerFactory.resetCurrentRoom(p);
		}
		
		return p;
	}

	@Override
	public boolean verifyPassword(String pName, String s) {
		RemotePlayer p = playerDetails.get(pName);
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

	@Override
	public void runCommand(Player p, String s) {
		commandExecutor.executeCommand(p, s);		
	}

}
