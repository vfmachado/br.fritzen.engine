package br.fritzen.engine;

import org.lwjgl.opengl.GL11;

import br.fritzen.engine.core.MainLoop;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.platform.windows.WindowsWindowImpl;
import br.fritzen.engine.window.Window;

public class Application extends MainLoop {

	
	private Window window;
	
	
	public Application() {
		this("Fritzen Engine", 1280, 720);
	}
	
	
	public Application(String title, int width, int height) {
		
		window = new WindowsWindowImpl(width, height, title);
		
	}
	
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

	
	@Override
	protected void input() {
		// TODO Auto-generated method stub

	}

	
	public void onEvent(Event e) {
		
	}
	
	
	@Override
	protected void render() {
		
		//Now it's tied to OpenGL
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		
		//the update method from window is related to render (VSYNC)
		window.onUpdate();
	}
	
	
	@Override
	protected void update(long deltatime) {
		
		
		
	}
	
}
