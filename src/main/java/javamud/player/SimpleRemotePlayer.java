package javamud.player;

import javamud.server.PlayerMappingService;

public class SimpleRemotePlayer extends AbstractPlayer implements RemotePlayer {

	String password;
	private PlayerMappingService playerMappingService;
	public void setPlayerMappingService(PlayerMappingService playerMappingService) {
		this.playerMappingService = playerMappingService;		
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public void doLogout() {
		playerMappingService.disconnectPlayer(this);
	}
	
	@Override
	public void sendResponse(String s) {
		playerMappingService.sendString(this,s);		
	}
	
	@Override
	public void hear(Player spkr,String s) {
		// TODO: test for deafness
		// TODO: test for language?
		playerMappingService.sendString(this,s);
	}
	
	@Override
	public void seeEvent(Player spkr,String s) {
		// TODO: test for blindness
		// TODO: test for invisibility
		playerMappingService.sendString(this,s);
	}
}
