package br.fritzen.engine.core;

public class GameLoopTest  extends MainLoopFixedFPS {

	@Override
	protected void init() {
	}

	@Override
	protected void input() {
	}

	@Override
	protected void update(long deltatime) {
	}

	@Override
	protected void render() {
	}

	
	public static void main(String [] args) {
		
		EngineLog.info("Must RUN at: " + EngineState.UPS + " UPS and " + EngineState.FPS + " FPS");
		GameLoopTest gameLoopTest = new GameLoopTest();
		gameLoopTest.run();
		
	}
}
