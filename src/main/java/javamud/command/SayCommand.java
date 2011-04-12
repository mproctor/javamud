package javamud.command;

import javamud.player.Player;
import javamud.room.Room;

/**
 * repeat the string to the room
 * @author Matthew Proctor
 *
 */
public class SayCommand implements Command {

	public static final String SAY_WHAT = "What do you want to say?!";
	@Override
	public void execute(final Player speaker, final String s) {	
		if (s == null) {
			speaker.sendResponse(SAY_WHAT);
			return ;
		}
		Room r = speaker.getCurrentRoom();
		for (Player p:r.getPlayers() ) {
			if (p != speaker){
				p.hear(speaker,speaker.getName()+" says \""+s+"\"");
			}
		}
		
		speaker.sendResponse( "You say: \""+s+"\"");
	}

}
