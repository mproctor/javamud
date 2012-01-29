package javamud.data;

import java.util.List;
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
	
	/**
	 * 
	 * @param s
	 * @param c
	 * @param e require exact match (if false supports substring)
	 */
	void addCommand(String s,Command c,boolean e);
	
	/**
	 * Map the strings to commands. Allow substrings unless command in "e"
	 * @param m
	 * @param e
	 */
	void init(Map<String, Command> m,List<String> e);

}
