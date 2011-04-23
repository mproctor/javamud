package javamud.command;

import javamud.room.Exit.Direction;

public class SouthCommand extends AbstractMoveCommand {
	
	public SouthCommand() {
		super(Direction.South);
	}
}
