package javamud.routine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoutineFactory {
	
	private static Map<String, Routine> routineMap ;
	public static Routine getNamedRoutine(String name) {
		return routineMap.get(name);
	}

	public void setRoutineMap(Map<String ,Routine> r) {
		routineMap =r;
	}
	
}
