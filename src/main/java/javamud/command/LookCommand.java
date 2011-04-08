package javamud.command;

import java.util.Iterator;

import javamud.player.Player;
import javamud.room.*;

public class LookCommand implements Command {

	@Override
	public String execute(final Player plyr, final String s) {
		StringBuffer viewDesc = new StringBuffer();
		Room r = plyr.getCurrentRoom();
		viewDesc.append(r.getTitle()).append('\r').append('\n');
		viewDesc.append(r.getDescription()).append('\r').append('\n');
		
		Iterator exitIter = r.getExits().iterator();
		if (exitIter.hasNext()) {
			viewDesc.append("There are exits: ");
			while (exitIter.hasNext()) {
				Exit e = (Exit)exitIter.next();

				// TODO: test visibility of exit to this player
				viewDesc.append(e.getDirection().getDescription());

				if (exitIter.hasNext()) {
					viewDesc.append(",");
				}
			}
			viewDesc.append('\r').append('\n');
		}
		for (Player p:r.getPlayers() ) {
			if (p != plyr){
				// TODO: test visibility of other player to this one
				viewDesc.append(p.getName()).append('\r').append('\n');
			}
		}
		
		return viewDesc.toString();
	}

}
