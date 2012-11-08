package javamud.routine;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javamud.util.AbstractXmlFactory;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreationFactory;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

// TODO: rename this.. to DefaultXmlFileRoutineFactory - and interface RoutineFactory extends ObjectCreationFactory (but what happens in the auto-player file?)
public class RoutineFactory extends AbstractXmlFactory implements
		ObjectCreationFactory {
	private static final Logger logger = Logger.getLogger(RoutineFactory.class);
	private Digester d;

	public Map<String, Routine> loadRoutines(Reader r) {
		try {
			List<Routine> routines = (List<Routine>) digester.parse(r);

			Map<String, Routine> routineMap = new HashMap<String, Routine>();

			for (Routine routine : routines) {
				routineMap.put(routine.getName(), routine);
			}

			return Collections.unmodifiableMap(routineMap);
		} catch (SAXException se) {
			logger.error("Error parsing the routines file: " + se.getMessage(),
					se);
		} catch (IOException ie) {
			logger.error("Error parsing the routines file: " + ie.getMessage(),
					ie);
		}

		return null;
	}

	@Override
	public Object createObject(Attributes attributes) throws Exception {
		String rName = attributes.getValue("name");
		String className = attributes.getValue("class");

		// if we were given a class, then create it and add it to the map, else
		// just look it up - we create it using the classloader used to create
		// this factory
		Routine r = null;
		if (className != null && !"".equals(className)) {
			Class<?> clazz = this.getClass().getClassLoader()
					.loadClass(className);
			r = (Routine) clazz.newInstance();
			r.setName(rName);

		} else {
			logger.error("Missing class attribute on routine");
		}

		if (r == null) {
			logger.warn("Unable to create routine named " + rName);
		}

		return r;
	}

	@Override
	public void setDigester(Digester digester) {
		if (this.digester == null) {
			this.digester = digester;
		} else if (!this.digester.equals(digester)) {
			logger.warn("Ignoring an attempt to reset the digester to a different digester");
		}

	}

}
