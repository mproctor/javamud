package javamud.calendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import javamud.server.MudEngine;
import javamud.server.MudJob;
import javamud.server.MudTime;
import javamud.server.MudTime.Unit;

public class MudCalendar implements InitializingBean {
	
	private static final Logger logger = Logger.getLogger(MudCalendar.class);
	
	public static final long MS_PER_MUD_TICK = 1000L;
	
	private static final String STATIC_PART="Month of the ";
	// tick each second
	public static final int TICKS_PER_HOUR = 10;
	private static final int HOURS_PER_DAY  = 20;
	private static final int DAYS_PER_MONTH = 20;
	
	private MudEngine mudEngine;
	
	public void setMudEngine(MudEngine mudEngine) {
		this.mudEngine = mudEngine;
	}

	// "Month of the .. "
	private static final String[] MONTHS = new String[]{"Eagle","Squirrel","Wolf","Bear","Yeti","Hazelnut","Cabbage","Apple","Garnet","Termite"};
	
	private int month=0,day=1,hour=1;
	
	protected void nextMonth() {
		if (month >= MONTHS.length) {
			month = 0;
		} else {
			month = month+1;
		}
		logger.debug("Month tick to "+MONTHS[month]);
	}
	
	protected void nextDay() {
		if (day >= DAYS_PER_MONTH-1) {
			day = 1;
			nextMonth();
		} else {
			day = day + 1;
		}	
		logger.debug("Day tick to "+day);
	}
	
	public void nextHour() {
		if (hour >= HOURS_PER_DAY-1) {
			hour=1;
			nextDay();
		} else {
			hour = hour+1;
		}
		logger.debug("Hour tick to "+hour);
	}
	
	public String getMonthName() {
		return STATIC_PART + MONTHS[month];
	}

	class CalendarJob extends MudJob {
		
		CalendarJob() {
			repeated=true;
			runTime=new MudTime(1, Unit.Hours);
		}

		@Override
		public void run() {
			nextHour();
		}
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// add the basic calendar behaviour
		
		mudEngine.submitScheduledTask(new CalendarJob());
	}
}
