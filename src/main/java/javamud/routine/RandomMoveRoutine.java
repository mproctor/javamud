package javamud.routine;

import javamud.player.Player;
import javamud.room.Exit;
import javamud.room.Room;
import javamud.server.MudTime;
import javamud.server.MudTime.Unit;
import javamud.util.Dice;

import org.apache.log4j.Logger;

public class RandomMoveRoutine extends SimpleRoutine {
	private static final Logger logger = Logger
			.getLogger(RandomMoveRoutine.class);
	private Dice dice = new Dice(1, 10);

	@Override
	public void execute(Player p) {
		// pick an exit from any available
		Room r = p.getCurrentRoom();
		for (Exit e : r.getExits()) {
			if (dice.roll() > 8) {
				switch (e.getDirection()) {
				case North:
					p.forceCommand("north");
					break;
				case South:
					p.forceCommand("south");
					break;
				case East:
					p.forceCommand("east");
					break;
				case West:
					p.forceCommand("west");
					break;
				case Up:
					p.forceCommand("up");
					break;
				case Down:
					p.forceCommand("down");
					break;

				default:
					logger.warn("unknown exit direction for move routine for "
							+ p.getName() + " (" + e.getDirection() + ")");
				}
				break;
			}
		}

	}

	/**
	 * we will just keep running this one forever
	 */
	@Override
	public boolean hasNext() {
		return true;
	}

	/**
	 * every 100 ticks is fairly low key.. TODO: get from config
	 */
	@Override
	public MudTime timeToNext() {
		return new MudTime(100, Unit.Ticks);
	}

}
