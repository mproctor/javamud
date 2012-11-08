package javamud.util;

import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

public abstract class AbstractXmlFactory {
	private String digesterFileName;
	protected Digester digester;

	private static final Logger logger = Logger
			.getLogger(AbstractXmlFactory.class);

	public void setDigesterFileName(String digesterFileName) {
		this.digesterFileName = digesterFileName;
	}

	public void init() {
		ClassPathResource cpr = new ClassPathResource(digesterFileName);
		try {
			digester = DigesterLoader.createDigester(cpr.getURL());
		} catch (IOException e) {
			logger.error("Problem getting digester: " + e.getMessage(), e);
		}
	}

	public Digester getDigester() {
		return digester;
	}

}
