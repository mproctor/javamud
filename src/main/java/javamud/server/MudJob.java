package javamud.server;

import java.util.Comparator;

public abstract class MudJob implements Runnable {
	
	private static Comparator<MudJob> jobComparator = new Comparator<MudJob> () {

		@Override
		public int compare(MudJob firstJob, MudJob secondJob) {
			return firstJob.runTime.compareTo(secondJob.runTime);
		}
		
	};
	
	public static Comparator<MudJob> getComparator() {
		return jobComparator;
	}
	
	private MudTime runTime;
	
	public MudTime getRunTime() {
		return runTime;
	}
}
