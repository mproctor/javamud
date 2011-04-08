package javamud.command;

import javamud.room.SimpleExit.Direction;

public class NorthCommand extends AbstractMoveCommand {
	
	public NorthCommand() {
		super(Direction.North);
	}
}
