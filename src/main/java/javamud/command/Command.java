package javamud.command;

import javamud.item.Item;
import javamud.player.Player;
import javamud.room.Exit;
import javamud.room.Room;

public interface Command {

	void execute(final Player p,final String s);
	
	class Util {
		public static Player findPlayerInRoom(Player looker,String target) {
			String[] numTargets = target.split("\\.");
			
			int itemNo=1;
			if (numTargets.length == 2) {
				itemNo = Integer.parseInt(numTargets[0]);
			}
			
			String name = numTargets[numTargets.length -1];
	
			for(Player p: looker.getCurrentRoom().getPlayers()) {
				
				// TODO: visibility check, can looker see p?
				if (p.getName().equals(name)) {
					itemNo--;
					if (itemNo == 0) {
						return p;
					}
				}
			}
			
			return null;
		}
		
		public static Item findItemInRoom(Player looker,String target) {
			String[] numTargets = target.split("\\.");
			
			int itemNo=1;
			if (numTargets.length == 2) {
				itemNo = Integer.parseInt(numTargets[0]);
			}
			
			String name = numTargets[numTargets.length -1];
			if (looker.getCurrentRoom().getItems() != null) {
				for (Item i: looker.getCurrentRoom().getItems()) {
					// TODO: visibility check, can looker see i?
					if (i.getName().equals(name)) {
						itemNo--;
						if (itemNo == 0) {
							return i;
						}
					}
				}
			}
			
			return null;
		}	
		
		public static Exit findExitInRoom(Player looker,String target) {
			String[] numTargets = target.split("\\.");
			
			int itemNo=1;
			if (numTargets.length == 2) {
				itemNo = Integer.parseInt(numTargets[0]);
			}
			
			String descr = numTargets[numTargets.length -1];
			
			for (Exit e: looker.getCurrentRoom().getExits()) {
				// TODO: visibility check, hidden exit?
				if (e.getKeywords().contains(descr)) {
					itemNo--;
					if (itemNo ==0) {
						return e;
					}
				}
			}
			
			return null;
		}
	}
}
