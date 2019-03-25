package br.fritzen.sandbox;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.events.key.KeyPressedEvent;

public class ApplicationTest extends Application {

	
	public static void main(String[] args) {
		
		EngineLog.info(new KeyPressedEvent('A', 0).toString());
		
		EngineLog.info("Starting app");
		
		Application app = new ApplicationTest();
		app.run();
		
	}
	
}
