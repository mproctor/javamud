package javamud.command;

import java.util.concurrent.ExecutorService;

import javamud.player.Player;
import javamud.server.PlayerMappingService;

public class DefaultCommandExecutor implements CommandExecutor {
	private PlayerMappingService playerMappingService;
	
	private ExecutorService executorService;

	private CommandParser commandParser;
	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
	public void setPlayerMappingService(PlayerMappingService pms) {
		this.playerMappingService = pms;
	}
	
	public void setCommandParser(CommandParser commandParser) {
		this.commandParser = commandParser;
	}
	@Override
	public void executeCommand(String pName,String cmd) {
		final Player p = playerMappingService.getPlayerByName(pName);
		
		int firstSpace = cmd.indexOf(' ');
		try {
			final Command c = commandParser.parse(cmd.substring(0,firstSpace));
			
			final String cmdArgs = cmd.substring(firstSpace);
			executorService.submit(new Runnable() {
				
				//TODO: this only allows a command to return a single string
				
				@Override
				public void run() {
					playerMappingService.sendString(p,c.execute(p, cmdArgs ));					
				}
			});
			
		} catch(CommandException e) {
			playerMappingService.sendString(p,  "Unknown command: "+e.getMessage());
		}
	}

}
