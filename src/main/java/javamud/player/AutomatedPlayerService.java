package javamud.player;

public interface AutomatedPlayerService extends PlayerService {

	public AutomatedPlayer spawnNew(int plyrId);

	public void reloadAutomatedPlayersForZone(Integer zId);
}
