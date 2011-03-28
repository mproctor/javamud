package javamud.command;

import javamud.data.DefaultTrie;
import javamud.data.Trie;

public class DefaultCommandParser implements CommandParser {
	private Trie t = new DefaultTrie();

	@Override
	public Command parse(String cmd) {
		return t.getCommandAtWord(cmd);
	}

}
