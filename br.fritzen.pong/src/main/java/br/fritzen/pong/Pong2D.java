package br.fritzen.pong;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.EngineLog;

public class Pong2D {

	public static void main(String[] args) {

		EngineLog.info("Starting app");
		
		Application app = Application.create("App Teste", 1280, 720);

		app.addLayer(new GameLayer());
		
		app.run();

	}

	
	
}
