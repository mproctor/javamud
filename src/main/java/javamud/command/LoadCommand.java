package javamud.command;

import java.util.Set;

import javamud.player.AutomatedPlayerService;
import javamud.player.Player;
import javamud.room.WorldService;

/**
 * Load zone or player file
 * @author Matthew Proctor
 *
 */
public class LoadCommand implements Command {
	
	private WorldService worldService;
	private AutomatedPlayerService automatedPlayerService;
	public void setAutomatedPlayerService(
			AutomatedPlayerService automatedPlayerService) {
		this.automatedPlayerService = automatedPlayerService;
	}

	public void setWorldService(WorldService worldService) {
		this.worldService = worldService;
	}

	@Override
	public void execute(Player p, String s) {
		String[] cmd = s.split(" +");
		if ("zone".equals(cmd[0].toLowerCase())) {
			Set<Integer> zoneIds = worldService.loadZone(cmd[1]);
			for(Integer i: zoneIds) {
				
			}
		}
	}

}
