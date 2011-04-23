package javamud.command;

import javamud.room.Exit.Direction;

public class DownCommand extends AbstractMoveCommand {
	
	public DownCommand() {
		super(Direction.Down);
	}
}
