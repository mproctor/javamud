package javamud.command;

import javamud.player.Player;
import javamud.room.WorldService;
import javamud.room.Room;

/**
 * repeat the string to the room
 * @author Matthew Proctor
 *
 */
public class SayCommand implements Command {

	private WorldService worldService;
	public void setWorldService(WorldService worldService) {
		this.worldService = worldService;
	}
	@Override
	public String execute(final Player speaker, final String s) {
		int rId = speaker.getCurrentRoomId();
		Room r = worldService.lookupRoom(rId);
		for (Player p:r.getPlayers() ) {
			if (p != speaker){
				p.hear(s);
			}
		}
		
		return "You say: \""+s+"\"";
	}

}
