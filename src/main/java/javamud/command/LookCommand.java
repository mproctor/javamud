package javamud.command;

import java.util.Iterator;

import javamud.item.Item;
import javamud.player.Player;
import javamud.room.*;
import javamud.room.SimpleExit.Direction;

public class LookCommand implements Command {

	@Override
	public void execute(final Player plyr, final String s) {
		if (s == null) {
			lookAtRoom(plyr);
		} else {
			String[] cmd = s.split(" +");
			
			
			// look for a person first
			Player p = Command.Util.findPlayerInRoom(plyr,cmd[0]);
			
			if (p != null) {
				lookAtPlayer(plyr,p);
				return;
			}
			
			Item i = Command.Util.findItemInRoom(plyr,cmd[0]);
			
			if (i != null) {
				lookAtItem(plyr, i);
				return;
			}
			
			// look direction e.g. look east
			
			Direction d = Direction.lookupDir(cmd[0]);
				
			if (d != null) {
				lookAtDirection(plyr,d);
				return;
			}
			
			// perhaps the direction has a description e.g. look 2.trapdoor
			Exit e = Command.Util.findExitInRoom(plyr,cmd[0]);
			if (e != null) {
				lookAtExit(plyr,e);
				return;
			}
			
			
			plyr.sendResponse("You don't see that here.");
			
		}
	}
	
	private void lookAtPlayer(Player plyr,Player target) {
		if (plyr == target) {
			plyr.sendResponse("You admire yourself for a few moments...");
		} else {
			plyr.sendResponse(target.getDescription());
		}
	}
	
	private void lookAtItem(Player plyr,Item target) {
		plyr.sendResponse(target.getDescription());
	}
	
	private void lookAtDirection(Player plyr,Direction dir) {
		Room r = plyr.getCurrentRoom();
		
		Exit e = r.getExit(dir);
			
		lookAtExit(plyr,e);
	}
	
	private void lookAtExit(Player plyr,Exit e) {
		if (e != null) {
			plyr.sendResponse(e.getDescription());
		}
	}
	
	
	private void lookAtRoom(Player plyr) {
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
		
		plyr.sendResponse(viewDesc.toString());
	}

}
