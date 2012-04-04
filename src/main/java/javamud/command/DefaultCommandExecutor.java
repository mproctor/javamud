package javamud.command;

import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;

import javamud.player.Player;
import javamud.server.MudEngine;
import javamud.server.PlayerMappingService;

public class DefaultCommandExecutor implements CommandExecutor {
	private static final Logger logger = Logger.getLogger(DefaultCommandExecutor.class);
	
	private MudEngine mudEngine;

	private CommandParser commandParser;
	public void setMudEngine(MudEngine mudEngine) {
		this.mudEngine = mudEngine;
	}
	
	public void setCommandParser(CommandParser commandParser) {
		this.commandParser = commandParser;
	}
	@Override
	public void executeCommand(final Player p,String cmd) {
		
		if (logger.isDebugEnabled())
		logger.debug("Player "+p.getName()+" in room "+p.getCurrentRoom().getId()+" issued command: "+cmd);
		
		int firstSpace = cmd.indexOf(' ');
		try {
			final Command c = commandParser.parse(firstSpace==-1?cmd:cmd.substring(0,firstSpace));
			
			final String cmdArgs = firstSpace==-1?null:cmd.substring(firstSpace+1);
			
			mudEngine.submitPlayerCommand(p,c,cmdArgs);
			
		} catch(CommandException e) {
			p.sendResponse("Unknown command: "+e.getMessage()+"\n");
		}
	}

}
