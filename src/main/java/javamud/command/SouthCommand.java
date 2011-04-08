package javamud.command;

import javamud.room.SimpleExit.Direction;

public class SouthCommand extends AbstractMoveCommand {
	
	public SouthCommand() {
		super(Direction.South);
	}
}
