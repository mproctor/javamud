package javamud.data;

import static org.junit.Assert.*;
import javamud.command.Command;
import javamud.player.Player;

import org.junit.*;

public class TrieTest {
	
	private static final Command c1 = new Command() {
		@Override
		public void execute(Player p) {			
		}
	};
	private static final Command c2 = new Command() {
		@Override
		public void execute(Player p) {			
		}
	};	
	private static final Command c3 = new Command() {
		@Override
		public void execute(Player p) {			
		}
	};
	private static final Command c4 = new Command() {
		@Override
		public void execute(Player p) {			
		}
	};
	@Test
	public void testSubstring() {
		Trie t = new DefaultTrie();
		t.addCommand("test", c1);
		
		assertEquals(c1, t.getCommandAtWord("test"));
		assertEquals(c1, t.getCommandAtWord("tes"));
		assertEquals(c1, t.getCommandAtWord("te"));
		assertEquals(c1, t.getCommandAtWord("t"));
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
		
		assertEquals(c2, t.getCommandAtWord("stand"));
		assertEquals(c2, t.getCommandAtWord("stan"));
		assertEquals(c1, t.getCommandAtWord("sta"));
		assertEquals(c1, t.getCommandAtWord("st"));
		assertEquals(c1, t.getCommandAtWord("s"));
		assertEquals(c1, t.getCommandAtWord("star"));
		assertEquals(c1, t.getCommandAtWord("start"));		
	}
	
	@Test
	public void testDistinctRoot() {
		Trie t = new DefaultTrie();
		t.addCommand("north", c1);
		t.addCommand("south", c2);
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
	}
}
