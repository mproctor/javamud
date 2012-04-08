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
	
	protected MudTime runTime;
	protected boolean repeated;
	
	public MudTime getRunTime() {
		return runTime;
	}
	
	public boolean isRepeated() {
		return repeated;
	}
}
