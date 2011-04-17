package javamud.command;

import javamud.player.Player;
import javamud.room.WorldService;

/**
 * Load zone or player file
 * @author Matthew Proctor
 *
 */
public class LoadCommand implements Command {
	
	private WorldService worldService;

	public void setWorldService(WorldService worldService) {
		this.worldService = worldService;
	}

	@Override
	public void execute(Player p, String s) {
		String[] cmd = s.split(" +");
		if ("zone".equals(cmd[0].toLowerCase())) {
			worldService.loadZone(cmd[1]);
		}
	}

}
