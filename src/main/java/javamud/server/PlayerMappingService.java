package javamud.server;

import java.nio.channels.SelectionKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javamud.player.Player;
import javamud.player.PlayerService;
import javamud.room.Room;
import javamud.room.WorldService;

/**
 * maintains the mapping between a player on the mud and their
 * socket connection
 * @author Matthew Proctor
 *
 */
public class PlayerMappingService {
	private MudServer mudServer;
	private PlayerService playerService;
	
	public void setMudServer(MudServer mudServer) {
		this.mudServer = mudServer;
	}

	private Map<Player,SelectionKey> playerChannel = new ConcurrentHashMap<Player,SelectionKey>();
	private Map<SelectionKey,Player> channelPlayer = new ConcurrentHashMap<SelectionKey,Player>();
	public SelectionKey lookup(Player p) {
		return playerChannel.get(p);
	}
	public Player lookup(SelectionKey client) {
		return channelPlayer.get(client);
	}
	public void removeClient(SelectionKey client) {
		// TODO needs synchronisation?
		Player p = channelPlayer.get(client);
		playerChannel.remove(p);
		channelPlayer.remove(client);
		
		Room r = p.getCurrentRoom();
		r.removePlayer(p);
	}
	
	public void disconnectPlayer(Player p) {
		SelectionKey sk = playerChannel.get(p);
		playerChannel.remove(p);
		channelPlayer.remove(sk);
		Room r = p.getCurrentRoom();
		r.removePlayer(p);
	}
	
	public void sendString(Player p, String s) {
		mudServer.sendStringToSelectionKey(s, lookup(p));
	}
	public Player getPlayerByName(String pName) {
		for(Player p:playerChannel.keySet()) {
			if(p.getName().equals(pName)) {
				return p;
			}
		}
		return null;
	}
	public void loadPlayerWithSelectionKey(String pName, SelectionKey k) {
		Player p = playerService.loadPlayer(pName);
		playerChannel.put(p,k);
		channelPlayer.put(k,p);
		
		Room r = p.getCurrentRoom();
		r.addPlayer(p);
	}
	public void setPlayerService(PlayerService playerService) {
		this.playerService = playerService;
	}
	public void executeCommand(String pName, String s) {
		final Player p = getPlayerByName(pName);

		playerService.runCommand(p, s);
	}
	
}
