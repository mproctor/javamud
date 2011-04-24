package javamud.routine;

import org.apache.log4j.Logger;

import javamud.util.Dice;
import javamud.player.Player;
import javamud.room.Room;
import javamud.room.Exit;

public class RandomMoveRoutine extends SimpleRoutine {
	private static final Logger logger = Logger.getLogger(RandomMoveRoutine.class);
	private Dice dice = new Dice(1,10);

	@Override
	public void execute(Player p) {
		// pick an exit from any available
		Room r = p.getCurrentRoom();
		for(Exit e: r.getExits()) {
			if (dice.roll() > 8) {
				switch(e.getDirection()) {
					case North:	p.forceCommand("north"); break;
					case South:	p.forceCommand("south"); break;
					case East:	p.forceCommand("east"); break;
					case West:	p.forceCommand("west"); break;
					case Up:	p.forceCommand("up"); break;
					case Down:	p.forceCommand("down"); break;
					
					default: logger.warn("unknown exit direction for move routine for "+p.getName()+" ("+e.getDirection()+")");
				}
				break;
			}
		}

	}

}
