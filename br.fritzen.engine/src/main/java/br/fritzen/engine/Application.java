package br.fritzen.engine;

import org.lwjgl.opengl.GL11;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.MainLoop;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventDispatcher;
import br.fritzen.engine.events.mouse.MouseMovedEvent;
import br.fritzen.engine.events.window.WindowCloseEvent;
import br.fritzen.engine.platform.windows.WindowsWindowImpl;
import br.fritzen.engine.window.Window;

public class Application extends MainLoop {

	
	private Window window;
	
	private static Application instance = null;
	
	
	public Application() {
		this("Fritzen Engine", 1280, 720);
	}
	
	
	private Application(String title, int width, int height) {
		
		window = new WindowsWindowImpl(width, height, title);
		//window.setEventCallback(() -> this.onEvent());
	}
	
	
	public static Application create(String title, int width, int height) {
		instance = new Application(title, width, height);
		return instance;
	}
	
	
	public static Application getInstance() {
		
		if (instance == null) {
			instance = new Application();
		}
		
		return instance;
	}
	
	
	public Window getWindow() {
		return this.window;
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
		dispatcher.dispatch(this::onMouseEvent, MouseMovedEvent.class);
		
		dispatcher.dispatch(this::onMouseEvent2, MouseMovedEvent.class);
		
		
		/*
		
		EventDispatcher dispatcher(event);
		dispatcher.Dispatch<MouseButtonPressedEvent>(HZ_BIND_EVENT_FN(ImGuiLayer::OnMouseButtonPressedEvent));
		dispatcher.Dispatch<MouseButtonReleasedEvent>(HZ_BIND_EVENT_FN(ImGuiLayer::OnMouseButtonReleasedEvent));
		dispatcher.Dispatch<MouseMovedEvent>(HZ_BIND_EVENT_FN(ImGuiLayer::OnMouseMovedEvent));
		dispatcher.Dispatch<MouseScrolledEvent>(HZ_BIND_EVENT_FN(ImGuiLayer::OnMouseScrolledEvent));
		dispatcher.Dispatch<KeyPressedEvent>(HZ_BIND_EVENT_FN(ImGuiLayer::OnKeyPressedEvent));
		dispatcher.Dispatch<KeyTypedEvent>(HZ_BIND_EVENT_FN(ImGuiLayer::OnKeyTypedEvent));
		dispatcher.Dispatch<KeyReleasedEvent>(HZ_BIND_EVENT_FN(ImGuiLayer::OnKeyReleasedEvent));
		dispatcher.Dispatch<WindowResizeEvent>(HZ_BIND_EVENT_FN(ImGuiLayer::OnWindowResizeEvent));
		
		 */
		
		
	}
	
	
	@Override
	protected void render() {
		

		//Now it's tied to OpenGL
		
		GL11.glClearColor(0, 0.7f, 0.7f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		
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
}
