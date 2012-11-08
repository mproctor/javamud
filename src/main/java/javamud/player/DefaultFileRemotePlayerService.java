package javamud.player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javamud.command.CommandExecutor;
import javamud.room.Room;
import javamud.room.WorldService;
import javamud.server.PlayerMappingService;

import org.apache.log4j.Logger;

public class DefaultFileRemotePlayerService implements PlayerService,
		LoginService {
	private static final Logger logger = Logger
			.getLogger(DefaultFileRemotePlayerService.class);
	private WorldService worldService;
	private PlayerMappingService playerMappingService;

	public void setPlayerMappingService(
			PlayerMappingService playerMappingService) {
		this.playerMappingService = playerMappingService;
	}

	public void setWorldService(WorldService worldService) {
		this.worldService = worldService;
	}

	private CommandExecutor commandExecutor;

	private PlayerFactory<RemotePlayer> playerFactory;

	// if the parser is unable to set this, we create an empty map
	private Map<String, RemotePlayer> playerDetails = new HashMap<String, RemotePlayer>();

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

			List<RemotePlayer> players = playerFactory.loadPlayers(br);
			for (RemotePlayer p : players) {
				SimpleRemotePlayer sp = (SimpleRemotePlayer) p;
				sp.setPlayerMappingService(playerMappingService);
				sp.setPlayerService(this);

				if (worldService.isZoneLoaded(sp.getCurrentZoneId())) {
					sp.setCurrentRoom(worldService.lookupRoom(
							sp.getCurrentZoneId(), sp.getCurrentRoomId()));
				}
				playerDetails.put(p.getName(), p);
			}

		} catch (IOException ie) {
			logger.error(
					"Unable to read player login file: " + ie.getMessage(), ie);
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error(
							"problem while closing player file: "
									+ e.getMessage(), e);
				}
			}
		}

		if (playerDetails == null) {
			logger.warn("No player information parsed from " + playerFileName);
			playerDetails = new HashMap<String, RemotePlayer>();
		}
	}

	@Override
	public Player loadPlayer(String pName) {
		Player p = playerDetails.get(pName);
		if (p.getCurrentRoom() == null) {
			resetCurrentRoom(p);
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
	public void addNewPlayer(String name, String pword) {
		logger.info("adding new player " + name);
		// loginDetails.put(name, pword);
		SimpleRemotePlayer p = new SimpleRemotePlayer();
		p.setPlayerMappingService(playerMappingService);
		p.setName(name);
		p.setPassword(pword);
		p.setDescription("The fresh faced appearance of someone who has yet to be killed by a rabbit");
		playerDetails.put(name, p);
		// flush new user to file
	}

	@Override
	public void runCommand(Player p, String s) {
		commandExecutor.executeCommand(p, s);
	}

	@Override
	public void resetCurrentRoom(Player p) {
		SimpleRemotePlayer sp = (SimpleRemotePlayer) p;
		Room r = worldService.lookupRoom(sp.getCurrentZoneId(),
				sp.getCurrentRoomId());
		sp.setCurrentRoom(r);
		r.addPlayer(sp);
	}
}
