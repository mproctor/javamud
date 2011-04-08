package javamud.command;

import javamud.room.SimpleExit.Direction;

public class DownCommand extends AbstractMoveCommand {
	
	public DownCommand() {
		super(Direction.Down);
	}
}
