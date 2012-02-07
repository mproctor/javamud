package javamud.routine;

public class SimpleParam implements Param {
	private String name,value;

	@Override
	public String getName() {
		return name;
	}

	public void setName(String n) {
		this.name = n;
	}

	@Override
	public String getValue() {
		return value;
	}

	public void setValue(String v) {
		this.value = v;
	}

}
