package javamud.data;

import java.util.Map;

import javamud.command.Command;
import javamud.command.CommandException;

/**
 * modified Trie structure that holds a Command at each level
 * this allows us to accept valid substrings
 * @author Matthew Proctor
 *
 */
public interface Trie {
	Command getCommandAtWord(String s) throws CommandException;
	
	void addCommand(String s,Command c);
	
	void init(Map<String, Command> m);

}
