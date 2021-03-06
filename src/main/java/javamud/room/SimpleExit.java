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
	private int toRoomId=Room.ROOMID_NULL,toZoneId=Zone.ZONEID_NULL;
	private Room destination;
	private Direction direction;
	private ExitType exitType;
	
	private Set<ExitFlag> flags;
	
	// there can be multiple keys that can unlock a lockable exit
	private Set<Integer> keyIds;
	
	@Override
	public boolean isKeyForExit(Item key) {
		return keyIds.contains(key.getId());
	}
	
	public int getToZoneId() {
		return toZoneId;
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
	
	public void setZoneId(String zoneId) {
		this.toZoneId = Integer.parseInt(zoneId);
	}
	
	public void setType(String exitType) {
		this.exitType = ExitType.lookupType(exitType);
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

	@Override
	public boolean hasKeyword(String descr) {
		return this.keywords != null && this.keywords.contains(descr);
	}

}
