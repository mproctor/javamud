package javamud.room;

import java.util.Set;

import javamud.room.SimpleExit.*;

public class RoomLinker {
	
	public static void makeSymmetricLink(Room from,Room to,Direction dir,ExitStatus status,Set<ExitFlag> flags) {
		if (null==status) {
			status = ExitStatus.Open;
		}
		SimpleExit fromExit = new SimpleExit();
		fromExit.setDestination(to);
		fromExit.setDirection(dir);
		fromExit.setFlags(flags);
		fromExit.setStatus(status);
		from.addExit(fromExit);
		
		SimpleExit toExit = new SimpleExit();
		toExit.setDestination(from);
		toExit.setDirection(Direction.oppositeDirection(dir));
		toExit.setFlags(flags);
		toExit.setStatus(status);
		to.addExit(toExit);
	}

}
