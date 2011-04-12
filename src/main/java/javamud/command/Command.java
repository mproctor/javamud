package javamud.command;

import javamud.player.Player;

public interface Command {

	void execute(final Player p,final String s);
}
