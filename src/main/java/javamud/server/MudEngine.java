package javamud.server;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

import javamud.command.Command;
import javamud.player.Player;

import org.quartz.Job;
//import org.quartz.JobBuilder;
//import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
//import org.quartz.SimpleScheduleBuilder;
//import org.quartz.Trigger;
//import org.quartz.TriggerBuilder;
//import org.quartz.impl.StdSchedulerFactory;
import org.apache.log4j.Logger;

/**
 * The main engine - driven by a timer that we schedule events on to
 * 
 * @author Matt
 * 
 */
public class MudEngine {

	private static final Logger logger = Logger.getLogger(MudEngine.class);

	private Scheduler coreScheduler;

	private Queue<MudJob> mudTasks;

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

		mudTasks = new PriorityQueue<MudJob>(startingCapacity,
				MudJob.getComparator());
		startTimerHeartbeat();

	}

	private Timer coreTimer;

	private void startTimerHeartbeat() {
		coreTimer = new Timer();
		
		logger.info("Starting the core timer");
		coreTimer.scheduleAtFixedRate(new MudEngineDequeJob(), 0, 1000L);
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
		@Override
		public void execute(JobExecutionContext arg0)
				throws JobExecutionException {

			logger.debug("running mudjob-dequeue");
			MudJob mudJob = null;

			mudJob = mudTasks.poll();

			if (mudJob != null) {
				logger.debug("Running job " + mudJob);

				scheduledTaskExecutor.execute(mudJob);
			}

		}

		@Override
		public void run() {
			logger.debug("running task-dequeue");
			MudJob mudJob = null;

			mudJob = mudTasks.poll();

			if (mudJob != null) {
				logger.debug("Running job " + mudJob);

				scheduledTaskExecutor.execute(mudJob);
			}
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
