package javamud.server;

import java.util.Comparator;

/**
 * Mud time units - ticks are the internal measure, hours are the minimum external
 * measure (calendars will combine hours into days/months/years)
 * @author Matt
 *
 */
public class MudTime implements Comparable<MudTime>{
	
	private static final int TICKS_PER_HOUR = 60; 

	public enum Unit {Ticks,Hours};
	
	private final Unit unit;
	private final int qty;
	
	public static Comparator<MudTime> getComparator() {
		return new Comparator<MudTime> () {

			@Override
			public int compare(MudTime first, MudTime second) {
				if (first == second) {
					return 0;
				} else if (first.unit == second.unit) {
					return new Integer(first.qty).compareTo(new Integer(second.getQty()));
				} else if (first.unit == Unit.Ticks && second.unit == Unit.Hours) {
					return new Integer(first.qty*TICKS_PER_HOUR).compareTo(new Integer(second.getQty()));
				} else {
					return new Integer(first.qty).compareTo(new Integer(second.getQty()*TICKS_PER_HOUR));
				}	
			}			
		};
	}
	
	public MudTime(int qty,Unit unit) {
		this.qty= qty;
		this.unit=unit;
	}
	
	public Unit getUnit() {
		return unit;
	}
	public int getQty() {
		return qty;
	}
	
	@Override
	public int hashCode() {
		return (31*unit.hashCode()) +  qty;
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		} else if (other instanceof MudTime) {
			return (this.compareTo((MudTime)other) == 0);
		}
		
		return false;
	}
	
	@Override
	public int compareTo(MudTime other) {
		if (this == other) {
			return 0;
		} else if (unit == other.unit) {
			return new Integer(qty).compareTo(new Integer(other.getQty()));
		} else if (unit == Unit.Ticks && other.unit == Unit.Hours) {
			return new Integer(qty*TICKS_PER_HOUR).compareTo(new Integer(other.getQty()));
		} else {
			return new Integer(qty).compareTo(new Integer(other.getQty()*TICKS_PER_HOUR));
		}
	}
}
