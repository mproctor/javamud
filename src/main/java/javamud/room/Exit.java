package javamud.room;

import java.util.Set;

import javamud.item.Item;

public interface Exit {
	public  boolean isKeyForExit(Item key);

	public  String getTitle();

	public  String getDescription();

	public  boolean isOpen();

	public  boolean isClosed();

	public  boolean isLocked();

	public  boolean isLockable();

	public  boolean isPickable();

	public Room getDestination();
	public Direction getDirection();

	public Set<String> getKeywords();
	
	public enum ExitType{
		OneWay("oneway"),TwoWay("twoway");
		
		private String description;
		private ExitType(String s) {
			this.description = s;
		}
		
		public static ExitType lookupType(String s) {
			if (null == s) {
				throw new IllegalArgumentException("null type string passed to ExitType.lookup");
			}
			
			for (ExitType e: values()) {
				if (s.equals(e.description)) {
					return e;
				}
			}
			
			return null;
		}
	}
	public enum Direction{
		North("north"),South("south"),East("east"),West("west"),Up("up"),Down("down");
		
		private String description;
		private Direction(String dir) {
			this.description = dir;
		}
		
		public static Direction oppositeDirection(Direction d) {
			switch(d) {
			case North: return South;
			case South: return North;
			case East: return West;
			case West: return East;
			case Up: return Down;
			case Down: return Up;
			default: return null;
			}
		}
		
		public String getDescription() {
			return this.description;
		}
		
		public static Direction lookupDir(String dir) {
			if (null == dir) {
				throw new IllegalArgumentException("null dir specified to Direction.lookupDir");
			}
			for(Direction d: values()) {
				if (dir.equals(d.description)) {
					return d;
				}
			}
			
			// unable to parse the dir
			return null;
		}
	};
	
	public enum ExitStatus {
		Open("open"),Closed("closed"),Locked("locked");
		
		private String description;
		private ExitStatus(String description) {
			this.description = description;
		}
		
		public String getDescription() {
			return this.description;
		}
		
		public static ExitStatus lookupStatus(String status) {
			
			if (null == status) {
				throw new IllegalArgumentException("null status specified to ExitStatus.lookupStatus");
			}
			for(ExitStatus s: values()) {
				if (status.equals(s.description)) {
					return s;
				}
			}
			
			// unable to parse the status
			return null;
		}
	}
	
	public enum ExitFlag {
		Lockable("lockable"),Pickable("pickable");
		
		private String description;
		private ExitFlag(String desc) {
			this.description = desc;
		}
		
		public String getDescription() {
			return this.description;
		}
		public static ExitFlag lookupFlag(String flag) {

			if (null == flag) {
				throw new IllegalArgumentException("null status specified to ExitFlag.lookupFlag");
			}
			for(ExitFlag f: values()) {
				if (flag.equals(f.description)) {
					return f;
				}
			}
			
			// unable to parse the flag
			return null;
		}
	}

	public boolean hasKeyword(String descr);

}
