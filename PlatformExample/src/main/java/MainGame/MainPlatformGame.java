package MainGame;

import br.fritzen.engine.Application;


public class MainPlatformGame {

	public static void main(String[] args) {
		Application platformGame = Application.create("Platform", 1280, 720);
		platformGame.addLayer(new PlatformGameLayer("MainLayer"));
		platformGame.run();
	}

}
