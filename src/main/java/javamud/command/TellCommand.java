package javamud.command;

import javamud.player.Player;
import javamud.room.Room;

/**
 * repeat the string to the room
 * @author Matthew Proctor
 *
 */
public class TellCommand implements Command {

	public static final String SAY_WHAT = "What do you want to say?!";
	public static final String TELL_WHAT = "What do you want to say to them?!";
	public static final String TELL_SELF = "You tell yourself something that you forgot.";

	@Override
	public void execute(final Player speaker, final String s) {	
		if (s == null) {
			speaker.sendResponse(SAY_WHAT);
			return ;
		}
		
		String[] cmd = s.split(" +");
		Player target = Command.Util.findPlayerInRoom(speaker, cmd[0]);
		if (target == null) {
			speaker.sendResponse("You can't see that person.");
			return;
		} else if (target == speaker) {
			speaker.sendResponse(TELL_SELF);
		}
		else if (cmd.length < 2) {
			speaker.sendResponse(TELL_WHAT);
			return;
		}
		
		StringBuffer cmdBuf = new StringBuffer();
		for (int i=1;i<cmd.length;i++) {
			if (i==1) {				
				cmdBuf.append(cmd[i]);
			} else {
				cmdBuf.append(" ").append(cmd[i]);
			}
		}
		target.hear(speaker,speaker.getName()+" tells you \""+cmdBuf.toString()+"\"");
		
		speaker.sendResponse( "You tell "+target.getName()+": \""+cmdBuf.toString()+"\"");
	}

}
