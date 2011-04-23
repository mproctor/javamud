package javamud.command;

import javamud.player.Player;
import javamud.player.RemotePlayer;

public class ExitCommand implements Command {

	@Override
	public void execute(Player p, String s) {
		if (p instanceof RemotePlayer) {
			((RemotePlayer)p).doLogout();
		}
	}

}
