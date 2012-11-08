package javamud.player;

import java.util.Map;

import javamud.routine.Routine;
import javamud.routine.RoutineService;

public interface AutomatedPlayer extends Player {

	boolean hasRoutine();

	void triggerRoutine();

	/**
	 * pass the {@link RoutineService} in to setup all the named routines
	 */
	void initRoutines(RoutineService rs);

	/**
	 * 
	 * @return an unmodifiable map of the player's current routine
	 *         names->routines
	 */
	Map<String, Routine> getAllRoutines();

	Routine getRoutine(String key);

}
