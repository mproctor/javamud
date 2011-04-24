package javamud.player;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import javamud.routine.Routine;

public class SimpleAutomatedPlayer extends AbstractPlayer implements AutomatedPlayer {

	private static final Logger logger = Logger.getLogger(SimpleAutomatedPlayer.class);
	private Set<Routine> routines = new HashSet<Routine>();
	
	
	public void addRoutine(Routine routine) {
		this.routines.add(routine);
	}


	@Override
	public void triggerRoutine() {
		logger.info("firing routine for "+getName());
	}


	@Override
	public void hear(Player p, String s) {
		logger.info(getName()+" hears "+s);
	}


	@Override
	public void seeEvent(Player p, String s) {
		logger.info(getName()+" sees "+s);
	}


	@Override
	public void sendResponse(String s) {
		logger.info(getName()+" should send response "+s);
	}

}
