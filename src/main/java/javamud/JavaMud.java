package javamud;

import javamud.server.MudServer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JavaMud {
	
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		MudServer server = (MudServer)ac.getBean("server");
		server.start();
	}

}
