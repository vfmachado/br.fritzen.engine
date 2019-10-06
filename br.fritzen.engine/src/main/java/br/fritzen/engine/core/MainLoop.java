package br.fritzen.engine.core;

/**
 * This class contains the main gameloop of the engine. 
 * Which attempted to stay at a fix FPS and UPS given the numbers of the EngineState class.
 * 
 * Must be used under the hood.
 * 
 * So don't change it except you know what are you doing.
 * 
 * @author fritz
 *
 */
public abstract class MainLoop {

	
	protected abstract void run();
	
	protected abstract void init();
		
	protected abstract void input();
	
}
