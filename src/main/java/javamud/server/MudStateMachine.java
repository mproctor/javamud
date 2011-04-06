package javamud.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Map;

import javamud.command.CommandExecutor;
import javamud.player.LoginService;

import org.apache.log4j.Logger;

public class MudStateMachine {
	private static final String STATE_ATTRIB = "state";
	private static final String STRING_BUFFER = "stringBuffer";
	private static final String PLAYER_NAME = "playerName";
	private static final String SELECTION_KEY = "selectionKey";
	private LoginService loginService;
	private CommandExecutor commandExecutor;
	private MudServer mudServer;
	private PlayerMappingService playerMappingService;
	
	public void setPlayerMappingService(PlayerMappingService pms) {
		this.playerMappingService = pms;
	}
	public void setCommandExecutor(CommandExecutor commandExecutor) {
		this.commandExecutor = commandExecutor;
	}

	public void setMudServer(MudServer mudServer) {
		this.mudServer = mudServer;
	}

	private static final Logger logger = Logger
			.getLogger(MudStateMachine.class);
	
	private ByteBuffer buffer = ByteBuffer.allocate(512);

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	@SuppressWarnings("unchecked")
	public void init(SelectionKey k) {
		Map<String, Object> attribs = (Map<String, Object>) k.attachment();
		CharsetEncoder enc = (CharsetEncoder) attribs.get("encoder");
		try {
			((SocketChannel) k.channel()).write(enc.encode(CharBuffer
					.wrap("Welcome to JavaMud\r\nPlease enter your name: ")));
			attribs.put(STRING_BUFFER, new StringBuffer());
			attribs.put(STATE_ATTRIB, new InitState());
			attribs.put(SELECTION_KEY, k);

		} catch (CharacterCodingException e) {
			attribs.put(STATE_ATTRIB, new FailState());
			logger.warn("Problems writing to client - moving to fail state:"
					+ e.getMessage(), e);
		} catch (IOException e) {
			attribs.put(STATE_ATTRIB, new FailState());
			logger.warn("Problems writing to client - moving to fail state:"
					+ e.getMessage(), e);
		}

	}

	@SuppressWarnings("unchecked")
	public void processMessage(SelectionKey k) {
		Map<String,Object> attribs = (Map<String,Object>)k.attachment();
		CharsetDecoder d = (CharsetDecoder)attribs.get("decoder");
		SocketChannel client = (SocketChannel)k.channel();
		StringBuffer sb = (StringBuffer)attribs.get(STRING_BUFFER);
		ClientState state = (ClientState)attribs.get(STATE_ATTRIB);
		
		// choke if we already failed
		if (state instanceof FailState) {
			mudServer.sendStringToSelectionKey("Login failed.", k);
			mudServer.close(k);
		}

		int bytesread = -1;
		try {
			bytesread = client.read(buffer);
		} catch (IOException e1) {
			logger.warn("Problem reading from client connection: "+e1.getMessage(),e1);
		}
        if (bytesread == -1) {
          mudServer.close(k);
          logger.warn("Client was readable but no bytes were read - closed client");
        }
        else {
	        buffer.flip();
	        try {
	        	sb.append(d.decode(buffer));
				//String request = d.decode(buffer).toString();
	        	
	        	if (sb.charAt(sb.length()-1) == '\n') {
	        		
					String response = state.runState(sb.toString().replaceAll("\\r|\\n", ""), attribs);
					
					sb.setLength(0);	// clear the buffer
					if (response != null) {
						mudServer.sendStringToSelectionKey(response, k);
					}
	        	}
			} catch (CharacterCodingException e) {
				logger.warn("Problem processing incoming message: "+e.getMessage(),e);
			}
	        buffer.clear();
        }
	}


	/**
	 * init -> request name -> request pwd -> logged in | -> create player
	 * (several stages) -> get pwd -> logged in
	 * 
	 * @author Matthew Proctor
	 * 
	 */
	public abstract class ClientState {
		protected String stateName;

