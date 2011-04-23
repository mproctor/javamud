package javamud.command;

import javamud.room.Exit.Direction;

public class WestCommand extends AbstractMoveCommand {
	
	public WestCommand() {
		super(Direction.West);
	}
}
