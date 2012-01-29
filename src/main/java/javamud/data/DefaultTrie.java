package javamud.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javamud.command.Command;
import javamud.command.CommandException;
import javamud.player.Player;

public class DefaultTrie implements Trie {
	
	private TrieNode root = new TrieNode('\0',null);
	
	public DefaultTrie(){};
	
	public void init(Map<String, Command> cmdMappings,List<String> exactString) {
		for(Map.Entry<String, Command> cmdMapping: cmdMappings.entrySet()) {
			addCommand(cmdMapping.getKey(), cmdMapping.getValue(),exactString != null && exactString.contains(cmdMapping.getKey()));
		}
	}

	@Override
	public Command getCommandAtWord(String s) throws CommandException {
		char[] sArr = s.toCharArray();
		TrieNode currNode = root;
		
		int i=0;
		for (i=0;i< sArr.length;) {
			if (currNode.hasChild(sArr[i])) {
				currNode = currNode.getChild(sArr[i]);
				i++;
			} else {
				break;
			}
		}
		
		if (i<sArr.length || sArr.length==0) {
			throw new CommandException(s);		// our string is not a substring of something in the trie 
		} else {
			return currNode.getCmd();
		}
	}

	@Override
	public void addCommand(String s, Command c,boolean e) {
		char[] sArr = s.toCharArray();
		
		TrieNode currNode = root;
		for (int i=0;i< sArr.length;i++) {
			if (currNode.hasChild(sArr[i])) {
				currNode = currNode.getChild(sArr[i]);
				
				// we can overwrite a previous "IncompleteCommand"
				if (!e && currNode.getCmd() instanceof IncompleteCommand) {
					currNode.setCmd(c);
				}
				continue;
			}
			
			TrieNode newNode = new TrieNode(sArr[i],(e&&i< sArr.length-1)?new IncompleteCommand(s):c);
			currNode.addChild(newNode);
			
			currNode = newNode;
		}
	}

	class IncompleteCommand implements Command {
		private final String fullCmd;
		
		IncompleteCommand(String s) {
			fullCmd=s;
		}

		@Override
		public void execute(Player p, String s) {
			p.sendResponse("No substrings available for "+fullCmd);
		}
		
	}
	class TrieNode {
		private Command cmd;
		private char ch='\0';
		private List<TrieNode> children = new LinkedList<TrieNode>();
		private StringBuffer childrenStr = new StringBuffer();
		
		public TrieNode (char ch,Command cmd) {
			this.ch = ch;
			this.cmd = cmd;
		}
		
		public Command getCmd() {
			return cmd;
		}
		
		public void setCmd(Command c) {
			cmd=c;
		}

		public void addChild(TrieNode n) {
			this.children.add(n);
			this.childrenStr.append(n.getCh());
		}
		
		public char getCh() {
			return ch;
		}
		
		public TrieNode getChild(char c) {
			if (hasChild(c)) {
				return (TrieNode) children.toArray()[childrenStr.toString().indexOf(c)];
			}
			
			return null;
		}
		
		public boolean hasChild(char c) {
			return (childrenStr.toString().indexOf(c) > -1);
		}
	}
}
