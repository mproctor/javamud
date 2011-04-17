package javamud.command;

import javamud.player.Player;

public class ExitCommand implements Command {

	@Override
	public void execute(Player p, String s) {
		p.doLogout();		
	}

}
