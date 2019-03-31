package br.fritzen.engine;

import org.lwjgl.opengl.GL11;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.MainLoop;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventDispatcher;
import br.fritzen.engine.events.key.KeyEvent;
import br.fritzen.engine.events.key.KeyPressedEvent;
import br.fritzen.engine.events.window.WindowCloseEvent;
import br.fritzen.engine.platform.windows.WindowsWindowImpl;
import br.fritzen.engine.window.Window;
import br.fritzen.engine.window.Window.WindowMode;

public class Application extends MainLoop {

	
	private Window window;
	
	private static Application instance = null;
	
	
	private Application() {
		//this("Fritzen Engine", 1280, 720);
	}
	
	
	private Application(String title, int width, int height) {

		
	}
	
	
	public static Application create(String title, int width, int height) {
		
		EngineLog.info("Creating applicaiton instance");
		
		instance = new Application(title, width, height);
		instance.window = new WindowsWindowImpl(width, height, title);
		return instance;
		
	}
	
	
	public static Application getInstance() {
		
		if (instance == null) {
			instance = Application.create("Fritzen Engine", 800, 600);
		}
		
		return instance;
	}
	
	
	public Window getWindow() {
		return getInstance().window;
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
		
		EngineLog.info(e.toString());
		
		EventDispatcher dispatcher = new EventDispatcher(e);
		dispatcher.dispatch(this::onWindowCloseEvent, WindowCloseEvent.class);
		
		dispatcher.dispatch(this::onKeyPressedEvent, KeyPressedEvent.class);
		
		//DISPATCHER TEST
		//dispatcher.dispatch(this::onMouseEvent, MouseMovedEvent.class);
		//dispatcher.dispatch(this::onMouseEvent2, MouseMovedEvent.class);
		
		
	}
	
	
	@Override
	protected void render() {
		

		//Now it's tied to OpenGL
		
		GL11.glClearColor(0, 0.7f, 0.7f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		
		GL11.glViewport(0, 0, this.getWindow().getWidth(), this.getWindow().getHeight());
		
		GL11.glColor3f(1, 1, 0);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glVertex2f( -1 , -1);
		GL11.glVertex2f(  0 ,  1);
		GL11.glVertex2f(  1 , -1 );
		GL11.glEnd();
		
		//the update method from window is related to render (VSYNC) ??
		window.onUpdate();
	}
	
	
	@Override
	protected void update(long deltatime) {
		
		
		
	}
	
	
	private boolean onWindowCloseEvent(Event e) {
	
		this.stop();
		return true;
		
	}
	
	/*
	private boolean onMouseEvent(Event e) {
		
		MouseMovedEvent evt = (MouseMovedEvent) e;
		
		boolean willReturn = Math.random() > 0.5 ? false :  true;
		
		System.out.printf("Mouse movimentado para: (%.0f, %.0f) : " + willReturn + "\n", evt.getPosX(), evt.getPosY());
		
		
		return willReturn;
	}
	
	
	private boolean onMouseEvent2(Event e) {
		
		MouseMovedEvent evt = (MouseMovedEvent) e;
		
		System.out.printf("Mouse movimentado para: (%.0f, %.0f)\n", evt.getPosX(), evt.getPosY());
		
		return false;
	}
	*/

	
	private boolean onKeyPressedEvent(Event e) {
	
		KeyPressedEvent evt = (KeyPressedEvent)e;
		
		System.out.println("Event dispatched");
		System.out.println(evt.getKeyCode() + "\t" + KeyEvent.KEY_F12);
		
		if (evt.getKeyCode() == KeyEvent.KEY_F12) {
			this.window.setWindowMode(WindowMode.FULL_SCREEN);
		} else if (evt.getKeyCode() == KeyEvent.KEY_F11) {
			this.window.setWindowMode(WindowMode.BORDERLESS);
		} else if (evt.getKeyCode() == KeyEvent.KEY_F10) {
			this.window.setWindowMode(WindowMode.WINDOWED);
		} 
		
		return false;
	}
}
