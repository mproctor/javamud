package javamud.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javamud.routine.Routine;
import javamud.routine.RoutineService;

import org.apache.log4j.Logger;

public class SimpleAutomatedPlayer extends AbstractPlayer implements
		AutomatedPlayer {

	private static final Logger logger = Logger
			.getLogger(SimpleAutomatedPlayer.class);
	private Map<String, Routine> routines = new HashMap<String, Routine>();
	private List<String> keywords = new ArrayList<String>();
	private List<String> routineNames = new ArrayList<String>();

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public void setKeyword(String kw) {
		this.keywords.add(kw);
	}

	public boolean hasKeyword(String k) {
		return this.keywords != null && this.keywords.contains(k);
	}

	@Override
	public boolean hasRoutine() {
		return routines.size() > 0;
	}

	public void addRoutine(String key, Routine routine) {
		this.routines.put(key, routine);
	}

	@Override
	public void triggerRoutine() {
		logger.info("firing routine for " + getName());
	}

	@Override
	public void hear(Player p, String s) {
		logger.info(getName() + " hears " + s);
	}

	@Override
	public void seeEvent(Player p, String s) {
		logger.info(getName() + " sees " + s);
	}

	@Override
	public void sendResponse(String s) {
		logger.info(getName() + " should send response " + s);
	}

	@Override
	public Routine getRoutine(String key) {
		return routines.get(key);
	}

	@Override
	public Map<String, Routine> getAllRoutines() {
		return Collections.unmodifiableMap(routines);
	}

	/**
	 * lookup a routine by name and add it to our map of known routines
	 * 
	 * @param rName
	 *            name of a routine in the routineMap
	 */
	public void setRoutineName(String rName) {
		routineNames.add(rName);
	}

	@Override
	public void initRoutines(RoutineService routineService) {
		if (routineNames.size() == 0) {
			logger.info("No routines to initialise for " + getName());
			return;
		}
		for (String name : routineNames) {
			Routine routine = routineService.getNamedRoutine(name);

			if (routine != null) {
				routines.put(name, routine);
			} else {
				logger.error("Unable to lookup the routine " + name
						+ " in the routine service");
			}
		}
	}
}
