package javamud.command;

import javamud.room.Exit.Direction;

public class EastCommand extends AbstractMoveCommand {
	
	public EastCommand() {
		super(Direction.East);
	}
}
