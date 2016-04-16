package org.fuzzybot.player;

import java.io.IOException;
import java.net.UnknownHostException;

public class MyRoboSoccer extends org.robosoccer.RoboSoccer{

	public MyRoboSoccer(String host, int port, boolean debug) throws UnknownHostException, IOException {
		super(host, port);
		super.setDebug(debug);
		
	}
	

}
