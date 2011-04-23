package javamud.command;

import javamud.room.Exit.Direction;

public class NorthCommand extends AbstractMoveCommand {
	
	public NorthCommand() {
		super(Direction.North);
	}
}
