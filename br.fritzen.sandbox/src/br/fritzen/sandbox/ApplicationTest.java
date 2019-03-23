package br.fritzen.sandbox;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.EngineLog;

public class ApplicationTest extends Application {

	
	public static void main(String[] args) {
		
		EngineLog.info("Starting app");
		
		Application app = new ApplicationTest();
		app.run();
		
	}
	
	
}
