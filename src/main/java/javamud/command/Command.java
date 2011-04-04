package javamud.command;

import javamud.player.Player;

public interface Command {

	String execute(final Player p,final String s);
}
