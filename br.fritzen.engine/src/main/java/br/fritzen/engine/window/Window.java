package br.fritzen.engine.window;

import br.fritzen.engine.Application;
import br.fritzen.engine.events.Event;

/**
 * 
 * @author fritz
 *
 */

public abstract class Window {

	public enum WindowMode {
		WINDOWED,
		FULL_SCREEN,
		BORDERLESS;
	};
	
	protected int width;
	
	protected int height;
	
	protected String title;
	
	protected WindowMode windowMode;
	
	protected abstract void init();
	
	public abstract void cleanUp();

	public abstract void onUpdate();
	
	public abstract long getNativeWindow();
	
	public abstract void setVSync(boolean enable);
	
	public abstract boolean isVSync();
	
	public abstract void setWindowMode(WindowMode windowMode);
	
	public abstract void setWindowSize(int width, int height);
	
	public void eventCallback(Event e) {
		
		Application.getInstance().onEvent(e);
		
	}
	
	
	public Window(int width, int height, String title) {
		
		this.width = width;
		this.height = height;
		this.title = title;
		this.windowMode = WindowMode.WINDOWED;
		
		init();
		
	}

	
	public int getWidth() {
		return this.width;
	}
	
	
	public int getHeight() {
		return this.height;
	}
	
	
	public String getTitle() {
		return this.title;
	}
	
	
	public WindowMode getCurrentWindowMode() {
		return windowMode;
	}
	
}
