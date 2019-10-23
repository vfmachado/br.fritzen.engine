package br.fritzen.sandbox2d;

import br.fritzen.engine.Application;

public class App2D {

	public static void main(String[] args) {

		Application app = Application.create("Sandbox 2D", 1280, 720);
		app.addLayer(new MainLayer());
		app.run();
	}

}
