package javamud.player;

public interface AutomatedPlayerService {

	public AutomatedPlayer spawnNew(int plyrId);

	public void reloadAutomatedPlayersForZone(Integer zId);
}
