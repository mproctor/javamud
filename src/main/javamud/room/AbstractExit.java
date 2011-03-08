package javamud.room;

import java.util.HashSet;
import java.util.Set;

/**
 * an exit can have a title (seen when looking at a room: the wooden door) and
 * a description (seen when looking at a keyword for the exit: the door has arcane symbols carved into it)
 * @author Matthew Proctor
 *
 */
public abstract class AbstractExit implements Exit {
	private ExitStatus status;
	
	private Set<String> keywords;	// triggers to show description
	private String title;
	private String description; 
	
	private Set<ExitFlag> flags;
	
	// there can be multiple keys that can unlock a lockable exit
	private Set<Integer> keyIds;
	
	public void setStatus(String status) {
		this.status = ExitStatus.lookupStatus(status);
	}
	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setFlags(Set<String> flags) {
		this.flags = new HashSet<ExitFlag>();
		for(String flag: flags) {
			this.flags.add(ExitFlag.lookupFlag(flag));
		}
	}
	public void setKeyIds(Set<Integer> keyIds) {
		this.keyIds = keyIds;
	}
	public boolean isOpen() {
		return status == ExitStatus.Open;
	}
	public boolean isClosed() {
		return status == ExitStatus.Closed;
	}
	public boolean isLocked() {
		return status == ExitStatus.Locked;
	}
	public boolean isLockable() {
		return flags.contains(ExitFlag.Lockable);
	}
	public boolean isPickable() {
		return flags.contains(ExitFlag.Pickable);
	}
	public enum Direction{
		North("north"),South("south"),East("east"),West("west"),Up("up"),Down("down");
		
		private String description;
		private Direction(String dir) {
			this.description = dir;
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

}