		public String getStateName() {
			return stateName;
		}

		// returns the next state
		// will modify the attribs to reset the next state
		// may return a string to send to the client
		public abstract String runState(String s, Map<String, Object> attribs);
	}

	public class FailState extends ClientState {
		public FailState() {
			this.stateName = "fail";
		}

		/**
		 * there is no valid next state from the fail state
		 */
		@Override
		public String runState(String s, Map<String, Object> attribs) {
			attribs.put(STATE_ATTRIB, null);
			return null;
		}

	}

	public class LoginPlayerState extends ClientState {
		public LoginPlayerState() {
			this.stateName = "loginplayer";
		}

		@Override
		public String runState(String s, Map<String, Object> attribs) {
			String pName = (String) attribs.get(PLAYER_NAME);

			// TODO: keep the attribute and give a pword retry?
			if (loginService.verifyPassword(pName, s)) {
				SelectionKey k = (SelectionKey)attribs.get(SELECTION_KEY);
				playerMappingService.loadPlayerWithSelectionKey(pName,k);
				attribs.put(STATE_ATTRIB, new LoggedInState());
				return null;
			} else {
				attribs.put(STATE_ATTRIB, new FailState());
				return "Password/User combination not matched";
			}
		}

	}

	public class LoggedInState extends ClientState {
		public LoggedInState() {
			this.stateName = "loggedin";
		}

		@Override
		public String runState(String s, Map<String, Object> attribs) {
			String pName = (String)attribs.get(PLAYER_NAME);
			commandExecutor.executeCommand(pName,s);
			
			return null;
		}
	}

	public class InitState extends ClientState {

		public InitState() {
			this.stateName = "init";
		}

		/**
		 * we should have just been sent a name look it up, if it exists move to
		 * password retrieval otherwise move to player creation
		 */
		@Override
		public String runState(String s, Map<String, Object> attribs) {
			if (loginService.playerExists(s)) {
				attribs.put(PLAYER_NAME, s);
				attribs.put(STATE_ATTRIB, new LoginPlayerState());
				return "Enter password: ";
			} else {
				attribs.put(STATE_ATTRIB, new PlayerCreateInitState());
				return "Creating new player, enter name: ";
			}
		}
	}

	public class PlayerCreateInitState extends ClientState {

		public PlayerCreateInitState() {
			this.stateName = "playercreateinit";
		}

		@Override
		public String runState(String s, Map<String, Object> attribs) {
			attribs.put("newPlayerName", s);
			attribs.put(STATE_ATTRIB, new PlayerCreatePwordState());
			return "Enter new password: ";
		}

	}

	public class PlayerCreatePwordState extends ClientState {

		public PlayerCreatePwordState() {
			this.stateName = "playercreatepword";
		}

		@Override
		public String runState(String s, Map<String, Object> attribs) {
			attribs.put("newPlayerPword", s);
			attribs.put(STATE_ATTRIB, new PlayerCreateCheckPwordState());
			return "Re-enter password: ";
		}

	}

	public class PlayerCreateCheckPwordState extends ClientState {
		public PlayerCreateCheckPwordState() {
			this.stateName = "playercreatecheckpword";
		}

		@Override
		public String runState(String s, Map<String, Object> attribs) {
			if (s.equals((String) attribs.get("newPlayerPword"))) {
				loginService.addUser((String) attribs.get("newPlayerName"),
						(String) attribs.get("newPlayerPword"));
				attribs.put(PLAYER_NAME, attribs.remove("newPlayerName"));
				attribs.put(STATE_ATTRIB, new LoggedInState());
				
				logger.info("Player "+attribs.get(PLAYER_NAME)+" has logged in");
				return null;
			} else {
				attribs.remove("newPlayerPword");

				attribs.put(STATE_ATTRIB, new PlayerCreatePwordState());
				return "Passwords didn't match, try again.\r\nEnter new password: ";
			}
		}
	}
}
