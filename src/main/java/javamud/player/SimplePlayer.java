package javamud.player;

import javamud.server.PlayerMappingService;
import javamud.room.Room;

public class SimplePlayer implements Player {
	private String password,name,description;

	private PlayerMappingService playerMappingService;
	public void setPlayerMappingService(PlayerMappingService playerMappingService) {
		this.playerMappingService = playerMappingService;
	}

	private int currentRoomId;
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

	@Override
	public String getPassword() {
		return password;
	}

	public int getCurrentRoomId() {
		return currentRoomId;
	}

}
