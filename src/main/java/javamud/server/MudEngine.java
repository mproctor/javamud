package javamud.server;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

import javamud.calendar.MudCalendar;
import javamud.command.Command;
import javamud.player.Player;
import javamud.server.MudTime.Unit;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

/**
 * The main engine - driven by a timer that we schedule events on to
 * 
 * @author Matt
 * 
 */
public class MudEngine {

	private static final Logger logger = Logger.getLogger(MudEngine.class);

	private Scheduler coreScheduler;

	private Queue<MudJob> mudTickTasks;
	
	private Queue<MudJob> mudHourTasks;


	private Executor scheduledTaskExecutor; // mud heartbeat
	private Executor playerCommandExecutor; // player initiated commands

	public void setPlayerCommandExecutor(Executor playerCommandExecutor) {
		this.playerCommandExecutor = playerCommandExecutor;
	}

	public void setScheduledTaskExecutor(Executor taskExecutor) {
		this.scheduledTaskExecutor = taskExecutor;
	}

//	private Trigger hbTrigger;

	public MudEngine(int startingCapacity) {

		mudTickTasks = new PriorityQueue<MudJob>(startingCapacity,MudJob.getComparator());
		mudHourTasks = new PriorityQueue<MudJob>(startingCapacity,MudJob.getComparator());
		
		startTimerHeartbeat();

	}

	private Timer coreTimer;

	private void startTimerHeartbeat() {
		coreTimer = new Timer();
		
		logger.info("Starting the core timer");
		coreTimer.scheduleAtFixedRate(new MudEngineDequeJob(), 0, MudCalendar.MS_PER_MUD_TICK);
	}

//	private void startHeartbeat() {
//		try {
//			coreScheduler = StdSchedulerFactory.getDefaultScheduler();
//			logger.info("Starting the core scheduled executor");
//			coreScheduler.start();
//
//			JobDetail dequeueJob = JobBuilder.newJob(MudEngineDequeJob.class)
//					.withIdentity("dequeue", "core").build();
//
//			hbTrigger = TriggerBuilder
//					.newTrigger()
//					.startNow()
//					.withIdentity("hbTrig", "core")
//					.forJob(dequeueJob)
//					.withSchedule(
//							SimpleScheduleBuilder.simpleSchedule()
//									.repeatForever()
//									.withIntervalInMilliseconds(1000L)).build();
//
//		} catch (SchedulerException e) {
//			logger.warn(
//					"Exception while starting up main engine loop: "
//							+ e.getMessage(), e);
//
//			// without the loop we may as well die
//			throw new RuntimeException(e);
//		}
//	}

	/**
	 * need to shutdown the quartz scheduler to allow jvm to stop
	 */
	public void shutdown() {
		try {
			coreScheduler.shutdown();
		} catch (SchedulerException e) {
			logger.warn(
					"Exception while shutting down engine main loop: "
							+ e.getMessage(), e);
		}
	}

	private class MudEngineDequeJob extends TimerTask implements Job {
		
		private int countdown =  MudCalendar.TICKS_PER_HOUR;
		@Override
		public void execute(JobExecutionContext arg0)
				throws JobExecutionException {

			logger.debug("running mudjob-dequeue");
			MudJob mudJob = null;

			mudJob = mudTickTasks.poll();

			if (mudJob != null) {
				logger.debug("Running job " + mudJob);

				scheduledTaskExecutor.execute(mudJob);
			}

		}

		// TODO: reschedule needs to be fixed
		@Override
		public void run() {
			MudJob mudJob = null;
			
			int tNum=mudTickTasks.size();
			logger.debug("Processing "+tNum+" tasks");

			while(tNum-- > 0) {

				mudJob = mudTickTasks.poll();
	
				if (mudJob != null) {
					logger.debug("Running job " + mudJob);
	
					scheduledTaskExecutor.execute(mudJob);
					
					if (mudJob.isRepeated()) {
						submitScheduledTask(mudJob);
					}
				}
			}
			
			countdown--;
			
			if (countdown == 0) {
				countdown = MudCalendar.TICKS_PER_HOUR;
				tNum = mudHourTasks.size();
				logger.debug("Processing "+tNum+" hourly tasks");
				while (tNum-- > 0) {
					mudJob = mudHourTasks.poll();
					if (mudJob != null) {
						logger.debug("Running hourly job "+mudJob);
						scheduledTaskExecutor.execute(mudJob);
						
					}
				}				
			}
		}
	}
	
	public void submitScheduledTask(MudJob job) {
		if (job.getRunTime() != null && job.getRunTime().getUnit() == Unit.Hours) {
			mudHourTasks.offer(job);
		} else {
			mudTickTasks.offer(job);
		}
	}

	public void submitPlayerCommand(final Player p, final Command c,
			final String cmdArgs) {
		playerCommandExecutor.execute(new Runnable() {

			// TODO: this only allows a command to return a single string

			@Override
			public void run() {
				c.execute(p, cmdArgs);
			}
		});

	}
}
