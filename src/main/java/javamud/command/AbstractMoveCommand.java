package javamud.command;

import javamud.player.Player;
import javamud.room.Exit;
import javamud.room.Room;
import javamud.room.SimpleExit.Direction;

public abstract class AbstractMoveCommand implements Command {

	private static final String NO_SUCH_DIR="No exit in that direction";
	private Direction direction;
	public AbstractMoveCommand(Direction d) {
		this.direction = d;
	}
	
	public Direction getDirection() {
		return direction;
	}

	@Override
	public  String execute(Player p, String s) {
		Room r = p.getCurrentRoom();
		
		Exit e = r.getExit(direction);
		if (e != null) {
			Room toRoom = e.getDestination();
			r.removePlayer(p);			
			toRoom.addPlayer(p);
		}
		
		return NO_SUCH_DIR ;
	}

}
