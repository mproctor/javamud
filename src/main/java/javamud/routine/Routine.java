package javamud.routine;

import javamud.player.Player;
import javamud.server.MudEngine;
import javamud.server.MudTime;

/**
 * AutomatedPlayers run Routines
 * 
 * @author Matthew Proctor
 * 
 */
public interface Routine {

	/**
	 * sphere of influence for this routine, e.g. can a move routine move
	 * between zones or not
	 * 
	 * @author Matt
	 * 
	 */
	public enum Sphere {
		WithinZone, BetweenZone
	};

	/**
	 * This is the name that will be used to refer to this routine in the
	 * npc.xml
	 * 
	 * @return the name of the routine as defined in the routines.xml
	 */
	String getName();

	/**
	 * does this routine have an execution to run (note: returning false here
	 * doesn't mean the routine will be cleaned from memory, routines can be
	 * rescheduled)
	 * 
	 * @return whether the scheduler needs to schedule this routine right now
	 */
	boolean hasNext();

	/**
	 * How many mud ticks (see {@link MudEngine} for time of a 'tick') or mud
	 * 'hours' until next execution
	 * 
	 * @return number of ticks/hours until next execution or 0 for immediate
	 *         (next cycle)
	 */
	MudTime timeToNext();

	void execute(Player p);

	void setName(String rName);
}
