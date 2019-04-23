package br.fritzen.engine;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.MainLoop;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.core.layers.LayerStack;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventDispatcher;
import br.fritzen.engine.events.key.KeyEvent;
import br.fritzen.engine.events.key.KeyPressedEvent;
import br.fritzen.engine.events.window.WindowCloseEvent;
import br.fritzen.engine.imgui.ImGuiLayer;
import br.fritzen.engine.platform.windows.WindowsWindowImpl;
import br.fritzen.engine.window.Window;
import br.fritzen.engine.window.Window.WindowMode;

public class Application extends MainLoop {

	private static Application instance = null;
	
	private Window window;
	
	private EventDispatcher dispatcher;
	
	private LayerStack layerStack;
	
	private ImGuiLayer imguiLayer;
	
	private Application() {
		//this("Fritzen Engine", 1280, 720);
	}
	
	
	private Application(String title, int width, int height) {

		this.dispatcher = new EventDispatcher();
		this.layerStack = new LayerStack();
		
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
	
	
	public static Window getWindow() {
		return instance.window;
	}
	
	
	@Override
	protected void init() {
		//TODO CHECK TO REMOVE THIS... IT'S RELATED TO OPENGL STUFF
		GL.createCapabilities();
		
		imguiLayer = new ImGuiLayer();
		layerStack.pushOverlay(imguiLayer);
		
	}

	
	@Override
	protected void input() {
		// TODO Auto-generated method stub

	}
	
	
	public void onEvent(Event e) {
		
		EngineLog.info(e.toString());
		
		dispatcher.setEvent(e);
		
		dispatcher.dispatch(this::onWindowCloseEvent, WindowCloseEvent.class);
		
		dispatcher.dispatch(this::onFKeyWindowModeEvent, KeyPressedEvent.class);
		
		
		for (Layer layer : layerStack) {
			layer.onOvent(e);
		}
		
	}
	
	
	@Override
	protected void render() {
		
		
		//Now it's tied to OpenGL
		GL.createCapabilities();
		
		
		GL11.glClearColor(0, 0.7f, 0.7f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		
		GL11.glViewport(0, 0, Application.getWindow().getWidth(), Application.getWindow().getHeight());
		
		GL11.glColor3f(1, 1, 0);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glVertex2f( -1 , -1);
		GL11.glVertex2f(  0 ,  1);
		GL11.glVertex2f(  1 , -1 );
		GL11.glEnd();
		
		
		imguiLayer.begin();
		
		for (Layer layer : layerStack) {
			layer.onImGuiRender();
		//	System.out.println("Render IMGUI: " + layer.getName());
		}
		
		imguiLayer.end();
		
		//the update method from window is related to render (VSYNC) or update ??
		getWindow().onUpdate();
	}
	
	
	@Override
	protected void update(long deltatime) {
		
		
		
		for (Layer layer : layerStack) {
		
			layer.onUpdate();
		
		}
		
	
	}
	
	
	private boolean onWindowCloseEvent(Event e) {
	
		this.stop();
		return true;
		
	}
	
	
	private boolean onFKeyWindowModeEvent(Event e) {
	
		KeyPressedEvent evt = (KeyPressedEvent)e;
		
		if (evt.getKeyCode() == KeyEvent.KEY_F12) {
			
			getWindow().setWindowMode(WindowMode.FULL_SCREEN);

		
		} else if (evt.getKeyCode() == KeyEvent.KEY_F11) {
			
			getWindow().setWindowMode(WindowMode.BORDERLESS);

		
		} else if (evt.getKeyCode() == KeyEvent.KEY_F10) {
		
			getWindow().setWindowMode(WindowMode.WINDOWED);

		
		} 
		
		return false;
	}
}
