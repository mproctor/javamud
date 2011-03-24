package javamud.exception;

@SuppressWarnings("serial")
public class LoginException extends Exception {

	public LoginException() {
		super();
	}
	
	public LoginException(String s) {
		super(s);
	}
	
	public LoginException(String s,Throwable t) {
		super(s,t);
	}
}
