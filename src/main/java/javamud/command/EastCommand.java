package javamud.command;

import javamud.room.SimpleExit.Direction;

public class EastCommand extends AbstractMoveCommand {
	
	public EastCommand() {
		super(Direction.East);
	}
}
