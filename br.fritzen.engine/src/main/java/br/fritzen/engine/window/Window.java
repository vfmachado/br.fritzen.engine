package br.fritzen.engine.window;

import br.fritzen.engine.Application;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.renderer.GraphicsContext;
import lombok.Getter;

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
	
	@Getter
	protected int width;
	
	@Getter
	protected int height;
	
	@Getter
	protected String title;
	
	@Getter
	protected WindowMode windowMode;
	
	@Getter
	protected GraphicsContext context;
	
	protected abstract void init();
	
	public abstract void cleanUp();

	public abstract void onUpdate();
	
	public abstract long getNativeWindow();
	
	public abstract void setVSync(boolean enable);
	
	public abstract boolean isVSync();
	
	public abstract void setWindowMode(WindowMode windowMode);
	
	public abstract void setWindowSize(int width, int height);
	
	public abstract void setCursorPos(float x, float y);
	
	public abstract void enableMouse();

	public abstract void disableMouse();

	
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

	
	


}
