package javamud.command;

import javamud.player.Player;
import javamud.room.Room;

/**
 * repeat the string to the room
 * @author Matthew Proctor
 *
 */
public class SayCommand implements Command {

	@Override
	public String execute(final Player speaker, final String s) {		
		Room r = speaker.getCurrentRoom();
		for (Player p:r.getPlayers() ) {
			if (p != speaker){
				p.hear(speaker,speaker.getName()+" says \""+s+"\"");
			}
		}
		
		return "You say: \""+s+"\"";
	}

}
