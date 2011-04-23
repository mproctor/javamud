package javamud.command;

import javamud.room.Exit.Direction;

public class UpCommand extends AbstractMoveCommand {
	
	public UpCommand() {
		super(Direction.Up);
	}
}
