package javamud.player;

import javamud.server.PlayerMappingService;
import javamud.room.Room;

public class SimplePlayer implements Player {
	private String password,name,description;

	private PlayerService playerService;
	private PlayerMappingService playerMappingService;
	
	public void setPlayerService(PlayerService playerService) {
		this.playerService = playerService;
	}

	public void setPlayerMappingService(PlayerMappingService playerMappingService) {
		this.playerMappingService = playerMappingService;		
	}

	private int currentRoomId,currentZoneId;
	private Room currentRoom;
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * setters for commons digester
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCurrentRoomId(int currentRoomId) {
		this.currentRoomId = currentRoomId;
	}
	
	public void setCurrentZoneId(int currentZoneId) {
		this.currentZoneId = currentZoneId;
	}

	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Room getCurrentRoom() {
		// TODO Auto-generated method stub
		return currentRoom;
	}
	
	public void hear(Player spkr,String s) {
		// TODO: test for deafness
		// TODO: test for language?
		playerMappingService.sendString(this,s);
	}
	
	public void seeEvent(Player spkr,String s) {
		// TODO: test for blindness
		// TODO: test for invisibility
		playerMappingService.sendString(this,s);
	}

	@Override
	public String getPassword() {
		return password;
	}

	public int getCurrentRoomId() {
		return currentRoomId;
	}

	@Override
	public void sendResponse(String s) {
		playerMappingService.sendString(this,s);		
	}

	/**
	 * player has to run this command, args and all
	 */
	@Override
	public void forceCommand(String string) {
		playerService.runCommand(this, string);
	}

	public int getCurrentZoneId() {
		return currentZoneId;
	}

	@Override
	public void doLogout() {
		playerMappingService.disconnectPlayer(this);
	}

}
