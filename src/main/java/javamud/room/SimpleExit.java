package javamud.room;

import java.util.HashSet;
import java.util.Set;

import javamud.item.Item;

/**
 * an exit can have a title (seen when looking at a room: the wooden door) and
 * a description (seen when looking at a keyword for the exit: the door has arcane symbols carved into it)
 * @author Matthew Proctor
 *
 */
public class SimpleExit implements Exit {
	private ExitStatus status;
	
	private Set<String> keywords;	// triggers to show description
	private String title;
	private String description; 
	private int toRoomId;
	private Room destination;
	private Direction direction;
	
	private Set<ExitFlag> flags;
	
	// there can be multiple keys that can unlock a lockable exit
	private Set<Integer> keyIds;
	
	@Override
	public boolean isKeyForExit(Item key) {
		return keyIds.contains(key.getId());
	}
	
	@Override
	public String getTitle() {
		return title;
	}
	@Override
	public String getDescription() {
		return description;
	}
	
	public void setRoomId(String roomId) {
		this.toRoomId = Integer.parseInt(roomId);
	}
	
	public Room getDestination() {
		return destination;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public void setDir(String direction) {
		this.direction = Direction.lookupDir(direction);
	}

	public void setDestination(Room destination) {
		this.destination = destination;
	}

	public void setStatus(String status) {
		this.status = ExitStatus.lookupStatus(status);
	}
	public void setExitStatus(ExitStatus status) {
		this.status = status;
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
	public void setFlagsByName(Set<String> flags) {
		this.flags = new HashSet<ExitFlag>();
		for(String flag: flags) {
			this.flags.add(ExitFlag.lookupFlag(flag));
		}
	}
	public void setFlags(Set<ExitFlag> flags) {
		this.flags = flags;
	}
	public void setKeyIds(Set<Integer> keyIds) {
		this.keyIds = keyIds;
	}
	@Override
	public boolean isOpen() {
		return status == ExitStatus.Open;
	}
	@Override
	public boolean isClosed() {
		return status == ExitStatus.Closed;
	}
	@Override
	public boolean isLocked() {
		return status == ExitStatus.Locked;
	}
	@Override
	public boolean isLockable() {
		return flags.contains(ExitFlag.Lockable);
	}
	@Override
	public boolean isPickable() {
		return flags.contains(ExitFlag.Pickable);
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

	public int getToRoomId() {
		return toRoomId;
	}

	public void setToRoomId(int toRoomId) {
		this.toRoomId = toRoomId;
	}

	@Override
	public Set<String> getKeywords() {
		return keywords;
	}

}
