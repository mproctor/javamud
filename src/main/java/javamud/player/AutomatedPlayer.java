package javamud.player;

public interface AutomatedPlayer extends Player {
	
	boolean hasRoutine();
	void triggerRoutine();

}
