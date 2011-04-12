package javamud.command;

import javamud.player.Player;
import javamud.room.Exit;
import javamud.room.Room;
import javamud.room.SimpleExit.Direction;

public abstract class AbstractMoveCommand implements Command {

	private static final String NO_SUCH_DIR="No exit in that direction\r\n";
	private Direction direction;
	public AbstractMoveCommand(Direction d) {
		this.direction = d;
	}
	
	public Direction getDirection() {
		return direction;
	}

	@Override
	public void execute(Player mover, String s) {
		Room r = mover.getCurrentRoom();
		
		Exit e = r.getExit(direction);
		if (e != null) {
			Room toRoom = e.getDestination();
			for (Player p:r.getPlayers() ) {
				if (p != mover){
					p.seeEvent(mover,mover.getName()+" heads "+direction+".");
				}
			}
			r.removePlayer(mover);			
			toRoom.addPlayer(mover);
			for (Player p:toRoom.getPlayers() ) {
				if (p != mover){
					p.seeEvent(mover,mover.getName()+" arrives from the "+Direction.oppositeDirection(direction)+"");
				}
			}
			
			mover.sendResponse("You head "+direction+".\r\n");
			mover.forceCommand("look");
		}
		else {
			mover.sendResponse(NO_SUCH_DIR);
		}
	}

}
