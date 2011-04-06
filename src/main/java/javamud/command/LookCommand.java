package javamud.command;

import javamud.player.Player;
import javamud.room.Room;
import javamud.room.WorldService;

public class LookCommand implements Command {

	private WorldService worldService;
	public void setWorldService(WorldService worldService) {
		this.worldService = worldService;
	}
	@Override
	public String execute(final Player speaker, final String s) {
		StringBuffer viewDesc = new StringBuffer();
		int rId = speaker.getCurrentRoomId();
		Room r = worldService.lookupRoom(rId);
		viewDesc.append(r.getTitle()).append('\r').append('\n');
		viewDesc.append(r.getDescription()).append('\r').append('\n');
		for (Player p:r.getPlayers() ) {
			if (p != speaker){
				viewDesc.append(p.getName()).append('\r').append('\n');
			}
		}
		
		return viewDesc.toString();
	}

}
