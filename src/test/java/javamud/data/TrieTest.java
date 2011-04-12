package javamud.data;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;

import javamud.command.Command;
import javamud.command.CommandException;
import javamud.player.Player;

import org.junit.Test;

public class TrieTest {
	
	private static final Command c1 = new Command() {
		@Override
		public void execute(Player p,String s) {	
			return ;
		}
	};
	private static final Command c2 = new Command() {
		@Override
		public void execute(Player p,String s) {
			return ;
		}
	};	
	private static final Command c3 = new Command() {
		@Override
		public void execute(Player p,String s) {
			return ;
		}
	};
	private static final Command c4 = new Command() {
		@Override
		public void execute(Player p,String s) {
			return ;
		}
	};
	@Test
	public void testSubstring() {
		Trie t = new DefaultTrie();
		t.addCommand("test", c1);
		
		try {
		assertEquals(c1, t.getCommandAtWord("test"));
		assertEquals(c1, t.getCommandAtWord("tes"));
		assertEquals(c1, t.getCommandAtWord("te"));
		assertEquals(c1, t.getCommandAtWord("t"));
		} catch(CommandException c) {assertTrue(false);}
	}
	
	/**
	 * order is important - the shorter substring will match
	 * the first word entered
	 */
	@Test
	public void testSharedRoot() {
		Trie t = new DefaultTrie();
		t.addCommand("start", c1);
		t.addCommand("stand", c2);
		
		try {
		assertEquals(c2, t.getCommandAtWord("stand"));
		assertEquals(c2, t.getCommandAtWord("stan"));
		assertEquals(c1, t.getCommandAtWord("sta"));
		assertEquals(c1, t.getCommandAtWord("st"));
		assertEquals(c1, t.getCommandAtWord("s"));
		assertEquals(c1, t.getCommandAtWord("star"));
		assertEquals(c1, t.getCommandAtWord("start"));
		} catch(CommandException c) {assertTrue(false);}

	}
	
	@Test
	public void testDistinctRoot() {
		Trie t = new DefaultTrie();
		t.addCommand("north", c1);
		t.addCommand("south", c2);
		try {
		assertEquals(c2, t.getCommandAtWord("south"));
		assertEquals(c2, t.getCommandAtWord("sout"));
		assertEquals(c2, t.getCommandAtWord("sou"));
		assertEquals(c2, t.getCommandAtWord("so"));
		assertEquals(c2, t.getCommandAtWord("s"));
		
		assertEquals(c1, t.getCommandAtWord("north"));
		assertEquals(c1, t.getCommandAtWord("nort"));
		assertEquals(c1, t.getCommandAtWord("nor"));
		assertEquals(c1, t.getCommandAtWord("no"));
		assertEquals(c1, t.getCommandAtWord("n"));	
		} catch(CommandException c) {assertTrue(false);}
	}
	
	@Test
	public void testInit() {
		Trie t = new DefaultTrie();
		LinkedHashMap<String, Command> m = new LinkedHashMap<String, Command>();
		m.put("north",c1);
		m.put("south", c2);
		
		t.init(m);
		
		try {
		assertEquals(c2, t.getCommandAtWord("south"));
		assertEquals(c2, t.getCommandAtWord("sout"));
		assertEquals(c2, t.getCommandAtWord("sou"));
		assertEquals(c2, t.getCommandAtWord("so"));
		assertEquals(c2, t.getCommandAtWord("s"));
		
		assertEquals(c1, t.getCommandAtWord("north"));
		assertEquals(c1, t.getCommandAtWord("nort"));
		assertEquals(c1, t.getCommandAtWord("nor"));
		assertEquals(c1, t.getCommandAtWord("no"));
		assertEquals(c1, t.getCommandAtWord("n"));	
		} catch(CommandException c) {assertTrue(false);}

	}
}
