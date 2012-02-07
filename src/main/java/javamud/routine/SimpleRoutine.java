package javamud.routine;

import java.util.HashMap;
import java.util.Map;

public abstract class SimpleRoutine implements Routine{
	
	private Map<String,String> params = new HashMap<String,String>();
	
	public void addParam(SimpleParam p) {
		params.put(p.getName(),p.getValue());
	}

	public Map<String, String> getParams() {
		return params;
	}

}
