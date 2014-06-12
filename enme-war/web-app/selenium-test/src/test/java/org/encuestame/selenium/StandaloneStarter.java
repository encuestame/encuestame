package org.encuestame.selenium;

public class StandaloneStarter {

	public static void main(String[] args) throws Exception {
		InitialHomeTestIT start = new InitialHomeTestIT();
        start.setUp();
		start.testInitialLogin();
        start.tearDown();
	}

}
