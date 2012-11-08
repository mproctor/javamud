package javamud.routine;

import java.util.HashMap;
import java.util.Map;

public abstract class SimpleRoutine implements Routine {

	private Map<String, String> params = new HashMap<String, String>();

	private String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public void addParam(Param p) {
		params.put(p.getName(), p.getValue());
	}

	public Map<String, String> getParams() {
		return params;
	}

}
