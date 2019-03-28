package br.fritzen.sandbox;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.EngineLog;

public class ApplicationTest {

	
	
	public static void main(String[] args) {
		
		EngineLog.info("Starting app");
		
		Application app = Application.create("App Teste", 1280, 720);
		app.run();
		
		
	}
	
}
