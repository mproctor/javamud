package javamud.command;

import javamud.player.Player;
public interface CommandExecutor {
	
	public void executeCommand(Player p,String s);

}
