package javamud.routine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreationFactory;
import org.xml.sax.Attributes;

public class RoutineFactory implements ObjectCreationFactory {

	private static Map<String, Routine> routineMap;
	private Digester d;

	public static Routine getNamedRoutine(String name) {
		return routineMap.get(name);
	}

	public void setRoutineMap(Map<String, Routine> r) {
		routineMap = r;
	}

	@Override
	public Object createObject(Attributes attributes) throws Exception {
		String rName = attributes.getValue("name");

		return getNamedRoutine(rName);
	}

	@Override
	public Digester getDigester() {
		return d;
	}

	@Override
	public void setDigester(Digester digester) {
		d=digester;
	}

}
