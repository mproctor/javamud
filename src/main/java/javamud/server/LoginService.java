package javamud.server;

public interface LoginService {

	public abstract boolean verifyPassword(String pName, String s);

	public abstract boolean playerExists(String s);

	public abstract void addUser(String name, String pword);

}