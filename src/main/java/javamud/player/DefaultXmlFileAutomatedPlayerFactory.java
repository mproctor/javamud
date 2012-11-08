package javamud.player;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

import javamud.util.AbstractXmlFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class DefaultXmlFileAutomatedPlayerFactory extends AbstractXmlFactory
		implements PlayerFactory<AutomatedPlayer> {

	private static final Logger logger = Logger
			.getLogger(DefaultXmlFileAutomatedPlayerFactory.class);

	@Override
	public List<AutomatedPlayer> loadPlayers(Reader r) {
		try {
			List<AutomatedPlayer> players = (List<AutomatedPlayer>) digester
					.parse(r);

			return Collections.unmodifiableList(players);
		} catch (SAXException se) {
			logger.error(
					"Error parsing the automated player file: "
							+ se.getMessage(), se);
		} catch (IOException ie) {
			logger.error(
					"Error parsing the automated player file: "
							+ ie.getMessage(), ie);
		}

		// throw new RuntimeException("Exception while parsing");
		return null;
	}

}
