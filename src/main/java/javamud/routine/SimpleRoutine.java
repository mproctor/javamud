package javamud.routine;

import java.util.HashMap;
import java.util.Map;

public abstract class SimpleRoutine implements Routine{
	
	private Map<String,String> params = new HashMap<String,String>();
	
	public void addParam(String n, String v) {
		params.put(n,v);
	}

	public Map<String, String> getParams() {
		return params;
	}

}
