package javamud.command;

import javamud.room.SimpleExit.Direction;

public class UpCommand extends AbstractMoveCommand {
	
	public UpCommand() {
		super(Direction.Up);
	}
}
