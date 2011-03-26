package javamud.server;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class DefaultFileLoginService implements LoginService {
	private static final Logger logger = Logger.getLogger(DefaultFileLoginService.class);
	private String playerFileName;
	
	private Map<String,String> loginDetails = new HashMap<String,String>();
	
	public void init() {
		try {
			FileReader fr = new FileReader(playerFileName);
			BufferedReader br = new BufferedReader(fr);
			
			String line=null;
			while ((line=br.readLine()) != null) {
				String[] login = line.split(":");
				loginDetails.put(login[0], login[1]);
			}
			fr.close();
			
		} catch(IOException ie) {
			logger.error("Unable to read player login file: "+ie.getMessage(),ie);
		}
	}

	public void setPlayerFileName(String playerFileName) {
		this.playerFileName = playerFileName;
	}

	@Override
	public boolean verifyPassword(String pName, String s) {
		return loginDetails.get(pName).equals(s);
	}

	@Override
	public boolean playerExists(String s) {
		return loginDetails.containsKey(s);
	}

	@Override
	public void addUser(String name, String pword) {
		logger.info("adding new user "+name);
		loginDetails.put(name, pword);	
		
		// flush new user to file
	}

}
