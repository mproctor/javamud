package javamud.server;

import java.nio.channels.SelectionKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javamud.player.Player;

/**
 * maintains the mapping between a player on the mud and their
 * socket connection
 * @author Matthew Proctor
 *
 */
public class PlayerMappingService {

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
	}
}
