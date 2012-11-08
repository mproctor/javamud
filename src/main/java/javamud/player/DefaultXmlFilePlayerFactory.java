package javamud.player;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

import javamud.util.AbstractXmlFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class DefaultXmlFilePlayerFactory extends AbstractXmlFactory implements
		PlayerFactory<RemotePlayer> {

	private static final Logger logger = Logger
			.getLogger(DefaultXmlFilePlayerFactory.class);

	@Override
	public List<RemotePlayer> loadPlayers(Reader r) {
		try {
			List<RemotePlayer> players = (List<RemotePlayer>) digester.parse(r);

			return Collections.unmodifiableList(players);
		} catch (SAXException se) {
			logger.error("Error parsing the player file: " + se.getMessage(),
					se);
		} catch (IOException ie) {
			logger.error("Error parsing the player file: " + ie.getMessage(),
					ie);
		}

		return null;
	}

}
