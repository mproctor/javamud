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
import javamud.routine.Routine;
import javamud.routine.RoutineService;
import javamud.server.MudEngine;

import org.apache.log4j.Logger;

public class DefaultFileAutomatedPlayerService implements
		AutomatedPlayerService {
	private static final Logger logger = Logger
			.getLogger(DefaultFileAutomatedPlayerService.class);

	private RoutineService routineService;
	private WorldService worldService;
	private MudEngine mudEngine;
	private CommandExecutor commandExecutor;

	public void setRoutineService(RoutineService routineService) {
		this.routineService = routineService;
	}

	public void setMudEngine(MudEngine mudEngine) {
		this.mudEngine = mudEngine;
	}

	public void setWorldService(WorldService worldService) {
		this.worldService = worldService;
	}

	public void setCommandExecutor(CommandExecutor commandExecutor) {
		this.commandExecutor = commandExecutor;
	}

	private PlayerFactory<AutomatedPlayer> playerFactory;
	private Map<String, AutomatedPlayer> playerDetails = new HashMap<String, AutomatedPlayer>();

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

			List<AutomatedPlayer> players = playerFactory.loadPlayers(br);
			for (AutomatedPlayer p : players) {
				SimpleAutomatedPlayer sp = (SimpleAutomatedPlayer) p;
				sp.setPlayerService(this);

				if (worldService.isZoneLoaded(sp.getCurrentZoneId())) {
					Room room = worldService.lookupRoom(sp.getCurrentZoneId(),
							sp.getCurrentRoomId());
					sp.setCurrentRoom(room);
					room.addPlayer(sp);
				} else {
					logger.warn("Attempt to load automated player "
							+ sp.getId() + " when zone "
							+ sp.getCurrentZoneId() + " is not loaded");
				}
				p.initRoutines(routineService);
				playerDetails.put(p.getName(), p);

				if (p.hasRoutine()) {
					logger.info("Registering routines for " + p.getName());
					// if an auto-player has a routine, register with the server
					for (Map.Entry<String, Routine> routines : p
							.getAllRoutines().entrySet()) {

						// lookup the name of the routine
						Routine routine = routineService
								.getNamedRoutine(routines.getKey());

						// register this routine to this player on the scheduler
						mudEngine.registerRoutine(p, routine);
					}
				} else {
					logger.debug("No routines to register for " + p.getName());
				}
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

	}

	@Override
	public AutomatedPlayer spawnNew(int plyrId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"AutomatedPlayerService.spawnNew() not implemented");
	}

	/**
	 * when a zone is loaded, check that all the players for that zone have
	 * their rooms loaded
	 */
	@Override
	public void reloadAutomatedPlayersForZone(Integer zId) {
		for (Player p : playerDetails.values()) {
			if (p.getCurrentRoom() == null) {
				SimpleAutomatedPlayer ap = (SimpleAutomatedPlayer) p;
				if (zId.equals(ap.getCurrentZoneId())) {
					resetCurrentRoom(ap);
				}
			}
		}
	}

	@Override
	public Player loadPlayer(String pName) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"AutomatedPlayerService.loadPlayer() not implemented");
	}

	@Override
	public void runCommand(Player p, String s) {
		logger.debug(p.getName() + " running command " + s);
		commandExecutor.executeCommand(p, s);
	}

	@Override
	public void resetCurrentRoom(Player p) {
		SimpleAutomatedPlayer sp = (SimpleAutomatedPlayer) p;
		Room r = worldService.lookupRoom(sp.getCurrentZoneId(),
				sp.getCurrentRoomId());
		sp.setCurrentRoom(r);
		r.addPlayer(sp);
	}
}
