package javamud.command;

import javamud.data.Trie;
import java.util.*;

public class DefaultCommandParser implements CommandParser {
	private Trie t;
	private Map<String,Command> cmdMap;
	private List<String> exact;		// words in this list need exact match
	
	public void setT(Trie t) {
		this.t = t;
	}
	
	public void init() {
		t.init(cmdMap,exact);
		
		// TODO: clear cmdMap for garbage collection?
	}
	
	public void setExact(List<String> exact) {
		this.exact = exact;
	}

	public void setCmdMap(Map<String,Command> cmdMap) {
		this.cmdMap = cmdMap;
	}

	@Override
	public Command parse(String cmd) throws CommandException {
		return t.getCommandAtWord(cmd);
	}

}
