package br.fritzen.engine.core;

public class GameLoopTest  extends MainLoop {

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void input() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void update(long deltatime) {
		//System.out.println(deltatime);
	}

	@Override
	protected void render() {
		// TODO Auto-generated method stub
		
	}

	
	public static void main(String [] args) {
		
		GameLoopTest resourcesTest = new GameLoopTest();
		resourcesTest.run();
		
	}
}
