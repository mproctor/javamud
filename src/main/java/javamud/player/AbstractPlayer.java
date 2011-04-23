package javamud.player;

import javamud.room.Room;

public abstract class AbstractPlayer implements Player {
	private String name,description;

	protected PlayerService playerService;
	
	
	public void setPlayerService(PlayerService playerService) {
		this.playerService = playerService;
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

	public int getCurrentRoomId() {
		return currentRoomId;
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
}
